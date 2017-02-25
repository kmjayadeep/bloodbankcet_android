package com.juggleclouds.bloodbankcet.classes;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by jayadeep on 2/25/17.
 */

public class Duty extends SugarRecord implements Serializable {
    public long id;
    public long date;
    public String name;
    public String mobile;
    public String remarks;

    @Override
    public String toString() {
        return name;
    }
}
