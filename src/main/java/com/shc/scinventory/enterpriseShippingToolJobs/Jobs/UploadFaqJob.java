package com.shc.scinventory.enterpriseShippingToolJobs.Jobs;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.FaqBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Bindings.FAQInstance;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.FaqDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.QuestionIdMapperDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Daos.UnitTypeMapperDao;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.EnterpriseShippingToolConstants;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.EnterpriseShippingToolUtil;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.FileReader;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.JSONSerializer;
import com.shc.scinventory.enterpriseShippingToolJobs.Utilities.ListUtils;

@Component
public class UploadFaqJob {
    private static final Logger LOG = Logger.getLogger(UpdatePickupTimeJob.class);

    @Autowired
    private FaqDao faqDao;

    @Autowired
    private QuestionIdMapperDao questionIdMapperDao;

    @Autowired
    private UnitTypeMapperDao unitTypeMapperDao;
    
	public void run(String file) {
		if(file==null || file.isEmpty()) {
			System.out.println("Please provide a file of a list FAQ for upload");
			return;
		}
		
		try {
			init();
			
			String content = FileReader.readFile(file);
			List<String> lines = ListUtils.lineToList(content, "\n");
			
			for(String line : lines ) {
				List<String> segments = ListUtils.lineToList(line, "`");
				String questionText = segments.get(0);
				String unitTypes = segments.get(1);
				String answer = segments.get(2);
				
//				System.out.println(segments);
//				System.out.println(questionText);
//				System.out.println(unitTypes);
//				System.out.println(answer);
				//insert question and get question ID
				try {
					questionIdMapperDao.insert(questionText);
				} catch (Exception e) {
					//System.out.println(questionText + " already exists!");
				}
				Integer questionID = questionIdMapperDao.getQuestionID(questionText);
				
				//get visibility value from unitType. This requires pulling unitTypeMappers
				Integer visibility = 0;
				if(unitTypes.equals(EnterpriseShippingToolConstants.UNIT_TYPE_ALL)) {
					visibility = EnterpriseShippingToolConstants.FULL_VISIBILITY;
				} else {
//					Integer unit_index = unitTypeMapperDao.getUnitTypeIndex(unitType);
//					visibility = EnterpriseShippingToolUtil.setBit(visibility, unit_index);
//					System.out.println(unitType + " : " + unit_index + " : " + unit_index);
					for(String unitType: ListUtils.lineToList(unitTypes, "\\|") ) {
						Integer unit_index = unitTypeMapperDao.getUnitTypeIndex(unitType);
						visibility = EnterpriseShippingToolUtil.setBit(visibility, unit_index);
					}

				}
				//divide answer into segments 
				Integer answerLength = answer.length();
				Integer division = (answerLength/EnterpriseShippingToolConstants.ANSWER_COL_LENGTH) + 1;
				
				List<FaqBean> faqBeans = new LinkedList<FaqBean>();
				for(int i = 0, beginIndex = 0; i < division; i++, beginIndex+=EnterpriseShippingToolConstants.ANSWER_COL_LENGTH) {
					int endIndex = beginIndex + EnterpriseShippingToolConstants.ANSWER_COL_LENGTH;
					if(endIndex > answerLength) endIndex = answerLength;
					String partialAnswer = answer.substring(beginIndex, endIndex);
					FaqBean faqBean = new FaqBean(visibility, questionID, i, partialAnswer );
					faqBeans.add(faqBean);
					//System.out.println(JSONSerializer.serialize(faqBean));
				}
				faqDao.insertBatch(faqBeans);

				
				//insert into faq table
			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		unitTypeMapperDao.init();
	}
}
