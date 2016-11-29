package com.momentum.digitalsignagedemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnTabSelectedListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Justin on 10/10/2016.
 */

public class MainActivity extends AppCompatActivity {
    private BottomBar bottomBar;
    private User currentUser;
    private static final int CAMERA_PERMISSION_CODE = 10;
    private ArrayList<Ad> ads;
    Bundle b;
    SecondFragment two;
    ThirdFragment three;
    String str_display = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ads = new ArrayList<Ad>();
        for (int i = 0; i < 6; i++) {
            ads.add(new Ad("No ads yet :(", "Nothing here.", "Nothing here.", "Nothing here.", "http://i.imgur.com/k05H7bH.png", "http://i.imgur.com/k05H7bH.png"));
        }

        currentUser = (User)getIntent().getSerializableExtra("User");

        bottomBar = BottomBar.attach(this, savedInstanceState);
        two = SecondFragment.newInstance();
        three = ThirdFragment.newInstance();
        bottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
                new BottomBarFragment(FirstFragment.newInstance(currentUser), R.drawable.ic_login_white_24dp, "Profile"),
                new BottomBarFragment(two, R.drawable.ic_qrreader_on_white_24dp, "QR Scanner"),
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

        b = new Bundle();
        b.putParcelableArrayList("adsList", ads);
        three.setArguments(b);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != CAMERA_PERMISSION_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            two.scanFromFragment();
            return;
        }
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission Error")
                .setMessage(R.string.msg)
                .setPositiveButton(android.R.string.ok, listener)
                .show();
    }

    public void onButtonClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Log out");
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

    public void handleResult(String s, JSONObject object){
        ads.clear();
        processJson(object);
        three.updateAdapter();
        bottomBar.selectTabAtPosition(2, true);
        str_display = s;
        pushMetrics();
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
        builder.setTitle("Log out");
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
