package com.juggleclouds.bloodbankcet.account;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.juggleclouds.bloodbankcet.R;
import com.juggleclouds.bloodbankcet.classes.User;

import fr.ganfra.materialspinner.MaterialSpinner;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout tilName, tilEmail, tilMobile, tilStation, tilAddress, tilYear, tilComments, tilWeight;
    MaterialSpinner sBlood, sDepartment;
    Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
    }

    private void initialize() {
        tilName = (TextInputLayout) findViewById(R.id.etName);
        tilEmail = (TextInputLayout) findViewById(R.id.etEmail);
        tilMobile = (TextInputLayout) findViewById(R.id.etMobile);
        sDepartment = (MaterialSpinner) findViewById(R.id.department);
        sBlood = (MaterialSpinner) findViewById(R.id.bloodgroup);
        tilAddress = (TextInputLayout) findViewById(R.id.etAddress);
        tilStation = (TextInputLayout) findViewById(R.id.etStation);
        tilWeight = (TextInputLayout) findViewById(R.id.etWeight);
        tilYear = (TextInputLayout) findViewById(R.id.etYear);
        tilComments = (TextInputLayout) findViewById(R.id.etComments);
        bRegister = (Button) findViewById(R.id.register);
        bRegister.setOnClickListener(new ButtonListener());
        String[] bloodGroups = new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        ArrayAdapter<String> bAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroups);
        bAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBlood.setAdapter(bAdapter);
        String[] departments = new String[]{"AE", "AR", "C-1", "C-2", "CS", "E-1", "E-2", "EC", "M Tech", "M-1", "M-2", "MCA", "N"};
        ArrayAdapter<String> dAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departments);
        dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDepartment.setAdapter(dAdapter);
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            tilName.setError("");
            tilMobile.setError("");
            tilEmail.setError("");
            tilMobile.setError("");
            tilAddress.setError("");
            tilStation.setError("");
            tilWeight.setError("");
            tilYear.setError("");
            tilComments.setError("");
            sBlood.setError(null);
            sDepartment.setError(null);
            if (view.getId() == R.id.register) {
                User user = new User();
                user.name = tilName.getEditText().getText().toString();
                if (user.name.equals("")) {
                    tilName.setError("Enter name");
                    return;
                }
                user.email = tilEmail.getEditText().getText().toString();
                if (tilMobile.getEditText().getText().toString().equals("")) {
                    tilMobile.setError("Enter Mobile no");
                    return;
                }
                user.mobile = Long.parseLong(tilMobile.getEditText().getText().toString());
                user.department = sDepartment.getSelectedItem().toString();
                if (user.department.equals("Department"))
                    user.department = "";
                user.blood = sBlood.getSelectedItem().toString();
                if (user.blood.equals("Blood Group")) {
                    sBlood.setError("Enter blood group");
                    return;
                }
                user.address = tilAddress.getEditText().getText().toString();
                if (user.address.equals("")) {
                    tilAddress.setError("Enter Address");
                    return;
                }
                user.station = tilStation.getEditText().getText().toString();
                if (user.station.equals("")) {
                    tilStation.setError("Enter Station");
                    return;
                }
                user.comments = tilComments.getEditText().getText().toString();
                if (tilWeight.getEditText().getText().toString().equals("")) {
                    tilWeight.setError("Enter weight");
                    return;
                }
                user.weight = Integer.parseInt(tilWeight.getEditText().getText().toString());
                if (tilYear.getEditText().getText().toString().equals("")) {
                    user.year = 0;
                } else
                    user.year = Integer.parseInt(tilYear.getEditText().getText().toString());
                user.willing = true;
                Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                Log.i("user", new Gson().toJson(user));
            }
        }

    }
}
