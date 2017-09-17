package com.shc.scinventory.enterpriseShippingToolJobs.Beans;

public class AdhocAddressBean {
	private String addrId;
    private String upsAcc;
    private String company;
    private String contact;
    private String addr1;
    private String addr2;
    private String addr3;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    private String dcUnitId;

    public AdhocAddressBean () {
    	this.addrId = "";
    	this.upsAcc = "";
    	this.company = "";
    	this.contact = "";
    	this.addr1 = "";
    	this.addr2 = "";
    	this.addr3 = "";
    	this.city = "";
    	this.state = "";
    	this.zip = "";
    	this.country = "";
    	this.phone = "";
    	this.dcUnitId = "";
    }
    
    
    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr3() {
        return addr3;
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDcUnitId() {
        return dcUnitId;
    }

    public void setDcUnitId(String dcUnitId) {
        this.dcUnitId = dcUnitId;
    }

    public String getUpsAcc() {
        return upsAcc;
    }

    public void setUpsAcc(String upsAcc) {
        this.upsAcc = upsAcc;
    }
}
