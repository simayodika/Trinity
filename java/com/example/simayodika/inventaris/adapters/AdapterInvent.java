package com.example.simayodika.inventaris.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.simayodika.inventaris.R;
import com.example.simayodika.inventaris.modals.ListInvent;
import com.example.simayodika.inventaris.views.admin.DetailInventarisActivity;

public class AdapterInvent extends RecyclerView.Adapter<AdapterInvent.ViewHolder> {

    private List<ListInvent> list_invent;
    private Context context;

    public AdapterInvent(List<ListInvent> list_invent) {
        super();
        this.list_invent = list_invent;
    }

    @NonNull
    @Override
    public AdapterInvent.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_invent, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterInvent.ViewHolder viewHolder, final int i) {
        ListInvent listData = list_invent.get(i);

        final String id = listData.getIdInvent();
        final String nama = listData.getNamaInvent();
        final String jumlah = listData.getJumlah();
        final String ruangID = listData.getIdRuang();
        final String jenisID = listData.getIdJenis();
        final String kondisiID = listData.getKondisiId();
        final String ketID = listData.getKetId();

        viewHolder.txtNamaInvent.setText(nama);
        switch (kondisiID) {
            case "1" :
                viewHolder.txtKondisiInvent.setText(R.string.kondisi1);
                break;
            case "2" :
                viewHolder.txtKondisiInvent.setText(R.string.kondisi2);
                break;
            case "3" :
                viewHolder.txtKondisiInvent.setText(R.string.kondisi3);
                break;
        }
        switch (ketID) {
            case "1" :
                viewHolder.txtKetInvent.setText(R.string.ket1);
                break;
            case "2" :
                viewHolder.txtKetInvent.setText(R.string.ket2);
                break;
        }

        viewHolder.imgInfoInvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kondisi = viewHolder.txtKondisiInvent.getText().toString();
                String ket = viewHolder.txtKetInvent.getText().toString();
                Intent intent = new Intent(v.getContext(), DetailInventarisActivity.class);
                intent.putExtra("idinvent", id);
                intent.putExtra("nama", nama);
                intent.putExtra("kondisi", kondisiID);
                intent.putExtra("ket", ketID);
                intent.putExtra("jumlah", jumlah);
                intent.putExtra("jenis", jenisID);
                intent.putExtra("ruang", ruangID);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_invent.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNamaInvent, txtKondisiInvent, txtKetInvent;
        private ImageView imgInfoInvent;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaInvent = itemView.findViewById(R.id.txtNamaInvent);
            txtKondisiInvent = itemView.findViewById(R.id.txtKondisiInvent);
            txtKetInvent = itemView.findViewById(R.id.txtKetInvent);
            imgInfoInvent = itemView.findViewById(R.id.imgInfoInvent);
        }
    }
}
