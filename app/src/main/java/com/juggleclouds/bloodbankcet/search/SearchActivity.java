package com.juggleclouds.bloodbankcet.search;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.juggleclouds.bloodbankcet.Global;
import com.juggleclouds.bloodbankcet.R;
import com.juggleclouds.bloodbankcet.classes.User;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lvSearchResult;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String station = getIntent().getStringExtra("station");
        String bloodGroup = getIntent().getStringExtra("blood");
        new SearchTask(station, bloodGroup).execute();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvSearchResult = (ListView) findViewById(R.id.lvSearch);
        lvSearchResult.setOnItemClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.REQUEST_CODE_SAVE_USER && resultCode == Global.CODE_SAVE_USER) {
            int userIndex = data.getIntExtra("userIndex", 0);
            Log.i("got id", userIndex + "");
            SearchListAdapter adapter = (SearchListAdapter) lvSearchResult.getAdapter();
            adapter.updateItem(userIndex);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        User user = userList.get(i);
        Log.i("Selected", user.toString());
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("user_id", user.getId());
        intent.putExtra("user_index", i);
        startActivityForResult(intent, Global.REQUEST_CODE_SAVE_USER);
    }

    class SearchTask extends AsyncTask<Void, Void, List<User>> {

        String station, bloodGroup;

        SearchTask(String s, String b) {
            station = s;
            bloodGroup = b;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> users = null;
            Select<User> selector = Select.from(User.class);
            if (station.equals(""))
                selector = selector
                        .where(Condition.prop("blood").eq(bloodGroup));
            else
                selector = selector
                        .where(Condition.prop("blood").eq(bloodGroup), Condition.prop("station").eq(station));

            users = selector
                    .orderBy("department,year,name")
                    .list();
            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            SearchListAdapter searchListAdapter = new SearchListAdapter(SearchActivity.this, users);
            userList = users;
            lvSearchResult.setAdapter(searchListAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
