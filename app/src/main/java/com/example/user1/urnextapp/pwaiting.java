package com.example.user1.urnextapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import  java.lang.*;


import java.text.DateFormat;
import java.text.ParseException;
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
    private TextView queueNumber;
    private TextView estimate;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference Patient = database.getReference("Patient");
    DatabaseReference external = database.getReference("ExternalDB");
    DatabaseReference waiting = database.getReference("waiting time and queue number");
    String id=" ";
    //Constructor default
    public pwaiting(){

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View PageOne = inflater.inflate(R.layout.fragment_pwaiting, container, false);
         queueNumber = (TextView) PageOne.findViewById(R.id.queueNumber);
         estimate = (TextView) PageOne.findViewById(R.id.estimatedTime);
        if(user != null)
        {
            id=user.getUid();

        }

        Patient.child(id).addValueEventListener(new ValueEventListener(){
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  pname= dataSnapshot.child("Name").getValue(String.class);
                String  pphone= dataSnapshot.child("Phone").getValue(String.class);

                external.child("Appointment").child("Dental clinic").child(pphone).child(pname).addValueEventListener(new ValueEventListener(){
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String  dname= dataSnapshot.child("Doctor Name").getValue(String.class);
                        String papp= dataSnapshot.child("appTime").getValue(String.class);
                       waiting.child(dname).child(papp).setValue(id);

                        waiting.child(dname).orderByKey().endAt(papp).addValueEventListener(new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String count = "" + dataSnapshot.getChildrenCount();
                                queueNumber.setText(count);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });

                        //caculate admission time
                        //calculate esttimated time

                        final Time today = new Time(Time.getCurrentTimezone());
                        today.setToNow();
                       // int appHours = Integer.parseInt(papp.substring(0,2));
                        //int appMin=Integer.parseInt(papp.substring(3));
                        String tod=today.format("%k:%M");
                     //   int nowHours=Integer.parseInt(tod.substring(0,2));
                       // int nowMin = Integer.parseInt(tod.substring(3));

                      //  int subHours = nowHours - appHours %60;
                      //  int subMin = nowMin - appMin % 60;

                       // String totalTime =subHours +":"+subMin;

                       DateFormat df = new java.text.SimpleDateFormat("hh:mm");
                        Date date1 = null;
                        try {
                            date1 = df.parse(papp);
                            Date date2 = df.parse(tod);
                            long diff = date1.getTime() - date2.getTime();

                            long timeInSeconds = diff / 1000;
                            long hours, minutes,second;
                            hours = timeInSeconds / 3600;
                            timeInSeconds = timeInSeconds - (hours * 3600);
                            minutes = timeInSeconds / 60;
                            timeInSeconds = timeInSeconds - (minutes * 60);

                            reverseTimer((int)diff);

                          //  estimate.setText(t);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });





        return PageOne;
    }
    public void reverseTimer(int Seconds) {

        new CountDownTimer(Seconds, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);
            String t=           String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds);
                estimate.setText(t);
            }

            public void onFinish() {
                estimate.setText("Your turn!");
            }
        }.start();
    }
}
