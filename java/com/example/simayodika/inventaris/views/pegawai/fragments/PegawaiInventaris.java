package com.example.simayodika.inventaris.views.pegawai.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import butterknife.Unbinder;

public class PegawaiInventaris extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener{

    @BindView(R.id.svInvent)
    SearchView svInvent;
    @BindView(R.id.rvListInvent)
    RecyclerView rvListInvent;
    @BindView(R.id.swpListInvent)
    SwipeRefreshLayout swpListInvent;
    Unbinder unbinder;

    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    Context context;

    AdapterListInvent adapterInvent;
    List<ListInvent> list_invent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pegawai_inventaris, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = this.getActivity();
        assert context != null;
        requestQueue = Volley.newRequestQueue(context);
        sharedPreferences = context.getSharedPreferences(Util.SHARED_NAME, Context.MODE_PRIVATE);

        swpListInvent.setOnRefreshListener(this);
        svInvent.setOnQueryTextListener(this);

        rvListInvent.setLayoutManager(new LinearLayoutManager(context));
        list_invent = new ArrayList<>();
        adapterInvent = new AdapterListInvent(list_invent);

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

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        list_invent.clear();
        adapterInvent.notifyDataSetChanged();
        getListInvent();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchInvent(query);
        return false;    }

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
        return false;    }

    private void getListInvent() {
        list_invent.clear();
        adapterInvent.notifyDataSetChanged();
        swpListInvent.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getInvent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i=0; i<jsonArray.length(); i++) {
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
                    rvListInvent.setAdapter(adapterInvent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterInvent.notifyDataSetChanged();
                swpListInvent.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void searchInvent(String query) {
        list_invent.clear();
        adapterInvent.notifyDataSetChanged();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getSearchInvent(query), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i=0; i<jsonArray.length(); i++) {
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
                    rvListInvent.setAdapter(adapterInvent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterInvent.notifyDataSetChanged();
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
