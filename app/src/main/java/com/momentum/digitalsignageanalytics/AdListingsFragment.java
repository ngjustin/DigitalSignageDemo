package com.momentum.digitalsignageanalytics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * Created by Justin on 10/11/2016.
 */

public class AdListingsFragment extends Fragment {
    private ArrayList<Ad> ads;
    private RecyclerView rv;
    private RVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ad_listings_frag, container, false);
        getActivity().setTitle("Ads");

        rv = (RecyclerView)v.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        ads = getArguments().getParcelableArrayList("adsList");
        initializeAdapter();

        return v;
    }

    public static AdListingsFragment newInstance() {
        AdListingsFragment f = new AdListingsFragment();
        return f;
    }

    public void initializeAdapter(){
        adapter = new RVAdapter(ads);
        rv.setAdapter(adapter);
    }

    public void updateAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);
        }
    }
}