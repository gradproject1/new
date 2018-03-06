package com.example.user1.urnextapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user1.urnextapp.Patient;
import com.example.user1.urnextapp.R;

import java.util.List;

/**
 * Created by DELL on 26/02/18.


public class pl extends ArrayAdapter<Patient> {


    private Activity context;
    List<Patient> patientList;
    private int resource;

    //R.layout.list_layout
    public pl(Activity context, List<Patient> patientList){
        super(context,R.layout.list_layout ,patientList);
        this.resource=resource;
        this.context=context;
        this.patientList=patientList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = View.inflate(context,R.layout.list_layout,null);

        TextView paName = (TextView) listViewItem.findViewById(R.id.paName);

        Patient patient = patientList.get(position);

       /* firebase.auth().onAuthStateChanged(function(user) {
            if (user) {
                // User is signed in.
                textViewName.setText(patient.getName());
            } else {
                // No user is signed in.
            }
        });*/
       /* return listViewItem;
    }


}*/
