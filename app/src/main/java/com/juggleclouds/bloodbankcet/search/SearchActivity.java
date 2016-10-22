package com.juggleclouds.bloodbankcet.search;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.juggleclouds.bloodbankcet.R;
import com.juggleclouds.bloodbankcet.classes.User;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String station = getIntent().getStringExtra("station");
        String bloodGroup = getIntent().getStringExtra("blood");
        new SearchTask(station,bloodGroup).execute();
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    class SearchTask extends AsyncTask<Void,Void,List<User>>{

        String station,bloodGroup;

        SearchTask(String s,String b){
            station = s;
            bloodGroup = b;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> users = null;
            if(station.equals(""))
                users = User.find(User.class,"blood = ?",bloodGroup);
            else
                users = User.find(User.class,"blood = ? and station= ?",bloodGroup,station);
            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            Log.i("search",users.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
