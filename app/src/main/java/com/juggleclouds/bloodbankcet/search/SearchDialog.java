package com.juggleclouds.bloodbankcet.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.juggleclouds.bloodbankcet.R;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by jayadeep on 10/22/16.
 */
public class SearchDialog extends DialogFragment implements View.OnClickListener {

    MaterialSpinner sBlood;
    TextInputLayout tilStation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search, null, false);
        sBlood = (MaterialSpinner) view.findViewById(R.id.bloodgroup);
        String[] bloodGroups = new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBlood.setAdapter(adapter);
        getDialog().setTitle("Search");
        Button bSearch = (Button) view.findViewById(R.id.search);
        bSearch.setOnClickListener(this);
        tilStation = (TextInputLayout) view.findViewById(R.id.station);
        return view;
    }

    @Override
    public void onClick(View view) {
        String station = tilStation.getEditText().getText().toString();
        String bloodGroup = sBlood.getSelectedItem().toString();
        if (bloodGroup.equals("Blood Group")) {
            sBlood.setError("Enter blood group");
            return;
        }
        dismiss();
        if (getContext() instanceof ActionListener)
            ((ActionListener) getContext()).onSearch(station, bloodGroup);
    }

    public interface ActionListener {
        void onSearch(String station, String bloodGroup);
    }
}
