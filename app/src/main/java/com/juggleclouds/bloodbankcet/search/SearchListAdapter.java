package com.juggleclouds.bloodbankcet.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.juggleclouds.bloodbankcet.R;
import com.juggleclouds.bloodbankcet.classes.User;

import java.util.List;

/**
 * Created by jayadeep on 10/22/16.
 */
public class SearchListAdapter extends BaseAdapter {

    List<User> userList;
    Context context;

    SearchListAdapter(Context con, List<User> users) {
        context = con;
        userList = users;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.item_search, null);
        }
        User user = getItem(i);
        TextView tvName = (TextView) view.findViewById(R.id.name);
        TextView tvComments = (TextView) view.findViewById(R.id.comments);
        TextView tvDepartment = (TextView) view.findViewById(R.id.department);
        tvName.setText(user.name);
        tvComments.setText(user.comments);
        if (user.year != 0)
            tvDepartment.setText(user.department + " " + user.year);
        else
            tvDepartment.setText("");
        return view;
    }
}
