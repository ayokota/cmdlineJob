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

import com.shc.ecom.fastpromise.dao.casimpl.ConfigurationDaoImpl;
import com.shc.ecom.fastpromise.dao.caspojo.ConfigurationBean;

@Component
public class ConfigurationDao {

    private static final Logger LOG = Logger.getLogger(ConfigurationDaoImpl.class);

    private static final String selectAll = "select * from configuration";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Map<String, ConfigurationBean> configurationCache;

    public Map<String, ConfigurationBean> getConfigurationCache() {
        return configurationCache;
    }

    
    public String getProperty(String key) {
        String value = null;
        if(configurationCache!=null && !configurationCache.isEmpty() && key!=null && configurationCache.containsKey(key)) {
            ConfigurationBean configuration = configurationCache.get(key);
            if(configuration.isActive()) {
                value = configuration.getValue();
            }
        }
        return value;
    }

    @PostConstruct
    public void init() {
        List<ConfigurationBean> configurations = getAllConfigs();
        Map<String, ConfigurationBean> newConfigurationCache = new HashMap<String, ConfigurationBean>();
        if(configurations!=null && !getAllConfigs().isEmpty()) {
            for(ConfigurationBean configuration : configurations) {
                newConfigurationCache.put(configuration.getName(), configuration);
            }
        }
        configurationCache = newConfigurationCache;
    }

    public List<ConfigurationBean> getAllConfigs() {
        List <ConfigurationBean> configurations = null;
        try {

            configurations =
                    jdbcTemplate.query(selectAll, new BeanPropertyRowMapper(ConfigurationBean.class));
        } catch (Exception e) {
        	e.printStackTrace();
            LOG.error("An error has occured in getAllConfigs method with error msg: " + e.getMessage(), e);
        }
        return configurations;
    }
}
