package com.momentum.digitalsignagedemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

/**
 * Created by Justin on 10/11/2016.
 */

public class SecondFragment extends Fragment {

    public SecondFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_frag, container, false);
        getActivity().setTitle("QR Scanner");

        FloatingActionButton scan = (FloatingActionButton) v.findViewById(R.id.button);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanFromFragment();
            }
        });

        return v;
    }

    public void scanFromFragment() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("");
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    public static SecondFragment newInstance() {
        SecondFragment f = new SecondFragment();
        return f;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                final String resText = result.getContents();
                final MainActivity act = (MainActivity) getActivity();
                if("123456789".contains(resText)) {
                    builder.setTitle("Scan Success");
                    builder.setMessage("The ad feed has been updated with ads.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (resText.equals("1")) {
                                new DownloadWebpageTask(new AsyncResult() {
                                    @Override
                                    public void onResult(JSONObject object) {
                                        act.handleResult("Student Union Display 1", object);
                                    }
                                }, getContext()).execute("https://spreadsheets.google.com/tq?key=1a7_HmbfYc2sWd95JiSH6ikwG6ikffGUQ5Df81VcoekM");
                            } else if (resText.equals("2")) {
                                new DownloadWebpageTask(new AsyncResult() {
                                    @Override
                                    public void onResult(JSONObject object) {
                                        act.handleResult("Student Union Display 2", object);
                                    }
                                }, getContext()).execute("https://spreadsheets.google.com/tq?key=1SpZGMkAQZtPFFDmbN7FZanAXj930qm9Z-B22s7KW0I4");
                            } else if (resText.equals("3")) {
                                new DownloadWebpageTask(new AsyncResult() {
                                    @Override
                                    public void onResult(JSONObject object) {
                                        act.handleResult("Student Union Display 3", object);
                                    }
                                }, getContext()).execute("https://spreadsheets.google.com/tq?key=1du94yZUR8MLnKfqlYGmlOruQAPaNgAvyNgajdO51YUU");
                            }
                        }
                    });
                } else {
                    builder.setTitle("Scan Failure");
                    builder.setMessage("Invalid QR code scanned.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                }
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }
}