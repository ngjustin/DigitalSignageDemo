package com.momentum.digitalsignagedemo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 10/11/2016.
 */

public class ThirdFragment extends Fragment {

    private ArrayList<Ad> ads;
    private RecyclerView rv;
    private RVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.third_frag, container, false);
        getActivity().setTitle("Ads");

        rv = (RecyclerView)v.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        ads = getArguments().getParcelableArrayList("adsList");
        initializeAdapter();

        return v;
    }

    public static ThirdFragment newInstance() {
        ThirdFragment f = new ThirdFragment();
        return f;
    }

    private void initializeAdapter(){
        adapter = new RVAdapter(ads);
        rv.setAdapter(adapter);
    }
}