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

public class DetailInventarisActivity extends AppCompatActivity {

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
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.btnHapus)
    Button btnHapus;

    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;
    List<String> dataRuang;
    List<String> dataJenis;

    String idInvent, namaInvent, jumlahInvent, jenisInvent, ruangInvent, kondisiInvent, ketInvent;
    long jenisID, ruangID, kondisiID, ketID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_inventaris);
        ButterKnife.bind(this);
        sharedPreferences = this.getSharedPreferences(Util.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);
        dataRuang = new ArrayList<>();
        dataJenis = new ArrayList<>();

        getSetExtra();

        getSpnJenis();
        getSpnRuang();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUpdatedData();
                updateInvent();
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
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
        spnJenisInvent.setSelection(Integer.parseInt(jenisInvent));
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
        spnRuangInvent.setSelection(Integer.parseInt(ruangInvent));
    }

    private void getSetExtra() {
        //get data from bundle
        idInvent = getIntent().getStringExtra("idinvent");
        namaInvent = getIntent().getStringExtra("nama");
        jumlahInvent = getIntent().getStringExtra("jumlah");
        jenisInvent = getIntent().getStringExtra("jenis");
        ruangInvent = getIntent().getStringExtra("ruang");
        kondisiInvent = getIntent().getStringExtra("kondisi");
        ketInvent = getIntent().getStringExtra("ket");

        //change for spinner
        jenisID = Long.parseLong(jenisInvent) - 1;
        ruangID = Long.parseLong(ruangInvent) - 1;
        kondisiID = Long.parseLong(kondisiInvent) - 1;
        ketID = Long.parseLong(ketInvent) - 1;
        jenisInvent = String.valueOf(jenisID);
        ruangInvent = String.valueOf(ruangID);
        kondisiInvent = String.valueOf(kondisiID);
        ketInvent = String.valueOf(ketID);

        //set on layout
        edtNamaInvent.setText(namaInvent);
        edtJumlahInvent.setText(jumlahInvent);
        spnKondisiInvent.setSelection(Integer.parseInt(kondisiInvent));
        spnKetInvent.setSelection(Integer.parseInt(ketInvent));
    }

    private void getUpdatedData() {
        namaInvent = edtNamaInvent.getText().toString();
        jumlahInvent = edtJumlahInvent.getText().toString();
        kondisiID = spnKondisiInvent.getSelectedItemPosition() + 1;
        ketID = spnKetInvent.getSelectedItemPosition() + 1;
        jenisID = spnJenisInvent.getSelectedItemPosition() + 1;
        ruangID = spnRuangInvent.getSelectedItemPosition() + 1;

        kondisiInvent = String.valueOf(kondisiID);
        ketInvent = String.valueOf(ketID);
        jenisInvent = String.valueOf(jenisID);
        ruangInvent = String.valueOf(ruangID);

        String AP = Api.getUpdateInvent(idInvent, namaInvent, kondisiInvent, ketInvent, jumlahInvent, jenisInvent, ruangInvent);
        Log.i("APPPP", "getSetExtra: " + AP);
    }

    private void updateInvent() {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getUpdateInvent(idInvent, namaInvent, kondisiInvent, ketInvent, jumlahInvent, jenisInvent, ruangInvent), new Response.Listener<String>() {
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
        Toast.makeText(this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DetailInventarisActivity.this, AdminActivity.class);
        startActivity(intent);
        finish();
    }

    private void deleteData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getDeleteInvent(idInvent), new Response.Listener<String>() {
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
        Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DetailInventarisActivity.this, AdminActivity.class);
        startActivity(intent);
        finish();
    }
}
