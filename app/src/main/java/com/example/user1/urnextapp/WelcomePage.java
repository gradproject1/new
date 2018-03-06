package com.example.user1.urnextapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class WelcomePage extends AppCompatActivity {

    Button btnSignIn,btnSignUp;
    TextView txtSlogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        btnSignIn =(Button)findViewById(R.id.btn1);
        btnSignUp=(Button)findViewById(R.id.btn2);
        //external database

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view)
            {
                Intent signin = new Intent(WelcomePage.this, SignInPage.class);
                startActivity(signin);
            }

        });

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view)
            {
                Intent signup = new Intent(WelcomePage.this, PSignUpPage.class);
                startActivity(signup);
            }

        });


    }

    
}
