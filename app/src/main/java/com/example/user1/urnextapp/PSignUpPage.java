package com.example.user1.urnextapp;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


    EditText name,phone,DOB;

  //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psign_up_page);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.pass);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        DOB=(EditText) findViewById(R.id.DOB);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);


        buttonSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view)
            {
                Intent signup = new Intent(PSignUpPage.this, Patient.class);
                startActivity(signup);
            }

        });}
    private void registerUser(){

        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        String name1 = name.getText().toString().trim();
        String phone1=phone.getText().toString().trim();
        String DOB1 = DOB.getText().toString().trim();



        if(email.isEmpty() && !email.contains("@")){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(name1.isEmpty()){
            Toast.makeText(this,"Please enter name",Toast.LENGTH_LONG).show();
            return;
        }

        if(phone1.isEmpty()){
            Toast.makeText(this,"Please enter phone number",Toast.LENGTH_LONG).show();
            return;
        }

        if(DOB1.isEmpty()){
            Toast.makeText(this,"Please enter Date of Birth",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
//initilze DB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Patient = database.getReference("Patient");

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(PSignUpPage.this,"Successfully registered",Toast.LENGTH_LONG).show();
                        }else{
                            //display some message here
                            Toast.makeText(PSignUpPage.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
        Patient.child("Name").setValue(name1);
        Patient.child("Phone").setValue(phone1);
        Patient.child("email").setValue(email);
        Patient.child("Password").setValue(password);
        Patient.child("DOB").setValue(DOB1);
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener() {            @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(PSignUpPage.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        if (user.isEmailVerified()){
            Toast.makeText(PSignUpPage.this,"THIS EMAIL ALREADY REGISTER AND VARIED",  Toast.LENGTH_SHORT).show();
        }

        else
        if (!user.isEmailVerified()){
            Toast.makeText(PSignUpPage.this,"confirm your email",  Toast.LENGTH_SHORT).show();

            user.sendEmailVerification();
        }}

        public void onClick(View view) {
        //calling register method on click
        registerUser();
    }


}
