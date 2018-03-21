package com.example.user1.urnextapp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


public class addDN extends Fragment {

   private Button mSelectImage;
   private Button mSelectVideo;
   private Button mSelectFashion;
   private Button mSelectBook;
   private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
   private StorageReference mStorage;
   private static final int IMAGE_INTENT = 1;
   private static final int VIDEO_INTENT = 2;
   private static final int FASHION_INTENT = 3;
   private static final int BOOK_INTENT = 4;
   private ProgressDialog mprogressDialog;
   private Button logout;

   //Constructor default
   public addDN(){};

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View PageOne = inflater.inflate(R.layout.fragment_add_dn, container, false);


      mStorage = FirebaseStorage.getInstance().getReference();
      mSelectImage = (Button) PageOne.findViewById(R.id.selectImage);
      mSelectVideo = (Button) PageOne.findViewById(R.id.selectVideo);
      mSelectFashion = (Button) PageOne.findViewById(R.id.selectFashion);
      mSelectBook = (Button) PageOne.findViewById(R.id.selectBook);
      logout = (Button) PageOne.findViewById(R.id.logout3);
      mprogressDialog = new ProgressDialog(getActivity());



      mSelectImage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent();
            //Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select image"),IMAGE_INTENT);
         }
      });
////////////////////////////////////////////////////////////////
      mSelectVideo.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent();
            //Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select video"),VIDEO_INTENT);
         }
      });
/////////////////////////////////////////////////////////////////
      mSelectFashion.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent();
            //Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select fashion"),FASHION_INTENT);
         }
      });
///////////////////////////////////////////////////////////////////
      mSelectBook.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent();
            //Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("pdf/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select book"),BOOK_INTENT);
         }
      });
//////////////////////////////////////////////////////////////////////
      logout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            new AlertDialog.Builder(getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Log out")
                    .setMessage("Are you sure you want to logging out?")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                    {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                          if(firebaseAuth != null)
                          {
                             firebaseAuth.signOut();
                             Toast.makeText(getContext(),"You successfully logged out!", Toast.LENGTH_SHORT).show();
                             Intent h= new Intent(getContext(), WelcomePage.class);
                             startActivity(h); }
                       }

                    })
                    .setNegativeButton("Cancel", null)
                    .show();
         }

      });
      return PageOne;
   }
   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent Data){
      super.onActivityResult(requestCode,resultCode,Data);
      if(resultCode == RESULT_OK ){
         if(requestCode == IMAGE_INTENT){
         mprogressDialog.setMessage(" Uploading... ");
         mprogressDialog.show();
         Uri uri = Data.getData();
         StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
         filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               Toast.makeText(getActivity(),"Upload done",Toast.LENGTH_LONG).show();
               mprogressDialog.dismiss();

            }
         });
      }
      else if(requestCode == VIDEO_INTENT){

            mprogressDialog.setMessage(" Uploading... ");
            mprogressDialog.show();

            Uri uri = Data.getData();
            StorageReference filepath = mStorage.child("Videos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  Toast.makeText(getActivity(),"Upload done",Toast.LENGTH_LONG).show();
                  mprogressDialog.dismiss();

               }
            });
         }

         else if(requestCode == FASHION_INTENT){
            mprogressDialog.setMessage(" Uploading... ");
            mprogressDialog.show();

            Uri uri = Data.getData();
            StorageReference filepath = mStorage.child("Fashion").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  Toast.makeText(getActivity(),"Upload done",Toast.LENGTH_LONG).show();
                  mprogressDialog.dismiss();

               }
            });
         }

         else if(requestCode == BOOK_INTENT){
            mprogressDialog.setMessage(" Uploading... ");
            mprogressDialog.show();

            Uri uri = Data.getData();
            StorageReference filepath = mStorage.child("Books").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  Toast.makeText(getActivity(),"Upload done",Toast.LENGTH_LONG).show();
                  mprogressDialog.dismiss();

               }
            });
         }

      }

   }
}
