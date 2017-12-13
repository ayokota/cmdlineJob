package com.shc.scinventory.enterpriseShippingToolJobs.Beans;

public class UnitTypeMapperBean {
    private Integer unit_index;
    private String unit_type;

    public UnitTypeMapperBean () {
        this.unit_index = 0;
        this.unit_type = "";
    }

    public UnitTypeMapperBean(Integer unit_index, String unit_type) {
        this.unit_index = unit_index;
        this.unit_type = unit_type;
    }

    public Integer getUnit_index() {
        return unit_index;
    }

    public void setUnit_index(Integer unit_index) {
        this.unit_index = unit_index;
    }

    public String getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(String unit_type) {
        this.unit_type = unit_type;
    }
}
