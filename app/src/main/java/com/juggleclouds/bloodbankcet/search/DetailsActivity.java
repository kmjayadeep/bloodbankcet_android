package com.juggleclouds.bloodbankcet.search;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.juggleclouds.bloodbankcet.R;
import com.juggleclouds.bloodbankcet.classes.User;

import java.util.Date;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    User user;
    TextView tvName, tvBloodGroup, tvAddress, tvStation, tvDept, tvMobile, tvEmail, tvWeight, tvComments, tvDonated;
    TextInputLayout tilComments, tilDonated;
    Button bSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = (User) getIntent().getSerializableExtra("user");
        user.setId(getIntent().getLongExtra("user_id", 0));
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
        tvDonated = (TextView) findViewById(R.id.donated);
        tilDonated = (TextInputLayout) findViewById(R.id.input_donated);
        tilComments = (TextInputLayout) findViewById(R.id.input_comments);
        bSave = (Button) findViewById(R.id.save);
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
        if (user.donated > 0) {
            String date = new Date(user.donated).toString();
            tvDonated.setText(date);
        } else
            tvDonated.setText("Never");
        fab.setOnClickListener(this);
        bSave.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save) {
            String comments = "";
            if (tilComments.getEditText() != null)
                comments = tilComments.getEditText().getText().toString();
            Log.i("comments", comments);
            user.comments = comments;
//            user.donated = put date here
            user.save();
            tvComments.setText(user.comments);
            tilComments.setVisibility(View.GONE);
            tilDonated.setVisibility(View.GONE);
            bSave.setVisibility(View.GONE);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.fab) {
            if (tilComments.getVisibility() == View.GONE) {
                tilComments.setVisibility(View.VISIBLE);
                tilDonated.setVisibility(View.VISIBLE);
                bSave.setVisibility(View.VISIBLE);
                tilComments.getEditText().setText(user.comments);
            } else {
                tilComments.setVisibility(View.GONE);
                tilDonated.setVisibility(View.GONE);
                bSave.setVisibility(View.GONE);
                tvComments.setText(user.comments);
                if (user.donated > 0) {
                    String date = new Date(user.donated).toString();
                    tvDonated.setText(date);
                } else
                    tvDonated.setText("Never");
            }
        }
    }
}
