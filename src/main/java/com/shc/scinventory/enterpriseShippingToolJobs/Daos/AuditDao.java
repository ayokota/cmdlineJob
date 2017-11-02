package com.shc.scinventory.enterpriseShippingToolJobs.Daos;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuditDao {
    private static final Logger LOG = Logger.getLogger(AuditDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final String getDcsThatManifestedWithDateQuery = 
    		"select dcUnitId AS most_recent_manifest from audit where LENGTH(dcUnitId) = 4 and workFlow ='Manifest' and time > '[date]' group by dcUnitId;";
    
    public List<String> getDcsThatManifestedWithDate(String date) {
    	List <String> dcUnits = null;
    	try {
    		String query = getDcsThatManifestedWithDateQuery.replace("[date]", date);
    		dcUnits = jdbcTemplate.queryForList(query, String.class);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return dcUnits;
    }
}
