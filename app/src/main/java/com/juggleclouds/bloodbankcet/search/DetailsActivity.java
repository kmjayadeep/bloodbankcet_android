package com.juggleclouds.bloodbankcet.search;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juggleclouds.bloodbankcet.Global;
import com.juggleclouds.bloodbankcet.R;
import com.juggleclouds.bloodbankcet.classes.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    User user;
    TextView tvName, tvBloodGroup, tvAddress, tvStation, tvDept, tvMobile, tvEmail, tvWeight, tvComments, tvDonated;
    TextInputLayout tilComments, tilDonated;
    Button bSave;
    Calendar calDonated;
    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
    LinearLayout editLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        user = (User) getIntent().getSerializableExtra("user");
//        user.setId(getIntent().getLongExtra("user_id", 0));
        long userId = getIntent().getLongExtra("user_id", 0);
        user = User.findById(User.class, userId);
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
            tvDonated.setText(format.format(new Date(user.donated)));
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -3);
            if (user.donated > cal.getTimeInMillis())
                tvDonated.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else
            tvDonated.setText("Never");
        fab.setOnClickListener(this);
        bSave.setOnClickListener(this);
        tilDonated.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) return;
                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        DetailsActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setMaxDate(Calendar.getInstance());
                dpd.show(getFragmentManager(), "datepicker");
            }
        });
        tilDonated.getEditText().setKeyListener(null);
        editLayout = (LinearLayout) findViewById(R.id.llEdit);
        editLayout.setVisibility(View.GONE);
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
            if (calDonated != null)
                user.donated = calDonated.getTimeInMillis();
            Log.i("donated", user.donated + "");
            user.save();
            tvComments.setText(user.comments);
            if (user.donated > 0) {
                tvDonated.setText(format.format(new Date(user.donated)));
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, -3);
                if (user.donated > cal.getTimeInMillis())
                    tvDonated.setTextColor(getResources().getColor(R.color.colorPrimary));
                else
                    tvDonated.setTextColor(getResources().getColor(R.color.colorAccent));
            } else
                tvDonated.setText("Never");
            tilComments.getEditText().requestFocus();
            editLayout.setVisibility(View.GONE);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            Intent result = new Intent();
            result.putExtra("userId", user.getId());
            setResult(Global.CODE_SAVE_USER, result);
        } else if (view.getId() == R.id.fab) {
            if (editLayout.getVisibility() == View.GONE) {
                editLayout.setVisibility(View.VISIBLE);
                tilComments.getEditText().setText(user.comments);
                if (user.donated > 0)
                    tilDonated.getEditText().setText(format.format(new Date(user.donated)));
                calDonated = null;
            } else {
                editLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.i("date", dayOfMonth + "");
        calDonated = Calendar.getInstance();
        calDonated.set(Calendar.YEAR, year);
        calDonated.set(Calendar.MONTH, monthOfYear);
        calDonated.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tilDonated.getEditText().setText(format.format(calDonated.getTime()));
        tilComments.getEditText().requestFocus();
    }
}
