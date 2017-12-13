package com.shc.scinventory.enterpriseShippingToolJobs.Daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.shc.scinventory.enterpriseShippingToolJobs.Beans.UnitTypeMapperBean;
import com.shc.scinventory.enterpriseShippingToolJobs.Exception.ShippingToolException;

@Component
public class UnitTypeMapperDao{
    private static final Logger LOG = Logger.getLogger(UnitTypeMapperDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* queries */
    private static final String selectAll = "select * from unit_type_mapper";

    /* cache */
    private Map<String, Integer> unitTypeMapperCache;

    public void init() {
        try {
            Map<String, Integer> newUnitTypeMapperCache = new HashMap<String, Integer>();

            List<UnitTypeMapperBean> unitTypeMappers = getAllUnitTypeMapping();
            for(UnitTypeMapperBean unitTypeMapper : unitTypeMappers) {
                newUnitTypeMapperCache.put( unitTypeMapper.getUnit_type(), unitTypeMapper.getUnit_index());
            }
            unitTypeMapperCache = newUnitTypeMapperCache;

        } catch (ShippingToolException ste) {
            throw new ShippingToolException(ste.getMessage(), ste.getCause());
        } catch (Exception e) {
            throw new ShippingToolException("An error has occured in init "
                    + e.getMessage(), e.getCause());
        }
    }

    private List<UnitTypeMapperBean> getAllUnitTypeMapping () {
        List<UnitTypeMapperBean> unitTypeMappers;
        try {
            unitTypeMappers =  jdbcTemplate.query(selectAll, new BeanPropertyRowMapper(UnitTypeMapperBean.class));
        } catch (Exception e) {
            throw new ShippingToolException("An error has occured when pull all" +
                    " record from unit_type_mapper table with error "
                    + e.getMessage(), e.getCause());
        }
        return unitTypeMappers;

    }

    public Integer getUnitTypeIndex (String unitType) {
        if(!unitTypeMapperCache.containsKey(unitType)) {
            return -1;
        } else {
            return unitTypeMapperCache.get(unitType);
        }

    }


}
