package com.example.user1.urnextapp;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.EditText;
import android.widget.Toast;
import 	android.app.ProgressDialog;
import java.lang.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PSignUpPage extends AppCompatActivity {

    //defining view objects
    private EditText name,phone;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private EditText inputEmail;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private   FirebaseDatabase database = FirebaseDatabase.getInstance();
    private   DatabaseReference Patient = database.getReference("Patient");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psign_up_page);

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.pass);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);


        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = editTextEmail.getText().toString();
                final String password = editTextPassword.getText().toString();
                final String name1 = name.getText().toString();
                final String phone1 = phone.getText().toString();


                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (email.isEmpty()) {
                    editTextEmail.setError("Please enter an email!");
                }
                else if(!email.matches(emailPattern)){
                    editTextEmail.setError("Invalid email address");
                }


               else if (password.isEmpty()) {
                    editTextPassword.setError("Please enter a Password!");
                }

                else    if (password.length() < 6) {

                    editTextPassword.setError("Password too short, enter minimum 6 characters!");
                }
                else   if (name1.isEmpty()) {
                    name.setError("Please enter name!");

                }
                else  if (phone1.isEmpty()) {
                    phone.setError("Please enter a phone number!");
                }

                else {   auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(PSignUpPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (!task.isSuccessful()) {
                                    Toast.makeText(PSignUpPage.this, "This email ia already exist." ,
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    FirebaseUser user = auth.getCurrentUser();
                                    assert user != null;

                                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    Patient.child(uid).child("Name").setValue(name1);
                                    Patient.child(uid).child("Phone").setValue(phone1);
                                    Patient.child(uid).child("sport").setValue("0");
                                    Patient.child(uid).child("health").setValue("0");
                                    Patient.child(uid).child("fashion").setValue("0");
                                    Patient.child(uid).child("diy").setValue("0");
                                    Patient.child(uid).child("history").setValue("0");
                                    Patient.child(uid).child("technology").setValue("0");
                                    Patient.child(uid).child("travil").setValue("0");
                                    Patient.child(uid).child("book").setValue("0");
                                    Patient.child(uid).child("email").setValue(email);
                                    Patient.child(uid).child("Password").setValue(password);
                                    startActivity(new Intent(PSignUpPage.this, entertainmentSignUp.class));
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseNetworkException) {
                    Toast.makeText(PSignUpPage.this, "Please check your connection", Toast.LENGTH_LONG).show();
                }}
            });}
            }
        });
 }
}

