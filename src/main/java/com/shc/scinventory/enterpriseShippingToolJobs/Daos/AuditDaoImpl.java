package com.shc.scinventory.enterpriseShippingToolJobs.Daos;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.AuditBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Exception.ShippingToolException;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;

@Component
public class AuditDaoImpl  {

    private static final Logger LOG = Logger.getLogger(AuditDaoImpl.class);

    private final static String insertSql =
            "INSERT INTO audit (dcUnitId , userId , eventType , workFlow, deviceInfo, msg )VALUES\n" +
            "(? , ? , ?, ?, ?, ?)";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertInstance(AuditBean auditBean) {
        try {
            jdbcTemplate.update(insertSql,
                auditBean.getDcUnitId(),
                    auditBean.getUserId(),
                    auditBean.getEventType(),
                    auditBean.getWorkFlow(),
                    auditBean.getDeviceInfo(),
                    auditBean.getMsg()
            );
        } catch (Exception e) {
            throw new ShippingToolException("An error has occured in insert method with error msg: " + e.getMessage()
                    + " Original data -- " + JSONSerializer.serialize(auditBean), e.getCause());
        }
    }
}
