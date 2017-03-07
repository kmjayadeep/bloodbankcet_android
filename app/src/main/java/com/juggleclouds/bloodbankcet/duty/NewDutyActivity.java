package com.juggleclouds.bloodbankcet.duty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juggleclouds.bloodbankcet.R;

public class NewDutyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_duty);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
