package com.momentum.digitalsignagedemo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Justin on 10/11/2016.
 */

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_frag, container, false);

        //((AppCompatActivity)getActivity()).getSupportActionBar().show();
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().setTitle("QR Scanner");

        return v;
    }

    public static SecondFragment newInstance() {
        SecondFragment f = new SecondFragment();
        return f;
    }
}