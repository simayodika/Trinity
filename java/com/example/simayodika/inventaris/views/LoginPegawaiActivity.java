package com.example.simayodika.inventaris.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.simayodika.inventaris.R;
import com.example.simayodika.inventaris.helpers.Api;
import com.example.simayodika.inventaris.helpers.Util;
import com.example.simayodika.inventaris.views.pegawai.PegawaiActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginPegawaiActivity extends AppCompatActivity {

    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    String username, password;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pegawai);
        ButterKnife.bind(this);

        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = this.getSharedPreferences(Util.SHARED_NAME, Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtUsername.getText().toString();
                password = edtPassword.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(LoginPegawaiActivity.this, "Masukkan username dan password!!!", Toast.LENGTH_SHORT).show();
                } else {
                    loginPegawai();
                }
            }
        });
    }

    private void loginPegawai() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getLoginPegawai(username, password), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                    if (success.equals("1")) {
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Util.sharedId, object.getString("IDPegawai_"));
                            editor.putString(Util.sharedNama, object.getString("NamaPegawai_"));
                            editor.putString(Util.sharedNip, object.getString("Nip_"));
                            editor.putString(Util.sharedLevel, object.getString("Alamat_"));
                            editor.putString(Util.sharedLevel, "3");
                            editor.putString(Util.sharedUsername, object.getString("Username_"));
                            editor.putString(Util.sharedPassword, object.getString("Password_"));
                            editor.apply();

                            Intent intent = new Intent(LoginPegawaiActivity.this, PegawaiActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    if (success.equals("0")) {
                        Toast.makeText(LoginPegawaiActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
