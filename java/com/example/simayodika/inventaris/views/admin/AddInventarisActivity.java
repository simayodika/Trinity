package com.example.simayodika.inventaris.views.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

import com.example.simayodika.inventaris.R;
import com.example.simayodika.inventaris.helpers.Api;
import com.example.simayodika.inventaris.helpers.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddInventarisActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaInvent)
    EditText edtNamaInvent;
    @BindView(R.id.spnKondisiInvent)
    Spinner spnKondisiInvent;
    @BindView(R.id.spnKetInvent)
    Spinner spnKetInvent;
    @BindView(R.id.edtJumlahInvent)
    EditText edtJumlahInvent;
    @BindView(R.id.spnJenisInvent)
    Spinner spnJenisInvent;
    @BindView(R.id.spnRuangInvent)
    Spinner spnRuangInvent;
    @BindView(R.id.btnTambah)
    Button btnTambah;

    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;
    List<String> dataRuang;
    List<String> dataJenis;
    String nama, kondisi, ket, jumlah, jenis, ruang, idpetugas;
    long idkondisi, idket, idjenis, idruang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventaris);
        ButterKnife.bind(this);
        sharedPreferences = this.getSharedPreferences(Util.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);
        dataRuang = new ArrayList<>();
        dataJenis = new ArrayList<>();

        getSpnJenis();
        getSpnRuang();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                addInvent();
            }
        });
    }

    private void getSpnJenis() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getJenis, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    setSpnJenis(jsonArray);
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

    private void setSpnJenis(JSONArray jsonArray) {
        dataJenis.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                dataJenis.add(object.getString("NamaJenis_"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dataJenis);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnJenisInvent.setAdapter(spinnerAdapter);
    }

    private void getSpnRuang() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getRuang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    setSpnRuang(jsonArray);
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

    private void setSpnRuang(JSONArray jsonArray) {
        dataRuang.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                dataRuang.add(object.getString("NamaRuang_"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dataRuang);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRuangInvent.setAdapter(spinnerAdapter);
    }

    private void getData() {
        idkondisi = spnKondisiInvent.getSelectedItemId() + 1;
        idket = spnKetInvent.getSelectedItemId() + 1;
        idjenis = spnJenisInvent.getSelectedItemId() + 1;
        idruang = spnRuangInvent.getSelectedItemId() + 1;

        nama = edtNamaInvent.getText().toString();
        jumlah = edtJumlahInvent.getText().toString();
        kondisi = String.valueOf(idkondisi);
        ket = String.valueOf(idkondisi);
        jenis = String.valueOf(idjenis);
        ruang = String.valueOf(idruang);
        idpetugas = sharedPreferences.getString(Util.sharedId, "");
    }

    private void addInvent() {
        if (!nama.isEmpty() && !jumlah.isEmpty()) {
            nama = nama.replaceAll(" ", "%20");
            final StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getAddInvent(
                    nama, kondisi, ket, jumlah, jenis, ruang, idpetugas), new Response.Listener<String>() {
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
            Log.i("VolleyResponse", "addInvent: " + stringRequest);
            requestQueue.add(stringRequest);
            Toast.makeText(this, "Data berhasil masuk", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddInventarisActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Isi data dengan lengkap!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
