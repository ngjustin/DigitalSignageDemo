package com.momentum.digitalsignagedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohammad on 10/27/2016.
 */
public class LoginActivity extends Activity {

    ArrayList<User> users = new ArrayList<User>();
    ArrayList<String> names = new ArrayList<String>();

    User johnny = new User("Johnny", "Appleseed", "Computer Science", 20, "Male");
    User jane = new User("Jane", "Doe", "Software Engineering", 18, "Female");
    User lenny = new User("Lenny", "Linux", "Psychology", 43, "Male");
    User wendy = new User("Wendy", "Darling", "Nursing", 21, "Female");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    public void loginClicked(View view){
        //System.out.println("Button pressed");
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        switch(view.getId())
        {
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
        finish();
    }
}
