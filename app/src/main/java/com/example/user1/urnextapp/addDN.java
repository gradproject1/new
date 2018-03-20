package com.example.user1.urnextapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


public class addDN extends Fragment {

   private Button mSelectImage;
   private StorageReference mStorage;
   private static final int GALARRY_INTENT = 2;
   private ProgressDialog mprogressDialog;


   //Constructor default
   public addDN(){};

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View PageOne = inflater.inflate(R.layout.fragment_add_dn, container, false);


      mStorage = FirebaseStorage.getInstance().getReference();
      mSelectImage = (Button) PageOne.findViewById(R.id.selectImage);
      mprogressDialog = new ProgressDialog(getActivity());

      mSelectImage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent();
            //Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, GALARRY_INTENT);
         }
      });

      return PageOne;
   }
   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent Data){
      super.onActivityResult(requestCode,resultCode,Data);
      if(requestCode == GALARRY_INTENT && resultCode == RESULT_OK ){

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
   }
}
