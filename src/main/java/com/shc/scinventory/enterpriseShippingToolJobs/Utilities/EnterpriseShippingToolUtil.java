package com.shc.scinventory.enterpriseShippingToolJobs.Utilities;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class EnterpriseShippingToolUtil {
    private static final SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
    private static final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

    public static String prepend(String str, char ch, int length) {
        if (str == null || str.length() > length) {
            return null;
        }

        char[] array = new char[length];
        Arrays.fill(array, ch);

        str.getChars(0, str.length(), array, length - str.length());
        return new String(array);
    }


    public static String editLength(String str, char ch, int length) {
        if (str == null ) {
            return null;
        }

        if (str.length() > length) {
            return str.substring(str.length()-length);
        }

        char[] array = new char[length];
        Arrays.fill(array, ch);

        str.getChars(0, str.length(), array, length - str.length());
        return new String(array);
    }

    public static Date dateToString(String date) {
        Date dt = null;
        try {
            dt = new java.sql.Date( format.parse(date).getTime());
        } catch (Exception e ) {
        	e.printStackTrace();
        }
        return dt;
    }
    
    public static Date stringToDate(String date) {
        Date dt = null;
        try {
            dt = new java.sql.Date( format2.parse(date).getTime());
        } catch (Exception e ) {
        	e.printStackTrace();
        }
        return dt;
    }
    
    public static String getShipperCode (String dcUnitId) {
    	String dcId = editLength(dcUnitId,'0', 4);
		if(dcUnitId!= null) {
			if(dcUnitId.length()==7)
				return "D0"+ dcId;
			else if (dcUnitId.length()==4)
				return "K"+ dcId;
		}
		return null;
    }
}
