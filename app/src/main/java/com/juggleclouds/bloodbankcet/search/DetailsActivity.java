package com.juggleclouds.bloodbankcet.search;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.juggleclouds.bloodbankcet.R;
import com.juggleclouds.bloodbankcet.classes.User;

public class DetailsActivity extends AppCompatActivity {

    User user;
    TextView tvName, tvBloodGroup, tvAddress, tvStation, tvDept, tvMobile, tvEmail, tvWeight, tvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = (User) getIntent().getSerializableExtra("user");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        tvName = (TextView) findViewById(R.id.name);
        tvStation = (TextView) findViewById(R.id.station);
        tvAddress = (TextView) findViewById(R.id.address);
        tvBloodGroup = (TextView) findViewById(R.id.bloodgroup);
        tvDept = (TextView) findViewById(R.id.department_year);
        tvEmail = (TextView) findViewById(R.id.email);
        tvMobile = (TextView) findViewById(R.id.mobile);
        tvWeight = (TextView) findViewById(R.id.weight);
        tvComments = (TextView) findViewById(R.id.comments);
        tvName.setText(user.name);
        tvStation.setText(user.station);
        tvAddress.setText(user.address);
        tvBloodGroup.setText(user.blood);
        if (user.year != 0)
            tvDept.setText(user.department + " " + user.year);
        else
            tvDept.setVisibility(View.GONE);
        tvMobile.setText("+91 " + user.mobile);
        tvEmail.setText(user.email);
        tvWeight.setText(user.weight + " Kg");
        tvComments.setText(user.comments);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
