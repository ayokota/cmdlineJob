package com.shc.scinventory.enterpriseShippingToolJobs.Daos;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.UnitInfoBean;

@Component
public class UnitInfoDao {
    private static final Logger LOG = Logger.getLogger(UnitInfoDao.class);

    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Map<String, UnitInfoBean> unitInfoCache;

    private final String getCountSql = "select count(*) from unit_info where unit_id = \"?\"";

    private final String getUnitInfoSql = "select * from unit_info where unit_id = \"?\"";


    private static final String insertSql =
            "INSERT INTO unit_info (unit_id,\n" +
                    "reason_cd,\n" +
                    "reason_desc,\n" +
                    "packsize_enabled,\n" +
                    "show_reason,\n" +
                    "ptr_sl,\n" +
                    "ptr_pl," +
                    "label_sz, auto_print, ship_date) VALUES\n" +
                    "(?,?,?,?,?,?,?,?,?, ?);";

    public void insert(UnitInfoBean unitInfoBean) {
        try {
            jdbcTemplate.update(insertSql,
                    unitInfoBean.getUnit_id(),
                    unitInfoBean.getReason_cd(),
                    unitInfoBean.getReason_desc(),
                    unitInfoBean.isPacksize_enabled(),
                    unitInfoBean.isShow_reason(),
                    unitInfoBean.getPtr_sl(),
                    unitInfoBean.getPtr_pl(),
                    unitInfoBean.getLabel_sz(),
                    unitInfoBean.isAuto_print(),
                    unitInfoBean.getShip_date()
            );
        } catch (Exception e) {
            LOG.error("An error has occured in insert method with error msg: " + e.getMessage(), e);
        }
    }

    @PostConstruct
    public void init() {
        try {
            Map<String, UnitInfoBean> newUnitInfoCache = new HashMap<String, UnitInfoBean>();

            List<UnitInfoBean> appConfigBeans = getAllUnitInfo();
            for (UnitInfoBean bean : appConfigBeans) {
                newUnitInfoCache.put(bean.getUnit_id(), bean);
            }

            unitInfoCache = newUnitInfoCache;
        } catch (Exception e) {
            LOG.error("An error has occured when initializing UnitInfo table. Error msg: " + e.getMessage(),
                    e);

        }
    }

    public List<UnitInfoBean> getAllUnitInfo() {
        List <UnitInfoBean> unitInfoBeans = null;
        try {
            unitInfoBeans =
                    jdbcTemplate.query("select * from unit_info", new BeanPropertyRowMapper(UnitInfoBean.class));
        } catch (Exception e) {
            LOG.error("An error has occured in getAllUnitInfo method with error msg: " + e.getMessage(), e);
        }
        return unitInfoBeans;
    }

    
    public Map<String, UnitInfoBean> getUnitInfoCache() {
        return unitInfoCache;
    }

    public boolean unitInfoExists(String dcUnitId) {
        boolean exists = false;
        try {
            String query = getCountSql.replace("?", dcUnitId);
            int count = jdbcTemplate.queryForInt(query);
            if(count>0) {
                exists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    public UnitInfoBean getUnitInfo(String dcUnitId) {
        UnitInfoBean unitInfoBean = null;
        try {
            String query = getUnitInfoSql.replace("?", dcUnitId);
            List<UnitInfoBean> result =
                jdbcTemplate.query(query, new BeanPropertyRowMapper(UnitInfoBean.class));
            if(result!=null && result.size()>0) {
                unitInfoBean = result.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitInfoBean;
    }


    private static final String updateShipDateSql =
            "UPDATE unit_info\n" +
                    "SET\n" +
                    "ship_date = ?\n, " +
                    "previous_ship_date = ?\n" +
                    "WHERE unit_id = ?";
    
    private static final String autoUpdateShipDateSql =
            "UPDATE unit_info\n" +
                    "SET\n" +
                    "ship_date = ?\n, " +
                    "previous_ship_date = ?\n," +
                    "type_of_update = ?\n" +
                    "WHERE unit_id = ?";

    public void updateShipDate(String dcUnitId, Date ship_date) {
        Date previous_ship_date = null;
        try {
            previous_ship_date = getShipDate(dcUnitId);

            jdbcTemplate.update(updateShipDateSql,
                    ship_date,
                    previous_ship_date,
                    dcUnitId
            );
        } catch (Exception e) {
            LOG.error("An error has occured in insert method with error msg: " + e.getMessage()
                    + "\nWith dcUnitId: " + dcUnitId
                    + "\n& previous_ship_date: " + previous_ship_date
                    + " & ship_date: " + ship_date, e);
        }
    }

    
    public void autoUpdateShipDate(String dcUnitId, Date ship_date, String typeOfUpdate) {
        Date previous_ship_date = null;
        try {
            previous_ship_date = getShipDate(dcUnitId);

            jdbcTemplate.update(autoUpdateShipDateSql,
                    ship_date,
                    previous_ship_date,
                    typeOfUpdate,
                    dcUnitId
            );
        } catch (Exception e) {
            LOG.error("An error has occured in insert method with error msg: " + e.getMessage()
                    + "\nWith dcUnitId: " + dcUnitId
                    + "\n& previous_ship_date: " + previous_ship_date
                    + "\n& type of update: " + typeOfUpdate
                    + " & ship_date: " + ship_date, e);
        }
    }
    
    private final String getShipDateSql = "select ship_date from unit_info where unit_id = \"?\"";


    public Date getShipDate(String dcUnitId) {
        Date ship_date = null;
        try {
            String query = getShipDateSql.replace("?", dcUnitId);
            ship_date = jdbcTemplate.queryForObject(query, Date.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ship_date;
    }
    
    private static final String updatePickupTimeSql =
            "UPDATE unit_info\n" +
                    "SET\n" +
                    "pickup_time = ?\n " +
                    "WHERE unit_id = ?";
    
    
    public void updatePickupTime(String pickupTime, String dcUnitId) {
        try {
            jdbcTemplate.update(updatePickupTimeSql,
            		pickupTime,
            		dcUnitId
            );
        } catch (Exception e) {
            LOG.error("An error has occured in insert method with error msg: " + e.getMessage()
                    + "\nWith dcUnitId: " + dcUnitId
                    + "\n& pickupTime: " + pickupTime, e);
            e.printStackTrace();
        }
    }
    
    private final String getDcListQuery =
            "select unit_id from unit_info";

    public List<String> getListOfDcs() {
        List<String> dcList = null;
        try {
            dcList =
                    jdbcTemplate.queryForList (getDcListQuery, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dcList;
    }
}
