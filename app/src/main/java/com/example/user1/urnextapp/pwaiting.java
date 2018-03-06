package com.example.user1.urnextapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;
import  java.lang.*;


import java.text.SimpleDateFormat;
import java.util.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.datatype.Duration;

import model.*;
import model.Patient;

public class pwaiting extends Fragment {

    private TextView queueNumber ,estimatedTime ,DocName,name;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference Patient = database.getReference("Patient");
    private DatabaseReference waiting = database.getReference("waiting time and queue number");
    private DatabaseReference external = database.getReference("ExternalDB");
    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();

   private String pattern = "HH:mm";
   private SimpleDateFormat sdf = new SimpleDateFormat(pattern);
String PhoneNumber;
 String id=user.getUid();

    //Constructor default
    public pwaiting(){

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View PageOne = inflater.inflate(R.layout.fragment_pwaiting, container, false);
         final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        final String today1 =today.toString();
        estimatedTime= (TextView) PageOne.findViewById(R.id.estimatedTime);
        queueNumber= (TextView) PageOne.findViewById(R.id.queueNumber);
        DocName =(TextView) PageOne.findViewById(R.id.doctorName);
        name =(TextView) PageOne.findViewById(R.id.patientName);


            waiting.child("Ahmed salah").orderByValue().startAt(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String count=dataSnapshot.getChildrenCount()+"";
               queueNumber.setText(count);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message

                    // ...
                }
            });

        return PageOne;
    }
}
