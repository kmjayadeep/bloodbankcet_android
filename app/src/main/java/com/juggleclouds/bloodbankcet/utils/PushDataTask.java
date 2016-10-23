package com.juggleclouds.bloodbankcet.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juggleclouds.bloodbankcet.Global;
import com.juggleclouds.bloodbankcet.classes.User;

import java.util.Date;
import java.util.List;

/**
 * Created by jayadeep on 10/23/16.
 */
public class PushDataTask {
    Context context;

    public PushDataTask(Context context) {
        this.context = context;
    }

    public void execute() {
        new UploadDataTask().execute();

    }


    class UploadDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        public UploadDataTask() {
            dialog = new ProgressDialog(context);
            dialog.setTitle("Please Wait");
            dialog.setMessage("Uploading Data");
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            List<User> userList = User.listAll(User.class);
            databaseReference.child("users").setValue(userList).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(context, "Unable to upload data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Global.sharedPreferences, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("lastUpSync", new Date().getTime());
                    editor.commit();
                    dialog.dismiss();
                    Toast.makeText(context, "Data uploaded", Toast.LENGTH_SHORT).show();
                    if (context instanceof OnTaskFinishedListener)
                        ((OnTaskFinishedListener) context).onUpSyncFinished();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Unable to upload data", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void a) {

        }
    }

    public interface OnTaskFinishedListener {
        void onUpSyncFinished();
    }

}
