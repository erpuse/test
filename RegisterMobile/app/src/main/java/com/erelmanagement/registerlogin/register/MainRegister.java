package com.erelmanagement.registerlogin.register;

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
import com.erelmanagement.registerlogin.login.MainActivity;
import com.erelmanagement.registerlogin.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class MainRegister extends AppCompatActivity {
    SessionManager sessionManager;
    private EditText email,password,first,last;
    private Button register;
    private TextView punya_akun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);
        punya_akun = findViewById(R.id.punya_akun);
        first = findViewById(R.id.first);
        last = findViewById(R.id.last);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        sessionManager = new SessionManager(this);
        punya_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainRegister.this, MainActivity.class);

                startActivity(intent);;
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mfirst = first.getText().toString().trim();
                String mlast = last.getText().toString().trim();
                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if (!mfirst.isEmpty() || !mlast.isEmpty() || !mPass.isEmpty() || !mPass.isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                        int jumlah = mPass.length();
                        if(jumlah>=8){
                            Register(mfirst,mlast,mEmail, mPass);
                        }else{
                            Toast.makeText(MainRegister.this, "Password Minimal 8 Karakter", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainRegister.this, "Periksa kembali email", Toast.LENGTH_SHORT).show();
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
    private void Register(final String first,final String last,final String email, final String password) {

        final AlertDialog alertDialog = new SpotsDialog(this);
        alertDialog.show();
        alertDialog.setMessage("Sedang Mengambil Data...");
        final Call<com.erelmanagement.registerlogin.model.View> users= client.getApi().register(first,last,email,password);
        users.enqueue(new Callback<com.erelmanagement.registerlogin.model.View>() {
            @Override
            public void onResponse(Call<com.erelmanagement.registerlogin.model.View> call, retrofit2.Response<com.erelmanagement.registerlogin.model.View> response) {

                    if (response.isSuccessful()) {
                            if (response.body().getSuccess().equals("1")) {
                                Toast.makeText(MainRegister.this, "Success Tersimpan", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainRegister.this, MainActivity.class);

                                startActivity(intent);;

                            }else{
                                Toast.makeText(MainRegister.this, "Email sudah terdaftar", Toast.LENGTH_LONG).show();

                            }
                        alertDialog.dismiss();

                    } else {
                        Toast.makeText(MainRegister.this, "Gagal"+response, Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    }
            }

            @Override
            public void onFailure(Call<com.erelmanagement.registerlogin.model.View> call, Throwable t) {
                Toast.makeText(MainRegister.this, ""+t, Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();

            }
        });


    }
}