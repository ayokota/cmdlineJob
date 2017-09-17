package com.shc.scinventory.enterpriseShippingToolJobs.Utilities;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import liquibase.util.StringUtils;








public class ListUtils {
    public static List<String> rawToList(String raw) {
        List<String> result = new LinkedList<String>();

        try {
            for (String line : StringUtils.splitAndTrim(raw, "\n")) {
            	
                result.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<String> lineToList(String line, String delimiter) {
        List<String> result = new LinkedList<String>();

        try {
            for (String col : StringUtils.splitAndTrim(line.replace("\"", ""), delimiter)) {
                    result.add(col);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public static List<String> csvToList(String line) {
        List<String> result = new LinkedList<String>();

        try {

        	result = Arrays.asList(line.split("\\s*,\\s*"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
