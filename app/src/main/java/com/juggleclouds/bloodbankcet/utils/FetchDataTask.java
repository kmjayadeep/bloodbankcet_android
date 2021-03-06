package com.juggleclouds.bloodbankcet.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juggleclouds.bloodbankcet.Global;
import com.juggleclouds.bloodbankcet.classes.User;

import java.util.Date;

/**
 * Created by jayadeep on 10/22/16.
 */
public class FetchDataTask {

    ProgressDialog dialog;
    Context context;

    public FetchDataTask(Context context) {
        dialog = new ProgressDialog(context);
        this.context = context;
        dialog.setIndeterminate(true);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Loading data from server");
        dialog.setCancelable(false);
    }

    public void execute() {
        dialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                saveData(dataSnapshot);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(context, "Unable to fetch data from server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData(DataSnapshot dataSnapshot) {
        new SaveDataTask(dataSnapshot).execute();
    }

    class SaveDataTask extends AsyncTask<Void, Void, Void> {

        DataSnapshot dataSnapshot;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        public SaveDataTask(DataSnapshot snapshot) {
            dataSnapshot = snapshot;
            dialog = new ProgressDialog(context);
            dialog.setTitle("Please Wait");
            dialog.setMessage("Saving Data");
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            User.deleteAll(User.class);
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                User user = dataSnapshot1.getValue(User.class);
                if (user.id > 0)
                    user.setId(user.id);
                Log.i("got", user.name + " " + user.getId());
                user.save();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            SharedPreferences sharedPreferences = context.getSharedPreferences(Global.sharedPreferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("lastDownSync", new Date().getTime());
            editor.commit();
            Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show();
            if (context instanceof OnTaskFinishedListener)
                ((OnTaskFinishedListener) context).onDownSyncFinished();
        }
    }

    public interface OnTaskFinishedListener {
        void onDownSyncFinished();
    }

}


