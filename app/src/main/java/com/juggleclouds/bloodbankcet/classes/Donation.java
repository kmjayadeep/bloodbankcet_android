package com.juggleclouds.bloodbankcet.classes;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by jayadeep on 2/25/17.
 */

public class Donation extends SugarRecord implements Serializable {
    public long id;
    public Duty duty;
    public String requirementBy;
    public String phone;
    public String patientName;
    public String hospital;
    public String blood;
    public long date;
    public User donor;
    public String certificateNo;
    public String remarks;
}
