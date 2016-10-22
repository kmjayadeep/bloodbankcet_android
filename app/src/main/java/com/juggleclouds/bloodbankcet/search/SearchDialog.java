package com.juggleclouds.bloodbankcet.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.juggleclouds.bloodbankcet.R;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by jayadeep on 10/22/16.
 */
public class SearchDialog extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search,null,false);
        MaterialSpinner spinner = (MaterialSpinner) view.findViewById(R.id.bloodgroup);
        String[] bloodGroups = new String[]{"A+","A-","B+","B-","AB+","AB-","O+","O-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,bloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        getDialog().setTitle("Search");
        return  view;
    }
}
