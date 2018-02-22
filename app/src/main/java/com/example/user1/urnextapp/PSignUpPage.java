package com.example.user1.urnextapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.*;
import model.Patient;


public class PSignUpPage extends AppCompatActivity {

  EditText name,phone,email,pass,DOB;
  RadioGroup genderGroup;
  RadioButton gender;
  Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psign_up_page);

        name = (EditText)findViewById(R.id.name);
        phone = (EditText)findViewById(R.id.phone);
        email = (EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        DOB=(EditText)findViewById(R.id.DOB);
        genderGroup = (RadioGroup) findViewById(R.id.gender);
        btnSignUp = (Button) findViewById(R.id.next);

        //firebase

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference Patient_table = database.getReference("Patient");


        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
 public void onClick (View view)
            {
                final ProgressDialog mDialog = new ProgressDialog(PSignUpPage.this);
                mDialog.setMessage("Please waiting..");
                mDialog.show();

                Patient_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if already the email is exist
                        if (dataSnapshot.child(email.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(PSignUpPage.this,"This Email already register",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            mDialog.dismiss();
                            model.Patient patient = new Patient(name.getText().toString(),pass.getText().toString(), gender.getText().toString(),DOB.getText().toString());
                            Patient_table.child(email.getText().toString()).setValue(patient);
                            Toast.makeText(PSignUpPage.this,"Sign up successfully",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view)
            {
                Intent signup = new Intent(PSignUpPage.this, PSignUpPage2.class);
                startActivity(signup);
            }

        });

    }

    public void gender(View v)
    {
        int rbid = genderGroup.getCheckedRadioButtonId();
        gender = (RadioButton) findViewById(rbid);
    }

}
