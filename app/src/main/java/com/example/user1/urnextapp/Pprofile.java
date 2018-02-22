package com.example.user1.urnextapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Pprofile extends Fragment {
    //Constructor default
    public Pprofile(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View PageTree = inflater.inflate(R.layout.fragment_pprofile, container, false);



        return PageTree;
    }
}
