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

    private TextView name,arrival,docName,appTime;
    private Button logout,cancel;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference Patient = database.getReference("Patient");
    DatabaseReference external = database.getReference("ExternalDB");
     String id=" ";
    //Constructor default
    public Pprofile(){};


    @Override
    public View onCreateView(  @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws NullPointerException {
        View Page1 = inflater.inflate(R.layout.fragment_pprofile, container, false);

        name=    (TextView) Page1.findViewById(R.id.patientName);
        arrival = (TextView) Page1.findViewById(R.id.arrivelTime);
        docName =(TextView) Page1.findViewById(R.id.doctorName);
        appTime =(TextView) Page1.findViewById(R.id.appTime);
        logout = (Button) Page1.findViewById(R.id.logout);
        cancel =(Button) Page1.findViewById(R.id.cancel);


        if(user != null)
        {
            id=user.getUid();

        }


        Patient.child(id).addValueEventListener(new ValueEventListener(){
            public void onDataChange(DataSnapshot dataSnapshot) {
              String  pname= dataSnapshot.child("Name").getValue(String.class);
              String  pphone= dataSnapshot.child("Phone").getValue(String.class);
              name.setText(pname);


                external.child("Appointment").child("Dental clinic").child(pphone).child(pname).addValueEventListener(new ValueEventListener(){
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      String  dname= dataSnapshot.child("Doctor Name").getValue(String.class);
                      String papp= dataSnapshot.child("appTime").getValue(String.class);
                        docName.setText(dname);
                        appTime.setText(papp);
}
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

             }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        Patient.child(id).child("arrival").addValueEventListener(new ValueEventListener(){
            public void onDataChange(DataSnapshot dataSnapshot) {
               String parraival= dataSnapshot.getValue(String.class);
               arrival.setText(parraival);
      }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firebaseAuth != null)
                {
                    firebaseAuth.signOut();
                    Toast.makeText(getContext(),"You successfully logged out!", Toast.LENGTH_SHORT).show();
                    Intent h= new Intent(getContext(), WelcomePage.class);
                    startActivity(h); }

            }
        });

        return Page1;
    }
}
