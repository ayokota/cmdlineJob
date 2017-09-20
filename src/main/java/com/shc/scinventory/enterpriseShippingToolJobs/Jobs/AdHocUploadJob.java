package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.AdhocAddressBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.AdHocShipAddrDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.ThirdPartyShipperDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.EnterpriseShippingToolUtil;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.FileReader;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.ListUtils;

@Component
public class AdHocUploadJob {
    private static final Logger LOG = Logger.getLogger(AdHocUploadJob.class);

    @Autowired
    AdHocShipAddrDao adHocShipAddrDao;
    
    @Autowired
    ThirdPartyShipperDao thirdPartyShipperDao;
    
	public void run() {
		System.out.println("Adhoc upload job processor");
		
		System.out.println("uploading third party addr");
		test2();
		System.out.println("uploading regular addr");
		test();
		
		
		//uploadAddressBook();
		//uploadThirdPartyAddrBook();
		
	}
	
	public void test() {
		try {
			String content = FileReader.readResource("AddressBookdata.csv");
			
//			Iterable<CSVRecord> records = CSVParser.parse(content, CSVFormat.MYSQL.withDelimiter(',').withRecordSeparator("\n").withQuoteMode(QuoteMode.MINIMAL).withHeader());
			Iterable<CSVRecord> records = CSVParser.parse(content, CSVFormat.DEFAULT.withDelimiter('|').withRecordSeparator("\n").withQuoteMode(QuoteMode.MINIMAL).withHeader());

			for (CSVRecord cols : records) {
				
				AdhocAddressBean adhocAddressBean = new AdhocAddressBean();

				
				if (!cols.get(1).equals("NULL")) adhocAddressBean.setDcUnitId(EnterpriseShippingToolUtil.convertShipperCodeToDcUnits(cols.get(1).trim()));				
				if (!cols.get(2).equals("NULL")) adhocAddressBean.setAddrId(cols.get(2).trim());
				adhocAddressBean.setUpsAcc("");
				if (!cols.get(3).equals("NULL")) adhocAddressBean.setCompany(cols.get(3).trim());
				if (!cols.get(4).equals("NULL")) adhocAddressBean.setContact(cols.get(4).trim());;
				if (!cols.get(5).equals("NULL")) adhocAddressBean.setAddr1(cols.get(5).trim());
				if (!cols.get(6).equals("NULL")) adhocAddressBean.setAddr2(cols.get(6).trim());
				if (!cols.get(7).equals("NULL")) adhocAddressBean.setAddr3(cols.get(7).trim());
				if (!cols.get(8).equals("NULL")) adhocAddressBean.setCity(cols.get(8).trim());
				if (!cols.get(9).equals("NULL")) adhocAddressBean.setState(cols.get(9).trim());
				if (!cols.get(10).equals("NULL")) adhocAddressBean.setZip(filterPhoneAndZip(cols.get(10)));
				 adhocAddressBean.setCountry("US");
				if (!cols.get(11).equals("NULL")) adhocAddressBean.setPhone(filterPhoneAndZip(cols.get(11)));
				
				//System.out.println(JSONSerializer.serialize(adhocAddressBean));
				adHocShipAddrDao.insert(adhocAddressBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void uploadAddressBook () {
		try {
			List<AdhocAddressBean> AdhocAddresses = new LinkedList<AdhocAddressBean> ();
			String content = FileReader.readResource("AddressBookdata.csv");
			List<String> lines = ListUtils.rawToList(content);
			lines.remove(0);
			for (String line : lines) {
				List<String> cols = ListUtils.lineToList(line, "|");
				AdhocAddressBean adhocAddressBean = new AdhocAddressBean();

				try {
					
					if(cols.get(9).length()>2) {
						//System.out.println(JSONSerializer.serialize(cols));
					} else {
						if (!cols.get(1).equals("NULL")) adhocAddressBean.setDcUnitId(EnterpriseShippingToolUtil.convertShipperCodeToDcUnits(cols.get(1)));				
						if (!cols.get(2).equals("NULL")) adhocAddressBean.setAddrId(cols.get(2).trim());
						adhocAddressBean.setUpsAcc("");
						if (!cols.get(3).equals("NULL")) adhocAddressBean.setCompany(cols.get(3).trim());
						if (!cols.get(4).equals("NULL")) adhocAddressBean.setContact(cols.get(4).trim());;
						if (!cols.get(5).equals("NULL")) adhocAddressBean.setAddr1(cols.get(5).trim());
						if (!cols.get(6).equals("NULL")) adhocAddressBean.setAddr2(cols.get(6).trim());
						if (!cols.get(7).equals("NULL")) adhocAddressBean.setAddr3(cols.get(7).trim());
						if (!cols.get(8).equals("NULL")) adhocAddressBean.setCity(cols.get(8).trim());
						if (!cols.get(9).equals("NULL")) adhocAddressBean.setState(cols.get(9).trim());
						if (!cols.get(10).equals("NULL")) adhocAddressBean.setZip(filterPhoneAndZip(cols.get(10)));
						 adhocAddressBean.setCountry("US");
						if (!cols.get(11).equals("NULL")) adhocAddressBean.setPhone(filterPhoneAndZip(cols.get(11)));
						adHocShipAddrDao.insert(adhocAddressBean);
					}
				} catch (Exception e) {
					
					System.out.println(JSONSerializer.serialize(cols));
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void uploadThirdPartyAddrBook () {
		try {
			List<AdhocAddressBean> AdhocAddresses = new LinkedList<AdhocAddressBean> ();
			String content = FileReader.readResource("thirdPartyshipperAddress.csv");
			List<String> lines = ListUtils.rawToList(content);
			System.out.println(lines.size());

			lines.remove(0);
			for (String line : lines) {
				List<String> cols = ListUtils.lineToList(line, "|");
				AdhocAddressBean adhocAddressBean = new AdhocAddressBean();

				try {
					

						if (!cols.get(1).equals("NULL")) adhocAddressBean.setDcUnitId(EnterpriseShippingToolUtil.convertShipperCodeToDcUnits(cols.get(1)));				
						if (!cols.get(2).equals("NULL")) adhocAddressBean.setAddrId(cols.get(2).trim());
						if (!cols.get(3).equals("NULL")) adhocAddressBean.setUpsAcc(cols.get(3).trim());
						if (!cols.get(4).equals("NULL")) adhocAddressBean.setCompany(cols.get(4).trim());
						if (!cols.get(5).equals("NULL")) adhocAddressBean.setContact(cols.get(5).trim());;
						if (!cols.get(6).equals("NULL")) adhocAddressBean.setAddr1(cols.get(6).trim());
						if (!cols.get(7).equals("NULL")) adhocAddressBean.setAddr2(cols.get(7).trim());
						if (!cols.get(8).equals("NULL")) adhocAddressBean.setAddr3(cols.get(8).trim());
						if (!cols.get(9).equals("NULL")) adhocAddressBean.setCity(cols.get(9).trim());
						if (!cols.get(10).equals("NULL")) adhocAddressBean.setState(cols.get(10).trim());
						if (!cols.get(11).equals("NULL")) adhocAddressBean.setZip(filterPhoneAndZip(cols.get(11)));
						 adhocAddressBean.setCountry("US");
						if (!cols.get(13).equals("NULL")) adhocAddressBean.setPhone(filterPhoneAndZip(cols.get(13)));
						System.out.println(JSONSerializer.serialize(adhocAddressBean));
						//thirdPartyShipperDao.insert(adhocAddressBean);

				} catch (Exception e) {
					
					System.out.println(JSONSerializer.serialize(cols));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String filterPhoneAndZip (String input) {
		String output = "";
		try {
			if(!input.equals("NULL")) {
				
				output = input.replace("_", "")
							.replace("(", "")
							.replace(")", "")
							.replace("-", "")
							.replace(" ", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public void test2() {
		try {
			String content = FileReader.readResource("thirdPartyshipperAddress.csv");
			
//			Iterable<CSVRecord> records = CSVParser.parse(content, CSVFormat.MYSQL.withDelimiter(',').withRecordSeparator("\n").withQuoteMode(QuoteMode.MINIMAL).withHeader());
			Iterable<CSVRecord> records = CSVParser.parse(content, CSVFormat.DEFAULT.withDelimiter('|').withRecordSeparator("\n").withQuoteMode(QuoteMode.MINIMAL).withHeader());

			for (CSVRecord cols : records) {
				AdhocAddressBean adhocAddressBean = new AdhocAddressBean();

				try {
					

						if (!cols.get(1).equals("NULL")) adhocAddressBean.setDcUnitId(EnterpriseShippingToolUtil.convertShipperCodeToDcUnits(cols.get(1).trim()));				
						if (!cols.get(2).equals("NULL")) adhocAddressBean.setAddrId(cols.get(2).trim());
						if (!cols.get(3).equals("NULL")) adhocAddressBean.setUpsAcc(cols.get(3).trim());
						if (!cols.get(4).equals("NULL")) adhocAddressBean.setCompany(cols.get(4).trim());
						if (!cols.get(5).equals("NULL")) adhocAddressBean.setContact(cols.get(5).trim());;
						if (!cols.get(6).equals("NULL")) adhocAddressBean.setAddr1(cols.get(6).trim());
						if (!cols.get(7).equals("NULL")) adhocAddressBean.setAddr2(cols.get(7).trim());
						if (!cols.get(8).equals("NULL")) adhocAddressBean.setAddr3(cols.get(8).trim());
						if (!cols.get(9).equals("NULL")) adhocAddressBean.setCity(cols.get(9).trim());
						if (!cols.get(10).equals("NULL")) adhocAddressBean.setState(cols.get(10).trim());
						if (!cols.get(11).equals("NULL")) adhocAddressBean.setZip(filterPhoneAndZip(cols.get(11)));
						 adhocAddressBean.setCountry("US");
						if (!cols.get(13).equals("NULL")) adhocAddressBean.setPhone(filterPhoneAndZip(cols.get(13)));
						//System.out.println(JSONSerializer.serialize(adhocAddressBean));
						thirdPartyShipperDao.insert(adhocAddressBean);

				} catch (Exception e) {
					
					System.out.println(JSONSerializer.serialize(cols));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
