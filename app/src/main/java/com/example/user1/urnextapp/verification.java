package com.example.user1.urnextapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class verification extends AppCompatActivity {
    private TextView virifyEmail,press;
    private Button send;
    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        send=(Button) findViewById(R.id.sendAgain);
        press=(TextView) findViewById(R.id.press);
        virifyEmail= (TextView) findViewById(R.id.virifyEmail);

        if (!user.isEmailVerified())
        {
            String text = "we need to verify your email address we have sent an email at "+user.getEmail()+" to varify your address. Please click the link on that email";
            virifyEmail.setText(text);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(verification.this, "successfully send the verification email! " , Toast.LENGTH_LONG).show();
                    user.sendEmailVerification();
                }
            });
        }

        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.isEmailVerified()){
                    Intent intent = new Intent(verification.this, Patient.class);
                    startActivity(intent);
                }
               else {
                    Toast.makeText(verification.this, "you not verified your email yet!", Toast.LENGTH_LONG).show();
                    firebaseAuth.getCurrentUser().reload();
                }

            }
        });
    }
}
