package com.juggleclouds.bloodbankcet.classes;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by jayadeep on 10/22/16.
 */
public class User extends SugarRecord implements Serializable{
    public String name;
    public int year; //joining year. Set 0 for not applicable
    public String department;
    public String address;
    public String station;
    public long mobile;
    public String email;
    public String blood;
    public int weight;
    public boolean willing;
    public String comments;

    @Override
    public String toString() {
        return name;
    }
}
