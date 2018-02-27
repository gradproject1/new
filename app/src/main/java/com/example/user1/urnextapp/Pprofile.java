package com.example.user1.urnextapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import 	android.app.ProgressDialog;
import java.lang.*;
import 	android.text.format.Time;
import java.util.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;



public class Pprofile extends Fragment {
   private Button logout;
   private Button cancel;
   private TextView name ,arrival;
    private   FirebaseDatabase database = FirebaseDatabase.getInstance();
    private   DatabaseReference Patient = database.getReference("Patient");
  private  FirebaseAuth  firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();

    //Constructor default
    public Pprofile(){};

    @Override
    public View onCreateView(  @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws NullPointerException {
        View PageTree = inflater.inflate(R.layout.fragment_pprofile, container, false);
       logout=(Button) PageTree.findViewById(R.id.logout);
        cancel=(Button) PageTree.findViewById(R.id.cancel);
        name =(TextView) PageTree.findViewById(R.id.patientName);
        arrival =(TextView) PageTree.findViewById(R.id.arrivelTime);
       String id=user.getUid();
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
       arrival.setText(today.format("%k:%M:%S"));
        // Read from the database
        Patient.child("Name").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);
                name.setText(value);
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               name.setText("error to read value");
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() != null)
                    firebaseAuth.signOut();
                Toast.makeText(getActivity(), "successfully Log out , come back again! " , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WelcomePage.class);
                startActivity(intent);
            }
        });
        return PageTree;
    }
}
