package com.example.user1.urnextapp;
//android
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//java
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcceptList extends ArrayAdapter<Accept_List_Information> {
    // class for custom list view with adapter of Accept_List_Information object

    private Activity context;
    static List<Accept_List_Information> Patient_List;
    private TextView textViewGenre;
    private Button accept_button;
    private String Message ="Hi, it's your turn!"; // message to send when nurse click accept
    private FirebaseFirestore fire_store; // to store user document



    AcceptList(Activity context, List<Accept_List_Information> patient) {
        super(context, R.layout.list_layout, patient);
        this.context = context;
        this.Patient_List = patient;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"InflateParams", "ViewHolder"}) View listViewItem = inflater.inflate(R.layout.list_layout, null, true);


        TextView textViewName = (TextView) listViewItem.findViewById(R.id.pname);
        textViewGenre = (TextView) listViewItem.findViewById(R.id.time);
        accept_button= (Button)listViewItem.findViewById(R.id.accept);

        fire_store= FirebaseFirestore.getInstance(); // fire store to store user notification inside his document

        Accept_List_Information patient_Information = Patient_List.get(position);
        textViewName.setText(patient_Information.getname());

        accept_button.setOnClickListener(new View.OnClickListener() {
            // check if the button clicked then remove patient from list view by his position
            // and also remove him from waiting table by appointment time and from appointment table by his phone number
            @Override
            public void onClick(View v) {

                Patient_List.remove(position);
                AcceptPatient.AcceptList.setAdapter(AcceptPatient.adapter);
                AcceptPatient.adapter.notifyDataSetChanged();
                // delete him from waiting data
                AcceptPatient.waiting_table.child(AcceptPatient.Doctor_Name).child(AcceptPatient.appTime.get(position)).removeValue();
                // delete him from appointment data
                AcceptPatient.external_data.child("Appointment").child("Dental clinic").child(AcceptPatient.patient_phone.get(position)).removeValue();
                // store admission time when nurse click accept
                final Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                String Today= today.format("%k:%M");
                DateFormat df = new java.text.SimpleDateFormat("hh:mm");
                try {
                    Date date1=df.parse(Today);
                    Date date2 =df.parse(AcceptPatient.appTime.get(position));

                    String admition=(date1.getTime()-date2.getTime()+"");
                    AcceptPatient.patient_table.child(AcceptPatient.patient_ID.get(position)).child(" admission time").setValue(admition);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                // each user have a document of notification
                Map<String, Object> notification_message= new HashMap<>(); //map between user and his collection
                notification_message.put("Message",Message); //put the message in patient collection
                notification_message.put("From",AcceptPatient.User_ID );
                fire_store.collection("Usres/"+AcceptPatient.patient_ID.get(position)+"/Notifications").add(notification_message).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                    }
                });
                AcceptPatient.patient_ID.remove(position);


            }
        });

        //calculate estimated time
           /* final Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            String tod = today.format("%k:%M");

            DateFormat df = new java.text.SimpleDateFormat("hh:mm");
            Date date1 = null;
            try {

                date1 = df.parse(patient_Information.gettime());
                Date date2 = df.parse(tod);
                long diff = date2.getTime() - date1.getTime();

                long timeInSeconds = diff / 1000;
                long hours, minutes, second;
                hours = timeInSeconds / 3600;
                timeInSeconds = timeInSeconds - (hours * 3600);
                minutes = timeInSeconds / 60;
                timeInSeconds = timeInSeconds - (minutes * 60);

                reverseTimer((int) diff);

                //  estimate.setText(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

        return listViewItem;

    }
    private void reverseTimer(int Seconds) {


        new CountDownTimer(Seconds, 1000) {

            public void onTick(long millisUntilFinished) {
                for(int i=0;i<2;i++) {
                    int seconds = (int) (millisUntilFinished / 1000);
                    int hours = seconds / (60 * 60);
                    int tempMint = (seconds - (hours * 60 * 60));
                    int minutes = tempMint / 60;
                    seconds = tempMint - (minutes * 60);
                    String t = String.format("%02d", hours)
                            + ":" + String.format("%02d", minutes)
                            + ":" + String.format("%02d", seconds);
                    // textViewGenre.setText(t);
                }

            }

            public void onFinish() {

                textViewGenre.setText("Your turn!");
            }
        }.start();
    }

}


