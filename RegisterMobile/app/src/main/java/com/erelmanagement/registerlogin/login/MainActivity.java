package com.erelmanagement.registerlogin.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.erelmanagement.registerlogin.R;
import com.erelmanagement.registerlogin.api.client;
import com.erelmanagement.registerlogin.home.MainActivityhome;
import com.erelmanagement.registerlogin.register.MainRegister;
import com.erelmanagement.registerlogin.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    SessionManager sessionManager;
    private EditText email,password;
    private Button login;
    private TextView dont_akun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dont_akun = findViewById(R.id.dont_akun);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        dont_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainRegister.class);

                startActivity(intent);;
            }
        });
        sessionManager = new SessionManager(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPass.isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                        int jumlah = mPass.length();
                        if(jumlah>=8){
                            Login(mEmail, mPass);
                        }else{
                            Toast.makeText(MainActivity.this, "Password Minimal 8 Karakter", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Periksa kembali email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    email.setError("Masukan Email");
                    password.setError("Masukan password");
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void Login(final String email, final String password) {

        final AlertDialog alertDialog = new SpotsDialog(this);
        alertDialog.show();
        alertDialog.setMessage("Sedang Mengambil Data...");
        final Call<com.erelmanagement.registerlogin.model.View> users= client.getApi().login(email,password);
        users.enqueue(new Callback<com.erelmanagement.registerlogin.model.View>() {
            @Override
            public void onResponse(Call<com.erelmanagement.registerlogin.model.View> call, retrofit2.Response<com.erelmanagement.registerlogin.model.View> response) {
                if (response.isSuccessful()) {

                    if(response.body().getValue().equals("1")){
                        List<com.erelmanagement.registerlogin.model.View> read = response.body().getResult();

                        for (int i = 0; i < read.size(); i++) {
                            String id = read.get(i).getId();
                            String email = read.get(i).getEmail();
                             alertDialog.dismiss();
                            Intent intent = new Intent(MainActivity.this, MainActivityhome.class);


                            sessionManager.createSession(id, email);
                            startActivity(intent);
                        }

                        alertDialog.dismiss();
                    }else{
                       Toast.makeText(MainActivity.this, "Username / Password salah" , Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }

                }else{
                    alertDialog.dismiss();
                    Toast.makeText(MainActivity.this, ""+email, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this, jObjError.getString("error"), Toast.LENGTH_LONG).show();

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<com.erelmanagement.registerlogin.model.View> call, Throwable t) {
                Toast.makeText(MainActivity.this, "t"+t, Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();

            }
        });


    }
}