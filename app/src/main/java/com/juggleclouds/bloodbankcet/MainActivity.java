package com.juggleclouds.bloodbankcet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.juggleclouds.bloodbankcet.account.RegisterActivity;
import com.juggleclouds.bloodbankcet.classes.User;
import com.juggleclouds.bloodbankcet.search.SearchActivity;
import com.juggleclouds.bloodbankcet.search.SearchDialog;
import com.juggleclouds.bloodbankcet.utils.FetchDataTask;
import com.juggleclouds.bloodbankcet.utils.PushDataTask;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchDialog.ActionListener, FetchDataTask.OnTaskFinishedListener, PushDataTask.OnTaskFinishedListener {

    TextView tvCount, tvUpSync, tvDownSync;
    Button bUpSync, bDownSync, bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        tvCount = (TextView) findViewById(R.id.count);
        tvDownSync = (TextView) findViewById(R.id.last_downsync);
        tvUpSync = (TextView) findViewById(R.id.last_upsync);
        bUpSync = (Button) findViewById(R.id.upsync);
        bDownSync = (Button) findViewById(R.id.downsync);
        bRegister = (Button) findViewById(R.id.newuser);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences(Global.sharedPreferences, MODE_PRIVATE);

        if (sharedPreferences.getLong("lastDownSync", 0) == 0) {
            new FetchDataTask(this).execute();
        } else
            updateValues();

        fab.setOnClickListener(new FabListener());
        ButtonListener buttonListener = new ButtonListener();
        bRegister.setOnClickListener(buttonListener);
        bUpSync.setOnClickListener(buttonListener);
        bDownSync.setOnClickListener(buttonListener);
    }

    void updateValues() {
        long count = User.count(User.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Global.sharedPreferences, MODE_PRIVATE);
        tvCount.setText("Total Registered Users : " + count);
        long lastDownSync = sharedPreferences.getLong("lastDownSync", 0);
        long lastUpSync = sharedPreferences.getLong("lastUpSync", 0);

        if (lastDownSync == 0)
            tvDownSync.setText("Last Downsync : Never");
        else
            tvDownSync.setText("Last Downsync : " + DateFormat.format("yyyy-MM-dd hh:mm", new Date(lastDownSync)));
        if (lastUpSync == 0)
            tvUpSync.setText("Last Upsync : Never");
        else
            tvUpSync.setText("Last Upsync: " + DateFormat.format("yyyy-MM-dd hh:mm", new Date(lastUpSync)));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearch(String station, String bloodGroup) {
        Log.i("searching", station + " " + bloodGroup);
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("station", station);
        intent.putExtra("blood", bloodGroup);
        startActivity(intent);
    }

    @Override
    public void onDownSyncFinished() {
        updateValues();
    }

    @Override
    public void onUpSyncFinished() {
        updateValues();
    }

    private class FabListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            SearchDialog dialog = new SearchDialog();
            dialog.show(getSupportFragmentManager(), "search dialog");
        }
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.newuser) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            } else if (view.getId() == R.id.upsync) {
                new PushDataTask(MainActivity.this).execute();
            } else if (view.getId() == R.id.downsync) {
                User.deleteAll(User.class);
                new FetchDataTask(MainActivity.this).execute();
            }
        }
    }
}
