package com.momentum.digitalsignagedemo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRReader extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.second_frag);*/
    }

    public void onClick(View view){
        /*mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();*/
    }

    protected void onPause(){
        /*super.onPause();
        mScannerView.stopCamera();*/
    }

    @Override
    public void handleResult(Result result){
        /*Log.v("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
        builder.setMessage("Retrieving advertisements...");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // mScannerView.resumeCameraPreview(this);*/
    }

}