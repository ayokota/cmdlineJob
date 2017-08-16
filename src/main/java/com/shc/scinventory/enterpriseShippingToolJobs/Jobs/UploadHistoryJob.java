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
public class UploadHistoryJob {
	
	@Autowired
	PackageInfoDao packageInfoDao;
	
	public void run(String file) {

		if(file==null || file.isEmpty()) {
			System.out.println("Please provide a file of history record");
			return;
		}
		
		System.out.println("running upload history job" );
		
		try {
			String content = FileReader.readResource(file);
			List<String> lines = ListUtils.rawToList(content);
			List<PackageInfoBean> beans = createBeans(lines);
			
			packageInfoDao.insertPackageInfo(beans);
			//packageInfoDao.updatePackageInfo(beans);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<PackageInfoBean> createBeans (List<String> lines) {
		List<PackageInfoBean> beans = new LinkedList<PackageInfoBean>();
		
		try {
		    Map<String, Integer> pkgTracker = new HashMap<String, Integer>();
			for(String line : lines) {
				List<String> cols = ListUtils.lineToList(line, ",");
				PackageInfoBean bean = new PackageInfoBean();
				bean.setSuborderId(cols.get(1));
				bean.setOrderid(cols.get(0));
				bean.setTracking_no(cols.get(5));
				bean.setActual_shipmode(cols.get(4));
				bean.setActual_carrier(cols.get(3));
				bean.setLocn_nbr(EnterpriseShippingToolUtil.prepend(cols.get(6), '0', 7));
				bean.setLast_updated_ts(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				bean.setShipped_date(EnterpriseShippingToolUtil.stringToDate(cols.get(2).substring(0, 10)));
				
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
}
