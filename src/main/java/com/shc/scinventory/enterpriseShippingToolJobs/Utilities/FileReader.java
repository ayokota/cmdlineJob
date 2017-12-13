package com.shc.scinventory.enterpriseShippingToolJobs.Utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class FileReader {
    public static String readResource(final String fileName) throws IOException {
        return Resources.toString(Resources.getResource(fileName), Charsets.UTF_8);
    }
    
    public static String readFile(String fileName) throws IOException {
    	String everything = "";
    	FileInputStream inputStream = new FileInputStream(fileName);
    	try {
        	everything = IOUtils.toString(inputStream);
    	} finally {
    	    inputStream.close();
    	}
    	return everything;
    }
}
