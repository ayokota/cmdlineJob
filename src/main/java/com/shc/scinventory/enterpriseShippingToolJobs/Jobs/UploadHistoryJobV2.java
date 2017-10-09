package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.PackageInfoBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.PackageInfoDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.EnterpriseShippingToolUtil;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.FileReader;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.ListUtils;

@Component
public class UploadHistoryJobV2 {
	
	@Autowired
	PackageInfoDao packageInfoDao;
	
	public void run(String file, String storeType) {

		if(file==null || file.isEmpty()) {
			System.out.println("Please provide a file of history record");
			return;
		}
		
		if(file==null || file.isEmpty()) {
			System.out.println("Please provide a storeType");
			return;
		}
		
		System.out.println("running upload history job V2" );
		
		try {
			String content = FileReader.readResource(file);
			List<String> lines = ListUtils.rawToList(content);
			//remove header
			lines.remove(0);

			
			List<PackageInfoBean> beans = createBeans(lines, storeType);
			
			//debug
//			System.out.println(storeType);
//			for (PackageInfoBean packageInfoBean : beans ) {
//				System.out.println(JSONSerializer.serialize(packageInfoBean));
//			}
			
//			try {
//				packageInfoDao.insertPackageInfo(beans);
//			} catch ()
			for(PackageInfoBean bean : beans) {
				List<PackageInfoBean> newList = new LinkedList<PackageInfoBean>();
				newList.add(bean);
				try {
					packageInfoDao.insertPackageInfo(newList);
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<PackageInfoBean> createBeans (List<String> lines, String storeType) {
		List<PackageInfoBean> beans = new LinkedList<PackageInfoBean>();
		
		try {
		    Map<String, Integer> pkgTracker = new HashMap<String, Integer>();
			for(String line : lines) {
				List<String> cols = ListUtils.lineToList(line, ",");
				PackageInfoBean bean = new PackageInfoBean();
				bean.setSuborderId(cols.get(1));
				bean.setOrderid(cols.get(0));
				bean.setTracking_no(cols.get(5));
				
				
				//bean.setActual_shipmode(cols.get(4));
				
				if(storeType.equalsIgnoreCase("sears")) {
					bean.setActual_shipmode(cols.get(4));
				} else if (storeType.equalsIgnoreCase("kmart")) {
					bean.setActual_shipmode(convertKmartShipmode(cols.get(4)));
				}
				
				
				
				
				bean.setActual_carrier(cols.get(3));
				
				if(storeType.equalsIgnoreCase("sears")) {
					bean.setLocn_nbr(EnterpriseShippingToolUtil.prepend(cols.get(6), '0', 7));
				} else if (storeType.equalsIgnoreCase("kmart")) {
					bean.setLocn_nbr(EnterpriseShippingToolUtil.prepend(cols.get(6), '0', 4));
				}
				
				bean.setLast_updated_ts(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				//bean.setShipped_date(EnterpriseShippingToolUtil.stringToDate(cols.get(2).substring(0, 10)));
				
				if(storeType.equalsIgnoreCase("sears")) {
					bean.setShipped_date(EnterpriseShippingToolUtil.stringToDateWithFormat(cols.get(2), "yyyy-MM-dd"));
					//bean.setShipped_date(EnterpriseShippingToolUtil.stringToDateWithFormat(cols.get(2), "MM/dd/yy"));
				} else if (storeType.equalsIgnoreCase("kmart")) {
					bean.setShipped_date(EnterpriseShippingToolUtil.stringToDateWithFormat(cols.get(2), "MM/dd/yyyy"));
				}
				
	            String key = cols.get(0) + cols.get(1);
	            if(pkgTracker.containsKey(key)) {
	                Integer num = pkgTracker.get(key) + 1;
	                pkgTracker.put(key, num);
	            } else {
	                pkgTracker.put(key, 1);
	            }
	            
	            bean.setPackage_sequence(pkgTracker.get(key));
	            
	            //System.out.println(JSONSerializer.serialize(bean));
	            beans.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return beans;
	}
	
	private String convertKmartShipmode (String shipmode) {
		String convertedShipode = shipmode;
		
		try  {
			if(shipmode!=null) {
				switch (shipmode) {
				case "Ground":
					convertedShipode = "GD";
					break;
				case "SUREPOST":
					convertedShipode = "GDE";
					break;
				case "2nd Day Air":
					convertedShipode = "SD";
					break;
				case "Next Day":
					convertedShipode = "ND";
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return convertedShipode;
	}
	
}
