package com.example.user1.urnextapp;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.EditText;
import android.widget.Toast;
import 	android.app.ProgressDialog;
import java.lang.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PSignUpPage extends AppCompatActivity {

    //defining view objects
    private EditText name,phone,DOB;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private   FirebaseDatabase database = FirebaseDatabase.getInstance();
    private   DatabaseReference Patient = database.getReference("Patient");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psign_up_page);

        auth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.pass);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        DOB=(EditText) findViewById(R.id.DOB);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);


        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = editTextEmail.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();
                final String name1 = name.getText().toString().trim();
                final String phone1 = phone.getText().toString().trim();
                final String DOB1 = DOB.getText().toString().trim();

                if (email.isEmpty() && !email.contains("@")) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (DOB1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Date of birth!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (DOB1.charAt(2)!='/' && DOB1.charAt(5)!='/' &&DOB1.length()==10 && DOB1.charAt(0)>=0 &&DOB1.charAt(0)>10 &&
                        DOB1.charAt(1)>=0 &&DOB1.charAt(1)>10 && DOB1.charAt(3)>=0 &&DOB1.charAt(3)>10&& DOB1.charAt(4)>=0 &&DOB1.charAt(4)>10&&
                DOB1.charAt(6)>=0 &&DOB1.charAt(6)>10&&DOB1.charAt(7)>=0 &&DOB1.charAt(7)>10&&DOB1.charAt(8)>=0 &&DOB1.charAt(8)>10&&DOB1.charAt(9)>=0 &&DOB1.charAt(9)>10
                        )
              {
                    Toast.makeText(getApplicationContext(), "Please enter Date of birth correctly!", Toast.LENGTH_SHORT).show();
                    return;
                }
           final   FirebaseUser user = auth.getCurrentUser();
        auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(PSignUpPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(PSignUpPage.this, "waiting .." , Toast.LENGTH_SHORT).show();

                                if (!task.isSuccessful()) {
                                    Toast.makeText(PSignUpPage.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                        Toast.makeText(PSignUpPage.this, "successfully registered " , Toast.LENGTH_SHORT).show();
                                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                    Patient.child("Name").child(uid).setValue(name1);
                                    Patient.child("Phone").child(uid).setValue(phone1);
                                    Patient.child("email").child(uid).setValue(email);
                                    Patient.child("Password").child(uid).setValue(password);
                                    Patient.child("DOB").child(uid).setValue(DOB1);

                                    startActivity(new Intent(PSignUpPage.this, Patient.class));
                                    finish();
                                }
                            }
                        });


            }
        });
    }




}

