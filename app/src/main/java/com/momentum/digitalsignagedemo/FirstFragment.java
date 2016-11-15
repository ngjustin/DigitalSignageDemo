package com.momentum.digitalsignagedemo;

import android.content.Intent;
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

public class FirstFragment extends Fragment {
    private static User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.material_design_profile_screen_xml_ui_design, container, false);
        //View v = inflater.inflate(R.layout.first_frag, container, false);
        //TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        //tv.setText(currentUser.getFirstName() + " " + currentUser.getLastName());

        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().setTitle("Profile");

        return v;
    }

    public static FirstFragment newInstance(User user) {
        FirstFragment f = new FirstFragment();

        Bundle b = new Bundle();
        b.putSerializable("User", user);
        f.setArguments(b);
        currentUser = user;

        return f;
    }
}