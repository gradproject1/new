package com.example.user1.urnextapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.*;


public class patientList extends Fragment {
    //Constructor default
    public patientList(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View PageOne = inflater.inflate(R.layout.fragment_patient_list, container, false);


        FirebaseDatabase database;

        ListView listViewPatient;

        //our database reference object
        DatabaseReference databasePatient;

        //a list to store all the patient from firebase database
        List<com.example.user1.urnextapp.Patient> patientList;
        database = FirebaseDatabase.getInstance();
        //getting the reference of patient node
        databasePatient = database.getReference("Patient");
        //getting views
        listViewPatient = (ListView) getActivity().findViewById(R.id.listViewPatient);

        //list to store artists
        patientList = new ArrayList<>();




        return PageOne;
    }
    }

