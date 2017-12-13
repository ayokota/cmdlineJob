package com.shc.scinventory.enterpriseShippingToolJobs.Daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.FaqBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Exception.ShippingToolException;

@Component
public class FaqDao {
    private static final Logger LOG = Logger.getLogger(FaqDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* queries */
    private static final String selectAll = "select * from faq";
    private static final String insertSql = "insert into faq (visibility, question_id, ans_order,  answer)" +
    			"values ( ?, ?, ?, ?);";
    /* cache */
    private List<FaqBean> faqCache;

    public void init() {
        try {
            List<FaqBean> newFaqCache = getAllFaqBean ();
            faqCache = newFaqCache;

        } catch (ShippingToolException ste) {
            ste.printStackTrace();
            throw new ShippingToolException(ste.getMessage(), ste.getCause());
        } catch (Exception e) {
            throw new ShippingToolException("An error has occured in init "
                    + e.getMessage(), e.getCause());
        }
    }

    public List<FaqBean> getAllFaqBean () {
        List<FaqBean> faqBeans;
        try {
            faqBeans =  jdbcTemplate.query(selectAll, new BeanPropertyRowMapper(FaqBean.class));
        } catch (Exception e) {
            throw new ShippingToolException("An error has occured when pull all" +
                    " record from question_id_mapper table with error "
                    + e.getMessage(), e.getCause());
        }
        return faqBeans;
    }

    public List<FaqBean> getFaqCache() {
        return faqCache;
    }
    
    public void insertBatch(final List<FaqBean> faqBeans) {
        jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
            	FaqBean faqBean =  faqBeans.get(i);

                preparedStatement.setInt(1, faqBean.getVisibility());
                preparedStatement.setInt( 2, faqBean.getQuestion_id());
                preparedStatement.setInt( 3, faqBean.getAns_order());
                preparedStatement.setString( 4, faqBean.getAnswer());
         
            }

            @Override
            public int getBatchSize() {
                return faqBeans.size();
            }
        });
    }
}
