package com.erelmanagement.registerlogin.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.erelmanagement.registerlogin.R;
import com.erelmanagement.registerlogin.session.SessionManager;

import java.util.HashMap;

public class MainActivityhome extends AppCompatActivity {
    private SessionManager sessionManager;
    private String email;
    private TextView nama;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityhome);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        nama = findViewById(R.id.email);
        logout = findViewById(R.id.logout);
        email = user.get(sessionManager.EMAIL);
        nama.setText(email);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });
    }
}