package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Daos.ConfigurationDao;

@Component
public class DBcheck {
    private static final Logger LOG = Logger.getLogger(DBcheck.class);

	
	@Autowired
	ConfigurationDao configurationDao;
	
	public void run()  {
		System.out.println("running");
		
		
		int i = 0;
		while( true) {
			try {
				Thread.sleep(5000);
				System.out.println(Calendar.getInstance().getTime().toString() + " run: " + i);
				configurationDao.getAllConfigs();
				i++;
			} catch (Exception e) {
				e.printStackTrace();
//				LOG.error(e);
			}
		}
	}
}
