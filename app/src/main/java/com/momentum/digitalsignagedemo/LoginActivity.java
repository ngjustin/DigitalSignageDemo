package com.momentum.digitalsignagedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohammad on 10/27/2016.
 */
public class LoginActivity extends AppCompatActivity {

    User justin = new User("Justin", "Ng", "Computer Science", 20, "Male");
    User johnny = new User("Johnny", "Appleseed", "Computer Science", 20, "Male");
    User jane = new User("Jane", "Doe", "Software Engineering", 18, "Female");
    User lenny = new User("Lenny", "Linux", "Psychology", 43, "Male");
    User wendy = new User("Wendy", "Darling", "Nursing", 21, "Female");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setTitle("Login");
        setContentView(R.layout.login_activity);
    }

    public void loginClicked(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        switch(view.getId())
        {
            case R.id.buttonJustin:
                intent.putExtra("User", justin);
                break;
            case R.id.buttonJohnny:
                intent.putExtra("User", johnny);
                break;
            case R.id.buttonJane:
                intent.putExtra("User", jane);
                break;
            case R.id.buttonLenny:
                intent.putExtra("User", lenny);
                break;
            case R.id.buttonWendy:
                intent.putExtra("User", wendy);
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }
        startActivity(intent);
    }
}
