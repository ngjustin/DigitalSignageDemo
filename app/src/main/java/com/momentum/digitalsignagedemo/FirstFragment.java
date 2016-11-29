package com.momentum.digitalsignagedemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        View v = inflater.inflate(R.layout.first_frag, container, false);
        TextView name = (TextView) v.findViewById(R.id.user_profile_name);
        name.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        TextView major = (TextView) v.findViewById(R.id.major);
        major.setText(currentUser.getMajor());
        TextView age = (TextView) v.findViewById(R.id.age);
        age.setText(currentUser.getAge() + " years old");
        TextView gender = (TextView) v.findViewById(R.id.gender);
        gender.setText(currentUser.getGender());

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