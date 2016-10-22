package com.juggleclouds.bloodbankcet.classes;

import com.orm.SugarRecord;

/**
 * Created by jayadeep on 10/22/16.
 */
public class User extends SugarRecord{
    String name;
    int year;
    String department;
    String address;
    String station;
    String mobile;
    String email;
    String blood;
    int weight;
    boolean willing;
    String comments;
}
