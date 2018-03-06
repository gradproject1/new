package com.example.user1.urnextapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.widget.TextView;
import android.widget.Toast;

import java.lang.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;



public class Pprofile extends Fragment {
    private TextView name ,arrival,AppointmentTime,DocName;
    private   FirebaseDatabase database = FirebaseDatabase.getInstance();
    private   DatabaseReference Patient = database.getReference("Patient");
    private   DatabaseReference external = database.getReference("ExternalDB");
    private DatabaseReference waiting = database.getReference("waiting time and queue number");
  private  FirebaseAuth  firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
   private String id=user.getUid();
    private String PhoneNumber  ;


    //Constructor default
    public Pprofile(){};


    @Override
    public View onCreateView(  @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws NullPointerException {
        View PageTree = inflater.inflate(R.layout.fragment_pprofile, container, false);
        name =(TextView) PageTree.findViewById(R.id.patientName);
        arrival =(TextView) PageTree.findViewById(R.id.arrivelTime);
        DocName =(TextView) PageTree.findViewById(R.id.doctorName);
        AppointmentTime =(TextView) PageTree.findViewById(R.id.appTime);


        // Read from the database

        Patient.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              name.setText(dataSnapshot.child("Name").getValue().toString());
              arrival.setText(dataSnapshot.child("arrival").getValue().toString());
              PhoneNumber=dataSnapshot.child("Phone").getValue().toString();}

            @Override
            public void onCancelled(DatabaseError error) {}
        });

        String clinic = "Dental clinic";

        external.child("Appointment").child(clinic).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

          if (dataSnapshot.hasChild(PhoneNumber) && dataSnapshot.child(PhoneNumber).hasChild(name.getText().toString()))
          {   DocName.setText(dataSnapshot.child(PhoneNumber).child(name.getText().toString()).child("Doctor Name").getValue().toString());
              AppointmentTime.setText(dataSnapshot.child(PhoneNumber).child(name.getText().toString()).child("appTime").getValue().toString());
              waiting.child(DocName.getText().toString()).child(AppointmentTime.getText().toString()).setValue(id);

          }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });




        Button logout = (Button) PageTree.findViewById(R.id.logout);
        Button cancel = (Button) PageTree.findViewById(R.id.cancel);
// actions for buttons
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() != null)
                    firebaseAuth.signOut();
                Toast.makeText(getActivity(), "successfully Log out , come back again! " , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), WelcomePage.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //remove patient from queue
                waiting.child(DocName.getText().toString()).child(id).removeValue();
                Toast.makeText(getActivity(), "successfully canceling the appointment! " , Toast.LENGTH_LONG).show();
            }
        });
        return PageTree;
    }
}
