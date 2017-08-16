package com.shc.scinventory.enterpriseShippingToolJobs.Beans;

import java.sql.Date;
import java.sql.Timestamp;

public class PackageInfoBean {
    private String suborderId;
    private String orderid;
    private String box_used;
    private String tracking_no;
    private Date shipped_date;
    private String suggested_carrier;
    private String modified_by;
    private Timestamp last_updated_ts;
    private String items;
    private String suggested_shipmode;
    private String actual_shipmode;
    private String actual_carrier;
    private int package_sequence;
    private String locn_nbr;
    private Date promise_date;
    private String reason_cd;
    private String cstm_bx_ht;
    private String cstm_bx_wdt;
    private String cstm_bx_wgt;
    private String cstm_bx_lth;
    private Integer msn;

    public PackageInfoBean () {
    	box_used = "CustomBox";
    	reason_cd = "N/A";
    }
    
    public String getSuborderId() {
        return suborderId;
    }

    public void setSuborderId(String suborderId) {
        this.suborderId = suborderId;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getBox_used() {
        return box_used;
    }

    public void setBox_used(String box_used) {
        this.box_used = box_used;
    }

    public String getTracking_no() {
        return tracking_no;
    }

    public void setTracking_no(String tracking_no) {
        this.tracking_no = tracking_no;
    }

    public String getSuggested_carrier() {
        return suggested_carrier;
    }

    public void setSuggested_carrier(String suggested_carrier) {
        this.suggested_carrier = suggested_carrier;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getSuggested_shipmode() {
        return suggested_shipmode;
    }

    public void setSuggested_shipmode(String suggested_shipmode) {
        this.suggested_shipmode = suggested_shipmode;
    }

    public String getActual_shipmode() {
        return actual_shipmode;
    }

    public void setActual_shipmode(String actual_shipmode) {
        this.actual_shipmode = actual_shipmode;
    }

    public String getActual_carrier() {
        return actual_carrier;
    }

    public void setActual_carrier(String actual_carrier) {
        this.actual_carrier = actual_carrier;
    }

    public int getPackage_sequence() {
        return package_sequence;
    }

    public void setPackage_sequence(int package_sequence) {
        this.package_sequence = package_sequence;
    }

    public String getLocn_nbr() {
        return locn_nbr;
    }

    public void setLocn_nbr(String locn_nbr) {
        this.locn_nbr = locn_nbr;
    }


    public String getReason_cd() {
        return reason_cd;
    }

    public void setReason_cd(String reason_cd) {
        this.reason_cd = reason_cd;
    }

    public String getCstm_bx_ht() {
        return cstm_bx_ht;
    }

    public void setCstm_bx_ht(String cstm_bx_ht) {
        this.cstm_bx_ht = cstm_bx_ht;
    }

    public String getCstm_bx_wdt() {
        return cstm_bx_wdt;
    }

    public void setCstm_bx_wdt(String cstm_bx_wdt) {
        this.cstm_bx_wdt = cstm_bx_wdt;
    }

    public String getCstm_bx_wgt() {
        return cstm_bx_wgt;
    }

    public void setCstm_bx_wgt(String cstm_bx_wgt) {
        this.cstm_bx_wgt = cstm_bx_wgt;
    }

    public Date getShipped_date() {
        return shipped_date;
    }

    public void setShipped_date(Date shipped_date) {
        this.shipped_date = shipped_date;
    }

    public Timestamp getLast_updated_ts() {
        return last_updated_ts;
    }

    public void setLast_updated_ts(Timestamp last_updated_ts) {
        this.last_updated_ts = last_updated_ts;
    }




    public Date getPromise_date() {
        return promise_date;
    }

    public void setPromise_date(Date promise_date) {
        this.promise_date = promise_date;
    }

    public String getCstm_bx_lth() {
        return cstm_bx_lth;
    }

    public void setCstm_bx_lth(String cstm_bx_lth) {
        this.cstm_bx_lth = cstm_bx_lth;
    }

    public Integer getMsn() {
        if(msn==null)
            msn=0;
        return msn;
    }

    public void setMsn(Integer msn) {
        if(msn==null)
            msn=0;
        this.msn = msn;
    }
}
