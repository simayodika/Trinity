package com.example.simayodika.inventaris.views.admin.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.simayodika.inventaris.R;
import com.example.simayodika.inventaris.adapters.AdapterReport;
import com.example.simayodika.inventaris.helpers.Api;
import com.example.simayodika.inventaris.helpers.Util;
import com.example.simayodika.inventaris.modals.ListReport;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AdminReport extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.btnTotal)
    Button btnTotal;
    @BindView(R.id.btnDatePicker)
    Button btnDatePicker;
    @BindView(R.id.txtSelectDate)
    TextView txtSelectDate;
    @BindView(R.id.rvListReport)
    RecyclerView rvListReport;
    @BindView(R.id.swipeListReport)
    SwipeRefreshLayout swipeListReport;
    Unbinder unbinder;

    DatePickerDialog datePickerDialog;
    Calendar calendar;
    Date date;
    SimpleDateFormat simpleDateFormat;

    Context context;
    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;

    AdapterReport adapterReport;
    List<ListReport> list_report;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_report, container, false);
        unbinder = ButterKnife.bind(this, view);

        context = this.getActivity();
        sharedPreferences = context.getSharedPreferences(Util.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(context);

        calendar = Calendar.getInstance();
        date = Calendar.getInstance().getTime();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        swipeListReport.setOnRefreshListener(this);

        rvListReport.setLayoutManager(new LinearLayoutManager(context));
        list_report = new ArrayList<>();
        adapterReport = new AdapterReport(list_report);

        getListReport();

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialogDate();
                sampleDate();
            }
        });

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTotalReport();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        txtSelectDate.setText("Selected Date");
        list_report.clear();
        adapterReport.notifyDataSetChanged();
        getListReport();
    }

    private void dialogTotal(String jumlahInvent, String jumlahOperator, String jumlahPegawai, String barangPinjam, String barangKembali, String barangTerlambat) {
        android.support.v7.app.AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_report, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        final TextView txtJumlahInvent, txtJumlahOpertor, txtJumlahPegawai, txtBarangPinjam, txtBarangKembali, txtBarangTerlambat;
        txtJumlahInvent = dialogView.findViewById(R.id.txtJumlahInvent);
        txtJumlahOpertor = dialogView.findViewById(R.id.txtJumlahOperator);
        txtJumlahPegawai = dialogView.findViewById(R.id.txtJumlahPegawai);
        txtBarangPinjam = dialogView.findViewById(R.id.txtBarangPinjam);
        txtBarangKembali = dialogView.findViewById(R.id.txtBarangKembali);
        txtBarangTerlambat = dialogView.findViewById(R.id.txtBarangTerlambat);

        txtJumlahInvent.setText(jumlahInvent);
        txtJumlahOpertor.setText(jumlahOperator);
        txtJumlahPegawai.setText(jumlahPegawai);
        txtBarangPinjam.setText(barangPinjam);
        txtBarangKembali.setText(barangKembali);
        txtBarangTerlambat.setText(barangTerlambat);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getTotalReport() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getTotalReport, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("total");
                    if (success.equals("1")) {
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String jumlahInvent, jumlahOperator, jumlahPegawai, barangPinjam, barangKembali, barangTerlambat;
                            jumlahInvent = object.getString("JumlahInvent_");
                            jumlahOperator = object.getString("JumlahOperator_");
                            jumlahPegawai = object.getString("JumlahPegawai_");
                            barangPinjam = object.getString("PinjamBarang_");
                            barangKembali = object.getString("PinjamKembali_");
                            barangTerlambat = object.getString("PinjamTerlambat_");

                            dialogTotal(jumlahInvent, jumlahOperator, jumlahPegawai, barangPinjam, barangKembali, barangTerlambat);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        requestQueue.add(stringRequest);
    }

    private void dialogDate() {
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);

                String tgl = simpleDateFormat.format(calendar.getTime());
                Toast.makeText(context, tgl, Toast.LENGTH_SHORT).show();
                filterReport(tgl);
                txtSelectDate.setText(tgl);

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getListReport() {
        list_report.clear();
        adapterReport.notifyDataSetChanged();
        swipeListReport.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getReport, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListReport listData = new ListReport(
                                object.getString("IDInvent_"),
                                object.getString("IDPeminjaman_"),
                                object.getString("Jumlah_"),
                                object.getString("TglPinjaman_"),
                                object.getString("TglKembali_"),
                                object.getString("StatusPinjaman_"),
                                object.getString("NamaInvent_"),
                                object.getString("KetInvent_")
                        );
                        list_report.add(listData);
                    }
                    rvListReport.setAdapter(adapterReport);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterReport.notifyDataSetChanged();
                swipeListReport.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
    }

    private void filterReport(String tgl) {
        list_report.clear();
        adapterReport.notifyDataSetChanged();
        swipeListReport.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getFilterReport(tgl), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListReport listData = new ListReport(
                                object.getString("IDInvent_"),
                                object.getString("IDPeminjaman_"),
                                object.getString("Jumlah_"),
                                object.getString("TglPinjam_"),
                                object.getString("TglKembali_"),
                                object.getString("StatusPinjaman_"),
                                object.getString("NamaInvent_"),
                                object.getString("KetInvent_")
                        );
                        list_report.add(listData);
                    }
                    rvListReport.setAdapter(adapterReport);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterReport.notifyDataSetChanged();
                swipeListReport.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
    }

    private void sampleDate() {}
}
