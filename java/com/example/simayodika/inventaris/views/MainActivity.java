package com.example.simayodika.inventaris.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.simayodika.inventaris.R;
import com.example.simayodika.inventaris.helpers.Util;
import com.example.simayodika.inventaris.views.admin.AdminActivity;
import com.example.simayodika.inventaris.views.operator.OperatorActivity;
import com.example.simayodika.inventaris.views.pegawai.PegawaiActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imgPengelola)
    ImageView imgPengelola;
    @BindView(R.id.imgPegawai)
    ImageView imgPegawai;
    @BindView(R.id.llOperator)
    LinearLayout llOperator;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sharedPreferences = this.getSharedPreferences(Util.SHARED_NAME, Context.MODE_PRIVATE);

        imgPengelola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginPengelolaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginPegawaiActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cekLogin();
    }

    private void cekLogin() {
        String level = sharedPreferences.getString(Util.sharedLevel, "");
        assert level != null;
        switch (level) {
            case "1" :
                Intent intent1 = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent1);
                finish();
                break;
            case "2" :
                Intent intent2 = new Intent(MainActivity.this, OperatorActivity.class);
                startActivity(intent2);
                finish();
                break;
            case "3" :
                Intent intent3 = new Intent(MainActivity.this, PegawaiActivity.class);
                startActivity(intent3);
                finish();
                break;
        }
    }
}
