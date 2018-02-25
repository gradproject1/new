package com.example.user1.urnextapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class NDprofile extends Fragment {
    //Constructor default
    public NDprofile(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View PageOne = inflater.inflate(R.layout.fragment_ndprofile, container, false);



        return PageOne;
    }
}
