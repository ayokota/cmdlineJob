package com.shc.scinventory.enterpriseShippingToolJobs.Applications;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.shc.scinventory.enterpriseShippingToolJobs.CmdParsers.MainCommandLineParser;
import com.shc.scinventory.enterpriseShippingToolJobs.Handlers.JobsHandler;

public class MainApp {
    private static final Logger logger = Logger.getLogger(MainApp.class);

	
	public static void main(String [] args) {
		System.out.println("Enterprise Shipping Tool Jobs");
		
        ApplicationContext context = null;
        JobsHandler jobsHandler = null;
        MainCommandLineParser mainCommandLineParser = null;
        JCommander jc = null;
        
        try {
        	mainCommandLineParser = new MainCommandLineParser();
        	jc = new JCommander(mainCommandLineParser);
            jc.parse(args);
	        context = new ClassPathXmlApplicationContext("application-context.xml");
	        jobsHandler = (JobsHandler) context.getBean("JobsHandler");
	        
	        jobsHandler.executeJob(mainCommandLineParser);
        } catch (ParameterException e) {
        	logger.error("An exception has occured in main method: "+ e.getMessage());
            jc.usage();
        } catch (Exception e) {
        	logger.error("Exception occured in main. ", e);
        	e.printStackTrace();
        	System.exit(1);
        } 
        System.exit(0);

	}
}
