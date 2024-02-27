package com.longdy.livetime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button DOBbtn, timeBtn, exitBtn;
    TextView liveTextView;
    Calendar dobCalendar = Calendar.getInstance();
    Calendar tobCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DOBbtn = findViewById(R.id.bodBtn);
        timeBtn = findViewById(R.id.timeBtn);
        exitBtn = findViewById(R.id.exitBtn);
        liveTextView = findViewById(R.id.liveTextView);

        DOBbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(dobCalendar, true);
            }
        });

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(tobCalendar);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmExitDialog();
            }
        });
    }

    private void showDatePickerDialog(final Calendar calendar, final boolean isDOB) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        if (isDOB) {
                            calLiveDay();
                        }
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog(final Calendar calendar) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calLiveDay();
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void calLiveDay() {
        long dobMillis = dobCalendar.getTimeInMillis();
        long tobMillis = tobCalendar.getTimeInMillis();
        long liveMillis = tobMillis - dobMillis;

        long liveDays = liveMillis / (24 * 60 * 60 * 1000);
        long liveYears = liveDays / 365;
        long remainingDays = liveDays % 365;

        liveTextView.setText("Up time: " + liveYears + " years and " + remainingDays + " days");
    }

    private void confirmExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Excuse me").setIcon(android.R.drawable.ic_menu_help)
                .setCancelable(false)
                .setMessage("Are you sure you want to Exit?")
                .setPositiveButton("Yes", ((dialog, which) -> {
                    MainActivity.this.finish();
                })).setNegativeButton("No", ((dialog, which) -> {
                    dialog.dismiss();
                })).show();
    }
}
