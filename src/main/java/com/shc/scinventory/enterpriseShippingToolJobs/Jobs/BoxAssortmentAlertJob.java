package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.BOS.GetBoxInfo.BoxInfo;
import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.BOS.GetBoxInfo.GetAllBoxInfoResponse;
import com.shc.scinventory.enterpriseShippingToolJobs.Clients.BosClient;
import com.shc.scinventory.enterpriseShippingToolJobs.Clients.SmtpClient;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.UnitInfoDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.ListUtils;

@Component
public class BoxAssortmentAlertJob {
    private static final Logger LOG = Logger.getLogger(BoxAssortmentAlertJob.class);

    @Autowired
    UnitInfoDao unitInfoDao;
    
    @Autowired
    BosClient bosClient;
    
    @Autowired
    SmtpClient smtpClient;
    
    @Value("${SEARS_EMAIL_LIST}")
    private String searsEmailList;
    
    @Value("${KMART_EMAIL_LIST}")
    private String kmartEmailList;
    
    public void run() {
		System.out.println("Box Assortment Alert job running" );
		LOG.error("Box Assortment Alert job running" );

		try {			
			Map<String, Map<String, Boolean> > boxAssortmentData = gatherBoxAssortmentData ();
			List<String> boxTypes = extractBoxType (boxAssortmentData);
//			System.out.println(JSONSerializer.serialize(boxTypes));
//			processSearsBoxAssortment(boxAssortmentData);
//			processKmartBoxAssortment(boxAssortmentData);
			sendSearsSpreadSheet (boxAssortmentData, boxTypes);
			sendKmartSpreadSheet (boxAssortmentData, boxTypes);

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("An exception has occred in run method for auto ship date job with msg " + e.getMessage(), e);
		}
	}
    
    public Map<String, Map<String, Boolean> > gatherBoxAssortmentData () {
		Map<String, Map<String, Boolean> > boxAssortmentData = new HashMap<String, Map<String, Boolean> > ();
		try {
			List<String> dcUnitList = unitInfoDao.getListOfDcs();

			for(String dcUnit : dcUnitList) {
				Map<String, Boolean> boxAssortmentMap = new HashMap<String, Boolean> ();
				String response = bosClient.getBoxInfo(dcUnit);
				GetAllBoxInfoResponse getAllBoxInfoResponse = JSONSerializer.deserialize(response, GetAllBoxInfoResponse.class);
				if(getAllBoxInfoResponse.getStatus().equals("SUCCESS")) {
					//boxAssortmentMap.put(key, value)
					for(BoxInfo boxInfo: getAllBoxInfoResponse.getBoxInfo()) {
						boxAssortmentMap.put(boxInfo.getBoxId(), boxInfo.isActive());
					}
				}
				if(boxAssortmentMap.size() >0) {
					boxAssortmentData.put(dcUnit, boxAssortmentMap);
				}
			}
		} catch (Exception e) {
			LOG.error("An exception has occred in gatherBoxAssortmentData with msg " + e.getMessage(), e);
		}
		return boxAssortmentData;
    }
    
    public void processSearsBoxAssortment (Map<String, Map<String, Boolean> > boxAssortmentData) {
    	try {
    		StringBuilder sb = new StringBuilder();
    		for (String dcUnitId : boxAssortmentData.keySet()) {
    			if(dcUnitId.length()==7) {
    				//System.out.println(dcUnitId);
    				sb.append("DC Unit: ").append(dcUnitId).append("\n");
    				//.append().append()
    				for(String boxId : boxAssortmentData.get(dcUnitId).keySet()) {
    					sb.append(boxId).append(" : ").append(boxAssortmentData.get(dcUnitId).get(boxId)).append("\n");
    				}
    				sb.append("\n");
    			}
    		}
    		
    		//smtpClient.send(sb.toString(), searsEmailList, "Sears Box Assortment");
    		for(String email : ListUtils.lineToList(searsEmailList, ",")) {
    			smtpClient.send(sb.toString(), email, "Sears Box Assortment");
    		}
    	} catch (Exception e) {
			LOG.error("An exception has occred in processSearsBoxAssortment with msg " + e.getMessage(), e);
    	}
    }
    
    public void processKmartBoxAssortment (Map<String, Map<String, Boolean> > boxAssortmentData) {
    	try {
    		StringBuilder sb = new StringBuilder();
    		for (String dcUnitId : boxAssortmentData.keySet()) {
    			if(dcUnitId.length()==4) {
    				//System.out.println(dcUnitId);
    				sb.append("DC Unit: ").append(dcUnitId).append("\n");
    				//.append().append()
    				for(String boxId : boxAssortmentData.get(dcUnitId).keySet()) {
    					sb.append(boxId).append(" : ").append(boxAssortmentData.get(dcUnitId).get(boxId)).append("\n");
    				}
    				sb.append("\n");
    			}
    		}
    		
    		//smtpClient.send(sb.toString(), kmartEmailList, "Kmart Box Assortment");
    		for(String email : ListUtils.lineToList(kmartEmailList, ",")) {
    			smtpClient.send(sb.toString(), email, "Sears Box Assortment");
    		}
    	} catch (Exception e) {
			LOG.error("An exception has occred in processKmartBoxAssortment with msg " + e.getMessage(), e);
    	}
    }
    
    public void sendSearsSpreadSheet (Map<String, Map<String, Boolean> > boxAssortmentData, List<String> boxTypes) {
    	try {
    		XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Sears Box Assortments");
            int rowNum = 0;

			Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            Cell cell = row.createCell(colNum++);
			for(String boxId : boxTypes) {
                cell = row.createCell(colNum++);
                cell.setCellValue( (String) boxId );

			}
    		
    		for (String dcUnitId : boxAssortmentData.keySet()) {
                
    			if(dcUnitId.length()==7) {
    				row = sheet.createRow(rowNum++);
                    colNum = 0;
	                cell = row.createCell(colNum++);
	                cell.setCellValue( (String) dcUnitId );
	    			for(String boxId : boxTypes) {
	                    cell = row.createCell(colNum++);
	                    if(boxAssortmentData.get(dcUnitId).containsKey(boxId)) {
	                    	cell.setCellValue( (String) boxAssortmentData.get(dcUnitId).get(boxId).toString() );
	                    } else {
	                    	cell.setCellValue( (String) "N/A" );
	                    }

	    			}
    			}
    		}
    		String fileName = "searsAssprt.xlsx";
    		try {
                FileOutputStream outputStream = new FileOutputStream(fileName);
                workbook.write(outputStream);
                workbook.close();
        		for(String email : ListUtils.lineToList(searsEmailList, ",")) {
	                smtpClient.sendFile("Sears stores monthly box assortment in attachment.", email, "Sears Box Assortment", fileName);
        		}
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    	} catch (Exception e) {
			LOG.error("An exception has occred in sendSearsSpreadSheet with msg " + e.getMessage(), e);
    	}
    }
    
    public void sendKmartSpreadSheet (Map<String, Map<String, Boolean> > boxAssortmentData, List<String> boxTypes) {
    	try {
    		XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Sears Box Assortments");
            int rowNum = 0;

			Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            Cell cell = row.createCell(colNum++);
			for(String boxId : boxTypes) {
                cell = row.createCell(colNum++);
                cell.setCellValue( (String) boxId );

			}
    		
    		for (String dcUnitId : boxAssortmentData.keySet()) {
                
    			if(dcUnitId.length()==4) {
    				row = sheet.createRow(rowNum++);
                    colNum = 0;
	                cell = row.createCell(colNum++);
	                cell.setCellValue( (String) dcUnitId );
	    			for(String boxId : boxTypes) {
	                    cell = row.createCell(colNum++);
	                    if(boxAssortmentData.get(dcUnitId).containsKey(boxId)) {
	                    	cell.setCellValue( (String) boxAssortmentData.get(dcUnitId).get(boxId).toString() );
	                    } else {
	                    	cell.setCellValue( (String) "N/A" );
	                    }

	    			}
    			}
    		}
    		String fileName = "kmartAssprt.xlsx";
    		try {
                FileOutputStream outputStream = new FileOutputStream(fileName);
                workbook.write(outputStream);
                workbook.close();
        		for(String email : ListUtils.lineToList(kmartEmailList, ",")) {
	                smtpClient.sendFile("Kmart stores monthly box assortment in attachment.", email, "Kmart Box Assortment", fileName);
        		}
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    	} catch (Exception e) {
			LOG.error("An exception has occred in sendKmartSpreadSheet with msg " + e.getMessage(), e);
    	}
    }
    
    public List<String> extractBoxType (Map<String, Map<String, Boolean> > boxAssortmentData) {
    	List<String> boxTypes = new LinkedList<String>();
    	try {
    		for (String dcUnitId : boxAssortmentData.keySet()) {
    			if(dcUnitId.length()==7) {
    				for(String boxId : boxAssortmentData.get(dcUnitId).keySet()) {
    					if(!boxTypes.contains(boxId)) {
    						boxTypes.add(boxId);
    					}
    				}
    			}
    		}
    	} catch (Exception e) {
			LOG.error("An exception has occred in extractBoxType with msg " + e.getMessage(), e);
    	}
    	return boxTypes;
    }
}
