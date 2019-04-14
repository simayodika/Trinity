package com.example.simayodika.inventaris.views.pegawai;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.simayodika.inventaris.R;
import com.example.simayodika.inventaris.views.pegawai.fragments.PegawaiDashboard;
import com.example.simayodika.inventaris.views.pegawai.fragments.PegawaiHistory;
import com.example.simayodika.inventaris.views.pegawai.fragments.PegawaiInventaris;

public class PegawaiActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai);

        loadFragment(new PegawaiInventaris());
        BottomNavigationView navigationView = findViewById(R.id.navigationPegawai);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flPegawai, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_invent :
                fragment = new PegawaiInventaris();
                break;
            case R.id.navigation_history :
                fragment = new PegawaiHistory();
                break;
            case R.id.navigation_dashboard :
                fragment = new PegawaiDashboard();
                break;
        }
        return loadFragment(fragment);
    }
}
