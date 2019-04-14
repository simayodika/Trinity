package com.example.simayodika.inventaris.views.operator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.simayodika.inventaris.R;
import com.example.simayodika.inventaris.helpers.Api;
import com.example.simayodika.inventaris.helpers.Util;
import com.example.simayodika.inventaris.views.MainActivity;
import com.example.simayodika.inventaris.views.peminjaman.PeminjamanActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OperatorActivity extends AppCompatActivity {

    @BindView(R.id.llList)
    LinearLayout llList;
    @BindView(R.id.imgPeminjaman)
    ImageView imgPeminjaman;
    @BindView(R.id.imgPengembalian)
    ImageView imgPengembalian;
    @BindView(R.id.llOperator)
    LinearLayout llOperator;
    @BindView(R.id.btnLogOut)
    Button btnLogOut;

    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = this.getSharedPreferences(Util.SHARED_NAME, Context.MODE_PRIVATE);

        imgPeminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OperatorActivity.this, PeminjamanActivity.class);
                startActivity(intent);
            }
        });

        imgPengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPengembalian();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }

    private void dialogPengembalian() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_pengembalian, null);
        dialog.setView(dialogView);
        dialog.setTitle("Masukkan kode peminjaman");
        dialog.setCancelable(true);

        final EditText edtKodePinjaman = dialogView.findViewById(R.id.edtKodePinjaman);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String kode = edtKodePinjaman.getText().toString();
                if (kode.isEmpty()) {
                    Toast.makeText(OperatorActivity.this, "Masukkan kode peminjaman!!!", Toast.LENGTH_SHORT).show();
                } else {
                    cekKode(kode);
                }
            }
        });
        dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void cekKode(final String kode) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String tglSkrg = simpleDateFormat.format(date);
        Log.i("TAG", "cekKode: " + Api.getCekKode(kode, tglSkrg));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getCekKode(kode, tglSkrg), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("true")) {
                    prosesPengembalian(kode);
                }
                if (response.contains("date-false")) {
                    Toast.makeText(OperatorActivity.this, "kode tidak valid / sudah melewati tanggal", Toast.LENGTH_SHORT).show();
                }
                if (response.contains("false")) {
                    Toast.makeText(OperatorActivity.this, "kode tidak valid / sudah melewati tanggal", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void prosesPengembalian(String kode) {
        Log.i("TAG", "prosesPengembalian: " + Api.getPengembalian(kode));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getPengembalian(kode), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        Log.i("VolleyResponse", "Success: " + jsonObject);
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
        Toast.makeText(OperatorActivity.this, "Barang dikembalikan", Toast.LENGTH_SHORT).show();
    }

    private void logOut() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Util.sharedId, "");
        editor.putString(Util.sharedNama, "");
        editor.putString(Util.sharedNip, "");
        editor.putString(Util.sharedLevel, "");
        editor.putString(Util.sharedUsername, "");
        editor.putString(Util.sharedPassword, "");
        editor.apply();

        Intent intent = new Intent(OperatorActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
