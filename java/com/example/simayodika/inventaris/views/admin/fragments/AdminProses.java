package com.example.simayodika.inventaris.views.admin.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.simayodika.inventaris.views.peminjaman.PeminjamanActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AdminProses extends Fragment {

    @BindView(R.id.imgPeminjaman)
    ImageView imgPeminjaman;
    @BindView(R.id.imgPengembalian)
    ImageView imgPengembalian;
    Unbinder unbinder;

    Context context;
    RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_proses, container, false);
        unbinder = ButterKnife.bind(this, view);

        context = this.getActivity();
        requestQueue = Volley.newRequestQueue(context);

        imgPeminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PeminjamanActivity.class);
                startActivity(intent);
            }
        });

        imgPengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPengembalian();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void dialogPengembalian() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
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
                    Toast.makeText(context, "Masukkan kode peminjaman!!!", Toast.LENGTH_SHORT).show();
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
                   Toast.makeText(context, "kode tidak valid / sudah melewati tanggal", Toast.LENGTH_SHORT).show();
               }
               if (response.contains("false")) {
                   Toast.makeText(context, "kode tidak valid / sudah melewati tanggal", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(context, "Barang dikembalikan", Toast.LENGTH_SHORT).show();
    }
}
