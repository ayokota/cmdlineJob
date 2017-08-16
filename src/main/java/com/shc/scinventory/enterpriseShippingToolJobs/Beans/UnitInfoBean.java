package com.shc.scinventory.enterpriseShippingToolJobs.Beans;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class UnitInfoBean {
    private String unit_id;
    private String reason_cd;
    private String reason_desc;
    private boolean packsize_enabled;
    private boolean show_reason;
    private String ptr_sl;
    private String ptr_pl;
    private String label_sz;
    private boolean auto_print;
    private Date ship_date;
    private Date previous_ship_date;
    private Time pickup_time;
    private String type_of_update;
    private boolean processed_same_day;

    public UnitInfoBean () {
        this.unit_id = "";
        this.reason_cd = "";
        this.reason_desc = "";
        this.packsize_enabled = false;
        this.show_reason = false;
        this.ptr_sl = "";
        this.ptr_pl = "";
        this.label_sz = "";
        this.auto_print = false;
    }

    public UnitInfoBean (String unit_id) {
        this.unit_id = unit_id;
        this.reason_cd = "1";
        this.reason_desc = "1";
        this.packsize_enabled = false;
        this.show_reason = false;
        this.ptr_sl = "";
        this.ptr_pl = "";
        this.label_sz = "";
        this.auto_print = false;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getReason_cd() {
        return reason_cd;
    }

    public void setReason_cd(String reason_cd) {
        this.reason_cd = reason_cd;
    }

    public String getReason_desc() {
        return reason_desc;
    }

    public void setReason_desc(String reason_desc) {
        this.reason_desc = reason_desc;
    }

    public boolean isPacksize_enabled() {
        return packsize_enabled;
    }

    public void setPacksize_enabled(boolean packsize_enabled) {
        this.packsize_enabled = packsize_enabled;
    }

    public boolean isShow_reason() {
        return show_reason;
    }

    public void setShow_reason(boolean show_reason) {
        this.show_reason = show_reason;
    }

    public String getPtr_sl() {
        return ptr_sl;
    }

    public void setPtr_sl(String ptr_sl) {
        this.ptr_sl = ptr_sl;
    }

    public String getPtr_pl() {
        return ptr_pl;
    }

    public void setPtr_pl(String ptr_pl) {
        this.ptr_pl = ptr_pl;
    }

    public String getLabel_sz() {
        return label_sz;
    }

    public void setLabel_sz(String label_sz) {
        this.label_sz = label_sz;
    }

    public boolean isAuto_print() {
        return auto_print;
    }

    public void setAuto_print(boolean auto_print) {
        this.auto_print = auto_print;
    }

    public Date getShip_date() {
        return ship_date;
    }

    public void setShip_date(Date ship_date) {
        this.ship_date = ship_date;
    }

    public Date getPrevious_ship_date() {
        return previous_ship_date;
    }

    public void setPrevious_ship_date(Date previous_ship_date) {
        this.previous_ship_date = previous_ship_date;
    }

    
    public Time getPickup_time() {
		return pickup_time;
	}

	public void setPickup_time(Time pickup_time) {
		this.pickup_time = pickup_time;
	}

	public String getType_of_update() {
        return type_of_update;
    }

    public void setType_of_update(String type_of_update) {
        this.type_of_update = type_of_update;
    }

    public boolean isProcessed_same_day() {
        return processed_same_day;
    }

    public void setProcessed_same_day(boolean processed_same_day) {
        this.processed_same_day = processed_same_day;
    }
}
