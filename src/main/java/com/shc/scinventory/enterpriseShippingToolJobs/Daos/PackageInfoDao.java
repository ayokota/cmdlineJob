package com.shc.scinventory.enterpriseShippingToolJobs.Daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.PackageInfoBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Exception.ShippingToolException;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.EnterpriseShippingToolConstants;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.ListUtils;

@Component
public class PackageInfoDao {
    private static final Logger LOG = Logger.getLogger(PackageInfoDao.class);
    
    private final String insertSql = "insert into package_info "+
            "(suborderid, orderid, box_used, tracking_no, shipped_date, actual_shipmode, actual_carrier, locn_nbr, package_sequence, reason_cd, last_updated_ts)" +
            " values (?,?,?,?,?,?,?,?,?,?,?)";

    
    private final String updateSql = "update package_info set tracking_no = ? " +
    " where suborderid=? and orderid=? and package_sequence=? and locn_nbr=? ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    
    public void insertPackageInfo(final List<PackageInfoBean> packageInfoList) {
        jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
            
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                PackageInfoBean packageInfo =  packageInfoList.get(i);
                preparedStatement.setString(1, packageInfo.getSuborderId());
                preparedStatement.setString( 2, packageInfo.getOrderid());
                preparedStatement.setString( 3, packageInfo.getBox_used());
                preparedStatement.setString( 4, packageInfo.getTracking_no());
                preparedStatement.setDate( 5, packageInfo.getShipped_date());
                preparedStatement.setString( 6, packageInfo.getActual_shipmode());
                preparedStatement.setString( 7, packageInfo.getActual_carrier());
                preparedStatement.setString( 8, packageInfo.getLocn_nbr());
                preparedStatement.setInt( 9, packageInfo.getPackage_sequence());
                preparedStatement.setString(10, "N/A");
                preparedStatement.setTimestamp(11, packageInfo.getLast_updated_ts());

            }

            public int getBatchSize() {
                return packageInfoList.size();
            }
        });
    }

    public void updatePackageInfo(final List<PackageInfoBean> packageInfoList) {
        jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter() {
            
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                PackageInfoBean packageInfo =  packageInfoList.get(i);
                preparedStatement.setString( 1, packageInfo.getTracking_no());
                preparedStatement.setString(2, packageInfo.getSuborderId());
                preparedStatement.setString( 3, packageInfo.getOrderid());
                preparedStatement.setString( 5, packageInfo.getLocn_nbr());
                preparedStatement.setInt( 4, packageInfo.getPackage_sequence());
            }

            public int getBatchSize() {
                return packageInfoList.size();
            }
        });
    }
    
    private String getTrackingNums(List<String> trackingNumbers) {
        StringBuilder trackingNums = new StringBuilder();

        if(trackingNumbers!=null && trackingNumbers.size()>0) {
            for(String trackingNum : trackingNumbers) {
                trackingNums.append("'").append(trackingNum).append("',");
            }
            if(trackingNums.length()>0) {
                trackingNums.setLength(trackingNums.length()-1);
            }
        }
        return trackingNums.toString();
    }
    
    private final String getSubOrderIdWithTrackingQuery =
            "select suborderid from package_info where tracking_no in (?) and is_adhoc=0";
    
    public List<String> getSubOrderIdForTrackingNumbers(List<String> trackingNumbers) {
        List<String> subOrderIds = null;

        try {
            String trackingNums = getTrackingNums (trackingNumbers);
            String query = getSubOrderIdWithTrackingQuery.replace("?", trackingNums);
            subOrderIds =
                    jdbcTemplate.queryForList (query, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subOrderIds;
    }
    
    private final String getSubOrderIdWithMsnQuery =
            "select suborderid from package_info where msn in (?) and is_adhoc=0" +
                    " and update_status_id = " + EnterpriseShippingToolConstants.STATUS_UPDATE_CODE_ORDER_PROCESSED;

    public List<String> getSubOrderIdWithMsn(List<Integer> msn) {
        List<String> subOrderIds = null;

        try {
            String query = getSubOrderIdWithMsnQuery.replace("?", ListUtils.concatIntegersForUpdate(msn));
            LOG.debug(query);
            subOrderIds =
                    jdbcTemplate.queryForList (query, String.class);
        } catch (Exception e) {
            throw new ShippingToolException
                    ("An error has occured in updateStatusToShippedAndManifested method with error msg: "
                            + e.getMessage());
        }
        return subOrderIds;
    }
    
    private final static String updateShippedWithMsn = "update package_info set update_status_id="
            + EnterpriseShippingToolConstants.STATUS_UPDATE_CODE_SHIPPED_AND_MANIFESTED
            + " where msn in (?) and update_status_id = " + EnterpriseShippingToolConstants.STATUS_UPDATE_CODE_ORDER_PROCESSED;

    public void updateStatusToShippedAndManifested(List<Integer> msns) {
        try {
            String query = updateShippedWithMsn.replace("?", ListUtils.concatIntegersForUpdate(msns));
            LOG.debug(query);

            jdbcTemplate.execute(query);
        } catch (Exception e) {
            throw new ShippingToolException
                    ("An error has occured in updateStatusToShippedAndManifested method with error msg: "
                            + e.getMessage());
        }
    }
    

    private final static String updateFailedShippedWithMsn = "update package_info set update_status_id="
            + EnterpriseShippingToolConstants.STATUS_UPDATE_CODE_SHIPPED_UPDATE_FAILED
            + " where msn in (?) and update_status_id = " + EnterpriseShippingToolConstants.STATUS_UPDATE_CODE_ORDER_PROCESSED;

    public void updateStatusToFailedShipped(List<Integer> msns) {
        try {
            String query = updateFailedShippedWithMsn.replace("?", ListUtils.concatIntegersForUpdate(msns));
            LOG.debug(query);

            jdbcTemplate.execute(query);
        } catch (Exception e) {
            throw new ShippingToolException
                    ("An error has occured in updateStatusToFailedShipped method with error msg: "
                            + e.getMessage());
        }
    }
}
