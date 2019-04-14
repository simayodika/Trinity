package com.example.simayodika.inventaris.views.peminjaman;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

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
import com.example.simayodika.inventaris.adapters.AdapterListInvent;
import com.example.simayodika.inventaris.helpers.Api;
import com.example.simayodika.inventaris.helpers.Util;
import com.example.simayodika.inventaris.modals.ListInvent;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PeminjamanActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;

    AdapterListInvent adapterListInvent;
    List<ListInvent> list_invent;
    @BindView(R.id.svInvent)
    SearchView svInvent;
    @BindView(R.id.rvListInvent)
    RecyclerView rvListInvent;
    @BindView(R.id.swpListInvent)
    SwipeRefreshLayout swpListInvent;

    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);
        ButterKnife.bind(this);

        sharedPreferences = this.getSharedPreferences(Util.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);
        level = sharedPreferences.getString(Util.sharedLevel, "");

        rvListInvent.setLayoutManager(new LinearLayoutManager(this));
        list_invent = new ArrayList<>();
        adapterListInvent = new AdapterListInvent(list_invent);

        swpListInvent.setOnRefreshListener(this);
        svInvent.setOnQueryTextListener(this);

        getListInvent();

        svInvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svInvent.setIconified(false);
            }
        });
        svInvent.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                swpListInvent.setRefreshing(true);
                getListInvent();
                return false;
            }
        });
    }

    @Override
    public void onRefresh() {
        list_invent.clear();
        adapterListInvent.notifyDataSetChanged();
        getListInvent();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchInvent(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.equals("")) {
            swpListInvent.setEnabled(true);
            getListInvent();
        } else {
            swpListInvent.setRefreshing(false);
            swpListInvent.setEnabled(false);
            searchInvent(query);
        }
        return false;
    }

    private void getListInvent() {
        list_invent.clear();
        adapterListInvent.notifyDataSetChanged();
        swpListInvent.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getInvent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListInvent listData = new ListInvent(
                                object.getString("IDInvent_"),
                                object.getString("NamaInvent_"),
                                object.getString("Kondisi_"),
                                object.getString("KetInvent_"),
                                object.getString("Jumlah_"),
                                object.getString("IDJenis_"),
                                object.getString("TglRegis_"),
                                object.getString("IDRuang_"),
                                object.getString("KodeInven_"),
                                object.getString("NamaJenis_"),
                                object.getString("NamaRuang_")
                        );
                        list_invent.add(listData);
                    }
                    rvListInvent.setAdapter(adapterListInvent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterListInvent.notifyDataSetChanged();
                swpListInvent.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void searchInvent(final String query) {
        list_invent.clear();
        adapterListInvent.notifyDataSetChanged();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getSearchInvent(query), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListInvent listData = new ListInvent(
                                object.getString("IDInvent_"),
                                object.getString("NamaInvent_"),
                                object.getString("Kondisi_"),
                                object.getString("KetInvent_"),
                                object.getString("Jumlah_"),
                                object.getString("IDJenis_"),
                                object.getString("TglRegis_"),
                                object.getString("IDRuang_"),
                                object.getString("KodeInvent_"),
                                object.getString("NamaJenis_"),
                                object.getString("NamaRuang_")
                        );
                        list_invent.add(listData);
                    }
                    rvListInvent.setAdapter(adapterListInvent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterListInvent.notifyDataSetChanged();
                swpListInvent.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
