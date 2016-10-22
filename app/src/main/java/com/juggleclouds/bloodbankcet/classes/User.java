package com.juggleclouds.bloodbankcet.classes;

import com.orm.SugarRecord;

/**
 * Created by jayadeep on 10/22/16.
 */
public class User extends SugarRecord{
    public String name;
    int year; //joining year. Set 0 for not applicable
    String department;
    String address;
    String station;
    long mobile;
    String email;
    String blood;
    int weight;
    boolean willing;
    String comments;
}
