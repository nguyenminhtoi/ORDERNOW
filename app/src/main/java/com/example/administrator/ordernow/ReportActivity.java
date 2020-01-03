package com.example.administrator.ordernow;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReportActivity extends AppCompatActivity {
    String urlGetData = "http://minhtoi96.me/order/list_food/food.php";

    @Bind(R.id.tv_title_ql)
    TextView tvTitle;
    @Bind(R.id.imgBack)
    ImageView imgBack;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.img_choose_day)
    ImageView imgChooseDay;
    @Bind(R.id.tv_choose_day)
    TextView tvDay;

    @Bind(R.id.tv_price_day)
    TextView tvPriceDay;
    @Bind(R.id.tv_price_month)
    TextView tvPriceMonth;
    @Bind(R.id.tv_price_year)
    TextView tvPriceYear;

    @Bind(R.id.ln_day1)
    LinearLayout lnDay1;
    @Bind(R.id.ln_day2)
    LinearLayout lnDay2;
    @Bind(R.id.ln_day3)
    LinearLayout lnDay3;

    String id, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            id = sharedPreferences.getString("id", "90");
            role = sharedPreferences.getString("role", "90");
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgAdd.setVisibility(View.GONE);

        imgChooseDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBrithday();
                Toast.makeText(ReportActivity.this, currentDateandTime, Toast.LENGTH_SHORT).show();
            }
        });

        lnDay1.se

    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String currentDateandTime = sdf.format(new Date());

    Calendar calendarBirthday = Calendar.getInstance();
    private void setBrithday(){
        int day = calendarBirthday.get(Calendar.DATE);
        int month = calendarBirthday.get(Calendar.MONTH);
        int year = calendarBirthday.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendarBirthday.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                tvDay.setText(simpleDateFormat.format(calendarBirthday.getTime()));
            }
        },year, month, day);
        datePickerDialog.show();
    }
}
