package com.example.simayodika.inventaris.views.admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.simayodika.inventaris.R;
import com.example.simayodika.inventaris.views.admin.fragments.AdminInventaris;
import com.example.simayodika.inventaris.views.admin.fragments.AdminProses;
import com.example.simayodika.inventaris.views.admin.fragments.AdminReport;

public class AdminActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        loadFragment(new AdminInventaris());
        BottomNavigationView navigationView = findViewById(R.id.navigationAdmin);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flAdmin, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_invent :
                fragment = new AdminInventaris();
                break;
            case R.id.navigation_proses :
                fragment = new AdminProses();
                break;
            case R.id.navigation_report :
                fragment = new AdminReport();
                break;
        }
        return loadFragment(fragment);
    }
}
