package com.example.simayodika.inventaris.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.example.simayodika.inventaris.R;
import com.example.simayodika.inventaris.modals.ListReport;

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.ViewHolder> {

    private List<ListReport> list_report;

    public AdapterReport(List<ListReport> list_report) {
        super();
        this.list_report = list_report;
    }

    @NonNull
    @Override
    public AdapterReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_report, viewGroup, false);
        return new AdapterReport.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReport.ViewHolder viewHolder, int i) {
        ListReport listData = list_report.get(i);

        final String tgl = listData.getTglPinjaman();
        final String nama = listData.getNamaInvent();
        final String jumlah = listData.getJumlah();
        final String status = listData.getStatusPinjaman();

        viewHolder.txtTglPinjam.setText(tgl);
        viewHolder.txtNamaInvent.setText(nama);
        viewHolder.txtJumlahInvent.setText(jumlah + " Buah");
    }

    @Override
    public int getItemCount() {
        return list_report.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTglPinjam, txtNamaInvent, txtJumlahInvent;
        private ImageView imgInfoReport;
        private LinearLayout llReport;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTglPinjam = itemView.findViewById(R.id.txtTglPinjam);
            txtNamaInvent = itemView.findViewById(R.id.txtNamaInvent);
            txtJumlahInvent = itemView.findViewById(R.id.txtJumlahInvent);
            imgInfoReport = itemView.findViewById(R.id.imgInfoReport);
            llReport = itemView.findViewById(R.id.llReport);
        }
    }
}
