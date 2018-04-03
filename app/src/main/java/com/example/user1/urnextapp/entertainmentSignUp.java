package com.example.user1.urnextapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Rawan on 03/04/18.
 */

public class entertainmentSignUp extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference Patient = database.getReference("Patient");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment_sign_up);}

    public void selectItem (View view)
    {
        boolean checked =((CheckBox)view ).isChecked();

        switch (view.getId())
        {
            case R.id.video:
                if (checked)
               Patient.child("video").setValue("1");
                else
                    Patient.child("video").removeValue();
                break;
            case R.id.photo:
                if (checked)
                    Patient.child("photo").setValue("1");
                else
                    Patient.child("photo").removeValue();
                break;
            case R.id.fashion:
                if (checked)
                    Patient.child("fashion").setValue("1");
                else
                    Patient.child("fashion").removeValue();
                break;
            case R.id.sport:
                if (checked)
                    Patient.child("sport").setValue("1");
                else
                    Patient.child("sport").removeValue();
                break;

        }
    }


}
