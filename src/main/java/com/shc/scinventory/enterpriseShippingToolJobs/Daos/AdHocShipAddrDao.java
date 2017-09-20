package com.shc.scinventory.enterpriseShippingToolJobs.Daos;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.AdhocAddressBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;

@Component
public class AdHocShipAddrDao {
    private static final Logger LOG = Logger.getLogger(AdHocShipAddrDao.class);

    private final static String insertSql = "insert into adhoc_ship_addr " +
            "(addrId, company, contact, addr1, addr2, addr3, city, state, zip, phone, dcUnitId)" +
                " values ( ?,?,?,?,?,?,?,?,?,?,?)";

        private final static String updateSql = "UPDATE adhoc_ship_addr\n" +
                "SET\n" +
                "company = ?,\n" +
                "contact = ?,\n" +
                "addr1 = ?,\n" +
                "addr2 = ?,\n" +
                "addr3 = ?,\n" +
                "city = ?,\n" +
                "state = ?,\n" +
                "zip = ?,\n" +
                "phone = ?\n" +
                "WHERE addrId = ? and dcUnitId = ?";

        private final static String selectSql = "select * from adhoc_ship_addr where dcUnitId in ( '[dcUnitId]' , 'SHC')";

        private final static String deleteSql = "delete from adhoc_ship_addr where addrid = '[addrId]' " +
                "and dcUnitId = '[dcUnitId]';";


        @Autowired
        private JdbcTemplate jdbcTemplate;

        public void update(AdhocAddressBean adhocShipAddrBean) {
            try {
                jdbcTemplate.update(updateSql,
                        adhocShipAddrBean.getCompany(),
                        adhocShipAddrBean.getContact(),
                        adhocShipAddrBean.getAddr1(),
                        adhocShipAddrBean.getAddr2(),
                        adhocShipAddrBean.getAddr3(),
                        adhocShipAddrBean.getCity(),
                        adhocShipAddrBean.getState(),
                        adhocShipAddrBean.getZip(),
                        adhocShipAddrBean.getPhone(),
                        adhocShipAddrBean.getAddrId(),
                        adhocShipAddrBean.getDcUnitId()
                        );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void insert(AdhocAddressBean adhocShipAddrBean) {
            try {
                jdbcTemplate.update(insertSql,
                        adhocShipAddrBean.getAddrId(),
                        adhocShipAddrBean.getCompany(),
                        adhocShipAddrBean.getContact(),
                        adhocShipAddrBean.getAddr1(),
                        adhocShipAddrBean.getAddr2(),
                        adhocShipAddrBean.getAddr3(),
                        adhocShipAddrBean.getCity(),
                        adhocShipAddrBean.getState(),
                        adhocShipAddrBean.getZip(),
                        adhocShipAddrBean.getPhone(),
                        adhocShipAddrBean.getDcUnitId()
                );
            } catch (Exception e) {
              	System.out.println(JSONSerializer.serialize(adhocShipAddrBean));
            	System.out.println(e.getMessage());
            }
        }

        public List<AdhocAddressBean> getAdHocShipAddresses (String dcUnitId) {
            List<AdhocAddressBean> addresses = null;
            try {
                String sql = selectSql.replace("[dcUnitId]", dcUnitId);
                LOG.debug("Query to fetch all addresses for adhoc shipping: " + sql);
                addresses =
                        jdbcTemplate.query(sql, new BeanPropertyRowMapper(AdhocAddressBean.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return addresses;
        }

        public void deleteAdHocShipAddresses (String dcUnitId, String addrId) {
            String _deleteSql = "";
            try {
                _deleteSql = deleteSql.replace("[addrId]",addrId).replace("[dcUnitId]", dcUnitId);
                LOG.debug("Query to delete an addresses for adhoc shipping: " + _deleteSql);
                jdbcTemplate.update(_deleteSql);
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
}
