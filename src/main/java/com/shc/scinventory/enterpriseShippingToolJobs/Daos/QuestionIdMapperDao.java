package com.shc.scinventory.enterpriseShippingToolJobs.Daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.QuestionIdMapperBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Exception.ShippingToolException;

@Component
public class QuestionIdMapperDao {

    private static final Logger LOG = Logger.getLogger(QuestionIdMapperDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* queries */
    private static final String selectAll = "select * from question_id_mapper";
    private final static String insertQuestion = "insert into question_id_mapper (question_text) " +
                " values ( ?)";
    private final static String selectQuestion = "select id from question_id_mapper where question_text=?";
    /* cache */
    private Map<Integer, String> questionMapperCache;

    public void init() {
        try {
            Map<Integer, String> newQuestionMapperCache = new HashMap<Integer, String>();

            List<QuestionIdMapperBean> questionMappers = getAllQuestionMappers();
            for(QuestionIdMapperBean questionMapper : questionMappers) {
                newQuestionMapperCache.put(questionMapper.getId(), questionMapper.getQuestion_text());
            }
            questionMapperCache = newQuestionMapperCache;

        } catch (ShippingToolException ste) {
            throw new ShippingToolException(ste.getMessage(), ste.getCause());
        } catch (Exception e) {
            throw new ShippingToolException("An error has occured in init "
                    + e.getMessage(), e.getCause());
        }
    }

    private List<QuestionIdMapperBean> getAllQuestionMappers () {
        List<QuestionIdMapperBean> questionMappers;
        try {
            questionMappers =  jdbcTemplate.query(selectAll, new BeanPropertyRowMapper(QuestionIdMapperBean.class));
        } catch (Exception e) {
            throw new ShippingToolException("An error has occured when pull all" +
                    " record from question_id_mapper table with error "
                    + e.getMessage(), e.getCause());
        }
        return questionMappers;
    }
    
    public void insert(String questionText) {
        try {
            jdbcTemplate.update(insertQuestion,
            		questionText
            );
        } catch (Exception e) {
            throw  new ShippingToolException("An error has occured in insert method with error msg: " + e.getMessage()
                    + "\nOriginal data: "+ questionText, e);
        }
    }

    public Integer getQuestionID(String questionText) {
    	Integer id = 0;
        try {
            id = jdbcTemplate.queryForInt(selectQuestion, questionText);
        } catch (Exception e) {
            throw  new ShippingToolException("An error has occured in selectQuestion method with error msg: " + e.getMessage()
                    + "\nOriginal data: "+ questionText, e);
        }
        return id;
    }
    
    public String getQuestion(int id) {
        return questionMapperCache.get(id);
    }
}