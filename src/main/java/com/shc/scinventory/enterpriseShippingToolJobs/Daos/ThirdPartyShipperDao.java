package com.shc.scinventory.enterpriseShippingToolJobs.Daos;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.AdhocAddressBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;

@Component
public class ThirdPartyShipperDao {
	private static final Logger LOG = Logger.getLogger(ThirdPartyShipperDao.class);


    private final static String insertSql = "insert into third_party_shipper " +
            "(addrId, company, contact, addr1, addr2, addr3, city, state, zip, phone, dcUnitId, country, upsAcc)" +
            " values ( ?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private final static String updateSql = "UPDATE third_party_shipper\n" +
            "SET\n" +
            "company = ?,\n" +
            "contact = ?,\n" +
            "addr1 = ?,\n" +
            "addr2 = ?,\n" +
            "addr3 = ?,\n" +
            "city = ?,\n" +
            "state = ?,\n" +
            "zip = ?,\n" +
            "phone = ?,\n" +
            "country = ?,\n" +
            "upsAcc = ?\n" +
            "WHERE addrId = ? and dcUnitId = ?";

    private final static String selectSql = "select * from third_party_shipper where dcUnitId in ( '[dcUnitId]' , 'SHC')";

    private final static String deleteSql = "delete from third_party_shipper where addrid = '[addrId]' " +
            "and dcUnitId = '[dcUnitId]';";

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
                    adhocShipAddrBean.getDcUnitId(),
                    adhocShipAddrBean.getCountry(),
                    adhocShipAddrBean.getUpsAcc()
            );
        } catch (Exception e) {
        	System.out.println(JSONSerializer.serialize(adhocShipAddrBean));
        	System.out.println(e.getMessage());
//            LOG.error("An error has occured in insert method with error msg: " + e.getMessage()
//                    + "\nOriginal data: "+ JSONSerializer.serialize(adhocShipAddrBean), e);
        }
    }

}
