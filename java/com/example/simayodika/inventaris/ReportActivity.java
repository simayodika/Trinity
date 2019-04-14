package com.example.simayodika.inventaris;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//implementation 'com.borax12.materialdaterangepicker:library:1.9'


public class ReportActivity extends AppCompatActivity implements com.borax12.materialdaterangepicker.date.DatePickerDialog.OnDateSetListener{

    Button btnTanggal;
    TextView txtTanggal;
    String dFrom, dTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        btnTanggal = findViewById(R.id.btnTanggal);
        txtTanggal = findViewById(R.id.txtTanggal);

        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTanggal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar now = Calendar.getInstance();
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                ReportActivity.this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH)
                        );
                        dpd.setStartTitle("Dari");
                        dpd.setEndTitle("Sampai");
                        dpd.setMaxDate(now);
                        dpd.show(getFragmentManager(), "Datepickerdialog");
                    }
                });
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        monthOfYear += 1;
        monthOfYearEnd += 1;
        dFrom = year + "-" + monthOfYear + "-" + dayOfMonth;
        dTo = yearEnd + "-" + monthOfYearEnd + "-" + dayOfMonthEnd;

        int dayPlus = dayOfMonthEnd + 1;
        String dToSend = yearEnd + "-" + monthOfYearEnd + "-" + dayPlus;

//        getReport(dFrom, dToSend);

        Toast.makeText(this, dTo, Toast.LENGTH_SHORT).show();

        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-d", Locale.US);
            SimpleDateFormat outputFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);

            Date dateFromDate = inputFormatter.parse(dFrom);
            Date dateToDate = inputFormatter.parse(dTo);
            txtTanggal.setText(String.format("%s - %s", outputFormatter.format(dateFromDate), outputFormatter.format(dateToDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
