package com.example.user1.urnextapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInPage extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference ;
    FirebaseAuth.AuthStateListener mAuthListener;

    EditText inputEmail;
    EditText inputPassword;
    Button signin;
    TextView signup;
    TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        inputEmail = (EditText)findViewById(R.id.editTextEmailAddress);
        inputPassword = (EditText)findViewById(R.id.editText_Password);
        signin = (Button)findViewById(R.id.button3);
        signup = (TextView)findViewById(R.id.textView3);
        forgotPassword = (TextView)findViewById(R.id.textView);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //external database
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:743919137232:android:3c1f58f0b58ae563") // Required for Analytics.
                .setApiKey("AIzaSyDn_FE0FnrMOldDuQ1PQGh5UaN6Mw69rvM") // Required for Auth.
                .setDatabaseUrl("https://external-db-6f551.firebaseio.com/") // Required for RTDB.
                .build();
        // Initialize with secondary app.
        FirebaseApp.initializeApp(this , options, "secondary");
        // Retrieve secondary app.
        final FirebaseApp secondary = FirebaseApp.getInstance("secondary");
        // Get the database for the other app.
        FirebaseDatabase secondaryDatabase = FirebaseDatabase.getInstance(secondary);
        DatabaseReference Doctor = secondaryDatabase.getReference("Doctors");
        DatabaseReference Nurse = secondaryDatabase.getReference("Nurse");
        DatabaseReference Admin = secondaryDatabase.getReference("Admin");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
            }
        };

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String e = inputEmail.getText().toString();
                final String p = inputPassword.getText().toString();
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String emailAdminPattern = "[HAE]+[0-9]+@[a-z]+\\.+[a-z]+";
                final String emailDoctorPattern = "[HDE]+[0-9]+@[a-z]+\\.+[a-z]+";
                final String emailNusrePattern = "[HNE]+[0-9]+@[a-z]+\\.+[a-z]+";

                if (e.isEmpty()) {
                    inputEmail.setError("Please enter your email");
                }
                else if(!e.matches(emailPattern)){
                    inputEmail.setError("Invalid email address");
                }
                else if (p.isEmpty()) {
                    inputPassword.setError("Please enter your password");
                }
                else if (p.length()<5) {
                    inputPassword.setError("Password must to be more than 9 characters");
                }
                if(e.matches(emailAdminPattern)||e.matches(emailDoctorPattern)||e.matches(emailNusrePattern)){

                    firebaseAuth.getInstance(secondary).signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getInstance(secondary).getCurrentUser();
                                String uid = firebaseAuth.getInstance(secondary).getCurrentUser().getUid();
                                if(e.matches(emailAdminPattern)){
                                    Intent i = new Intent(SignInPage.this, Admin.class);
                                    startActivity(i);
                                }
                                else if(e.matches(emailDoctorPattern)){
                                    Intent i = new Intent(SignInPage.this, Doctor.class);
                                    startActivity(i);
                                }
                                else if(e.matches(emailNusrePattern)){
                                    Intent i = new Intent(SignInPage.this, Nurse.class);
                                    startActivity(i);
                                }
                            }
                            if (!task.isSuccessful()) {

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(SignInPage.this, "This user not found, create a new account", Toast.LENGTH_SHORT).show();
                            }
                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SignInPage.this, "The password is invalid, please try valid password", Toast.LENGTH_SHORT).show();
                            }
                            if (e instanceof FirebaseNetworkException) {
                                Toast.makeText(SignInPage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    firebaseAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Intent i = new Intent(SignInPage.this, Patient.class);
                                startActivity(i);
                            }
                            if (!task.isSuccessful()) {

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(SignInPage.this, "This user not found, create a new account", Toast.LENGTH_SHORT).show();
                            }
                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SignInPage.this, "The password is invalid, please try valid password", Toast.LENGTH_SHORT).show();
                            }
                            if (e instanceof FirebaseNetworkException) {
                                Toast.makeText(SignInPage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInPage.this, PSignUpPage.class);
                startActivity(i);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(SignInPage.this, forgotPassword.class);
                startActivity(ii);
            }
        });
    }
}