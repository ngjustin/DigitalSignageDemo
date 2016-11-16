package com.momentum.digitalsignagedemo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnTabSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private BottomBar bottomBar;
    private ZXingScannerView mScannerView;
    private User currentUser;
    private Bundle instanceState;
    private SparseIntArray mErrorString;
    private static final int REQUEST_PERMISSION = 10;
    private int urlIndex;
    private ArrayList<Ad> ads;
    ThirdFragment three;
    String str_display = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instanceState = savedInstanceState;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ads = new ArrayList<Ad>();
        for (int i = 0; i < 6; i++) {
            ads.add(new Ad("No ads yet :(", "Nothing here.", "Nothing here.", "Nothing here.", "http://i.imgur.com/k05H7bH.png", "http://i.imgur.com/k05H7bH.png"));
        }

        currentUser = (User)getIntent().getSerializableExtra("User");
        addFragments(instanceState);

        mErrorString = new SparseIntArray();
        requestAppPermissions(new String[] {
                        Manifest.permission.CAMERA },
                R.string.msg, REQUEST_PERMISSION);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState(outState);
    }

    public void addFragments(Bundle savedInstanceState) {
        bottomBar = BottomBar.attach(this, savedInstanceState);
        three = ThirdFragment.newInstance();
        bottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
                new BottomBarFragment(FirstFragment.newInstance(currentUser), R.drawable.ic_login_white_24dp, "Profile"),
                new BottomBarFragment(SecondFragment.newInstance(), R.drawable.ic_qrreader_on_white_24dp, "QR Scanner"),
                new BottomBarFragment(three, R.drawable.ic_ads_white_24dp, "Ads"));
        bottomBar.mapColorForTab(0, "#3B494C");
        bottomBar.mapColorForTab(1, "#00796B");
        bottomBar.mapColorForTab(2, "#7B1FA2");

        bottomBar.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                switch (position) {
                    case 0:
                        // Item 1 Selected
                }
            }
        });

        bottomBar.setActiveTabColor("#039789");

        Bundle b = new Bundle();
        b.putParcelableArrayList("adsList", ads);
        three.setArguments(b);
    }

    public void onButtonClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Log out?");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onClick(View view){
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    /*protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }*/

    @Override
    public void handleResult(Result result){
        Log.v("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        final String resText = result.getText();
        if("123456789".contains(resText)) {
            urlIndex = Integer.parseInt(resText);

            builder.setTitle("Scan Success");
            builder.setMessage("The ad feed has been updated with ads.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mScannerView.stopCamera();
                    setContentView(R.layout.activity_main);
                    if (resText.equals("1")) {
                        new DownloadWebpageTask(new AsyncResult() {
                            @Override
                            public void onResult(JSONObject object) {
                                ads.clear();
                                processJson(object);
                                str_display = "Student Union Monitor 1";
                                pushMetrics();
                            }
                        }, MainActivity.this).execute("https://spreadsheets.google.com/tq?key=1a7_HmbfYc2sWd95JiSH6ikwG6ikffGUQ5Df81VcoekM");
                    } else if (resText.equals("2")) {
                        new DownloadWebpageTask(new AsyncResult() {
                            @Override
                            public void onResult(JSONObject object) {
                                ads.clear();
                                processJson(object);
                                str_display = "Student Union Monitor 2";
                                pushMetrics();
                            }
                        }, MainActivity.this).execute("https://spreadsheets.google.com/tq?key=1SpZGMkAQZtPFFDmbN7FZanAXj930qm9Z-B22s7KW0I4");
                    } else if (resText.equals("3")) {
                        new DownloadWebpageTask(new AsyncResult() {
                            @Override
                            public void onResult(JSONObject object) {
                                ads.clear();
                                processJson(object);
                                str_display = "Student Union Monitor 3";
                                pushMetrics();
                            }
                        }, MainActivity.this).execute("https://spreadsheets.google.com/tq?key=1du94yZUR8MLnKfqlYGmlOruQAPaNgAvyNgajdO51YUU");
                    }
                    addFragments(instanceState);
                    bottomBar.selectTabAtPosition(1, true);
                }
            });
        } else {
            builder.setTitle("Scan Failure");
            builder.setMessage("Invalid QR code scanned.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mScannerView.stopCamera();
                    setContentView(R.layout.activity_main);
                    addFragments(instanceState);
                    bottomBar.selectTabAtPosition(1, true);
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onPermissionsGranted(int requestCode) {
        //Toast.makeText(getApplicationContext(), "Permission granted.", Toast.LENGTH_LONG).show();
    }

    public void requestAppPermissions(final String[] requestedPermissions, final int stringId, final int requestCode) {
        mErrorString.put(requestCode, stringId);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermissions = false;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
            showRequestPermissions = showRequestPermissions || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (showRequestPermissions) {
                Snackbar.make(findViewById(android.R.id.content), stringId,
                        Snackbar.LENGTH_INDEFINITE).setAction("GRANT", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(MainActivity.this, requestedPermissions, requestCode);
                    }
                }).show();
            }
            else {
                ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
            }
        }
        else {
            onPermissionsGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }

        if ((grantResults.length > 0) && PackageManager.PERMISSION_GRANTED == permissionCheck) {
            onPermissionsGranted(requestCode);
        }
        else {
            Snackbar.make(findViewById(android.R.id.content), mErrorString.get(requestCode),
                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.setData(Uri.parse("package:" + getPackageName()));
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(i);
                }
            }).show();
        }
    }

    private void processJson(JSONObject object) {
        try {
            JSONArray rows = object.getJSONArray("rows");
            for (int r = 1; r < rows.length(); r++) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                String adTitle = columns.getJSONObject(0).getString("v");
                String adDetails = columns.getJSONObject(1).getString("v");
                String adDescription = columns.getJSONObject(2).getString("v");
                String adLink = columns.getJSONObject(3).getString("v");
                String adThumbnail = columns.getJSONObject(4).getString("v");
                String adPicture = columns.getJSONObject(5).getString("v");

                Ad a = new Ad(adTitle, adDetails, adDescription, adLink, adThumbnail, adPicture);
                ads.add(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void pushMetrics() {
        String str_major = currentUser.getMajor();
        String str_age = Integer.toString(currentUser.getAge());
        String str_gender = currentUser.getGender();
        String str_time = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a").format(new Date());
        String type = "metrics";

        MetricsWorker metricsWorker = new MetricsWorker(this);
        metricsWorker.execute(type, str_major, str_age, str_gender, str_display, str_time);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Log out?");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
