package com.juggleclouds.bloodbankcet.duty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.juggleclouds.bloodbankcet.Global;
import com.juggleclouds.bloodbankcet.R;
import com.juggleclouds.bloodbankcet.classes.Duty;
import com.juggleclouds.bloodbankcet.search.SearchListAdapter;

public class DutyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(DutyActivity.this, NewDutyActivity.class), Global.REQUEST_CODE_ADD_DUTY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.REQUEST_CODE_ADD_DUTY && resultCode == Global.ADD_DUTY) {
            long dutyId = data.getLongExtra("dutyId", 0);
            Log.i("got id", dutyId + "");
        }
    }
}
