package com.example.administrator.ordernow;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReportActivity extends AppCompatActivity {
    String urlGetData = "http://minhtoi96.me/order/bill/reportDay.php";

    @Bind(R.id.tv_title_ql)
    TextView tvTitle;
    @Bind(R.id.imgBack)
    ImageView imgBack;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.img_choose_day)
    ImageView imgChooseDay;
    @Bind(R.id.tv_choose_day)
    TextView tvDayChoose;

    @Bind(R.id.tv_day)
    TextView tvDay;
    @Bind(R.id.tv_yesterday)
    TextView tvYesterday;
    @Bind(R.id.tv_yesterday2)
    TextView tvYesterday2;
    @Bind(R.id.tv_yesterday3)
    TextView tvYesterday3;
    @Bind(R.id.tv_yesterday4)
    TextView tvYesterday4;
    @Bind(R.id.tv_yesterday5)
    TextView tvYesterday5;
    @Bind(R.id.tv_yesterday6)
    TextView tvYesterday6;


    @Bind(R.id.tv_price_day)
    TextView tvPriceDay;
    @Bind(R.id.tv_price_week)
    TextView tvPriceWeek;
    @Bind(R.id.tv_price_month)
    TextView tvPriceMonth;
    @Bind(R.id.tv_price_last_month)
    TextView tvPriceLastMonth;

    @Bind(R.id.tv_total1)
    TextView tvTotal1;
    @Bind(R.id.tv_total2)
    TextView tvTotal2;
    @Bind(R.id.tv_total3)
    TextView tvTotal3;

    @Bind(R.id.ln_day1)
    LinearLayout lnDay1;
    @Bind(R.id.ln_day2)
    LinearLayout lnDay2;
    @Bind(R.id.ln_day3)
    LinearLayout lnDay3;
    @Bind(R.id.ln_price)
    LinearLayout lnPrice;

    String id, role, lastMonth, today, yesterday3, yesterday4, yesterday5, yesterday6, heighPrice;
    int topMargin1, topMargin2, topMargin3, priceday, priceday1, priceday2, priceday3, priceday4, priceday5, priceday6, heightPrice, heightt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        tvTitle.setText("Báo cáo doanh thu");
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
        lnPrice.setVisibility(View.GONE);
        imgChooseDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDay();
            }
        });
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if(height<1370){
            heightPrice = 525;
            heighPrice = "20000";
        }
        else {
            heightPrice = 720;
            heighPrice = "14925";
        }

        GetDayToday();
        GetDay();
        GetToday();
        GetYesterday();
        GetYesterday2();
        GetMonth();
        GetLastMonth();
        setTop1();
    }

    private void GetDayToday(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        today = sdf.format(new Date());
    }

    Calendar calendarBirthday = Calendar.getInstance();

    private void GetDay(){
        Calendar day1 = Calendar.getInstance();
        day1.add(Calendar.DAY_OF_MONTH, -1);
        Date date1 = day1.getTime();

        Calendar day2 = Calendar.getInstance();
        day2.add(Calendar.DAY_OF_MONTH, -2);
        Date date2 = day2.getTime();

        Calendar day3 = Calendar.getInstance();
        day3.add(Calendar.DAY_OF_MONTH, -3);
        Date date3 = day3.getTime();

        Calendar day4 = Calendar.getInstance();
        day4.add(Calendar.DAY_OF_MONTH, -4);
        Date date4 = day4.getTime();

        Calendar day5 = Calendar.getInstance();
        day5.add(Calendar.DAY_OF_MONTH, -5);
        Date date5 = day5.getTime();

        Calendar day6 = Calendar.getInstance();
        day6.add(Calendar.DAY_OF_MONTH, -6);
        Date date6 = day6.getTime();

        Calendar month = Calendar.getInstance();
        month.add(Calendar.MONTH, -1);
        Date month1 = month.getTime();

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        tvYesterday.setText(sd.format(date1));
        tvYesterday2.setText(sd.format(date2));
        yesterday3 = sd.format(date3);
        yesterday4 = sd.format(date4);
        yesterday5 = sd.format(date5);
        yesterday6 = sd.format(date6);

        lastMonth = sd.format(month1);
        GetYesterday3();
        GetYesterday4();
        GetYesterday5();
        GetYesterday6();
        //Toast.makeText(ReportActivity.this, yesterday3+" "+yesterday4+" "+yesterday5+" "+yesterday6+" ", Toast.LENGTH_LONG).show();

    }
    private void setTop1(){
        LinearLayout.LayoutParams tv1 = (LinearLayout.LayoutParams) tvTotal1.getLayoutParams();
        if(topMargin1>700){
            tv1.topMargin = 0;
        }
        else {
            tv1.topMargin = heightPrice - topMargin1;
        }
        tvTotal1.setLayoutParams(tv1);

        LinearLayout.LayoutParams tv2 = (LinearLayout.LayoutParams) tvTotal2.getLayoutParams();
        if(topMargin2>700){
            tv2.topMargin = 0;
        }
        else {
            tv2.topMargin = heightPrice - topMargin2;
        }
        tvTotal2.setLayoutParams(tv2);

        LinearLayout.LayoutParams tv3 = (LinearLayout.LayoutParams) tvTotal3.getLayoutParams();
        if(topMargin3>700){
            tv3.topMargin = 0;
        }
        else {
            tv3.topMargin = heightPrice - topMargin3;
        }
        tvTotal3.setLayoutParams(tv3);
    }

    private void setDay(){
        int day = calendarBirthday.get(Calendar.DATE);
        int month = calendarBirthday.get(Calendar.MONTH);
        int year = calendarBirthday.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendarBirthday.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                tvDayChoose.setText(simpleDateFormat.format(calendarBirthday.getTime()));
                if(tvDayChoose.getText().toString().equals(today)){
                    tvDay.setText("Hôm nay:");
                    tvDayChoose.setText("Hôm nay");
                    today = DateFormat.format(calendarBirthday.getTime());
                    GetDayChosse();
                }else {
                    today = DateFormat.format(calendarBirthday.getTime());
                    tvDay.setText(tvDayChoose.getText().toString()+":");
                    GetDayChosse();
                }
            }
        },year, month, day);
        datePickerDialog.show();
    }

    private void GetDayChosse(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ReportActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        tvPriceDay.setText(response.toString()+"Đ");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("DAY", today+"%");
                params.put("ID", id);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void GetToday(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ReportActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        tvPriceDay.setText(response.toString()+"Đ");
                        tvTotal1.setText(response.toString());
                        if(response.toString().equals("")){
                            topMargin1 = 0;
                            tvPriceDay.setText("0Đ");
                            tvTotal1.setText("0");
                        }else {
                            topMargin1 = Integer.valueOf(Math.round(Float.valueOf(response.toString())/Float.valueOf(heighPrice)));
                            tvPriceDay.setText(response.toString()+"Đ");
                            tvTotal1.setText(response.toString());
                        }
                        setTop1();
                        GetWeek();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("DAY", today+"%");
                params.put("ID", id);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void GetYesterday(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tvTotal2.setText(response.toString());
                        if(response.toString().equals("")){
                            topMargin2 = 0;
                            tvTotal2.setText("0");
                        }else {
                            topMargin2 = Integer.valueOf(Math.round(Float.valueOf(response.toString())/Float.valueOf(heighPrice)));
                            tvTotal2.setText(response.toString());
                        }
                        setTop1();
                        GetWeek();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("DAY", tvYesterday.getText().toString()+"%");
                params.put("ID", id);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void GetYesterday2(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ReportActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        tvTotal3.setText(response.toString());
                        if(response.toString().equals("")){
                            topMargin3 = 0;
                            tvTotal3.setText("0");
                        }else {
                            topMargin3 = Integer.valueOf(Math.round(Float.valueOf(response.toString())/Float.valueOf(heighPrice)));
                            tvTotal3.setText(response.toString());
                        }
                        //
                        setTop1();
                        GetWeek();                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("DAY", tvYesterday2.getText().toString()+"%");
                params.put("ID", id);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void GetYesterday3(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.toString().equals("")){
                            tvYesterday3.setText("0");
                        }else {
                            tvYesterday3.setText(response.toString());
                        }
                        GetWeek();
                        //Toast.makeText(ReportActivity.this, priceday3, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("DAY", yesterday3+"%");
                params.put("ID", id);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void GetYesterday4(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().equals("")){
                            tvYesterday4.setText("0");
                        }else {
                            tvYesterday4.setText(response.toString());
                        }
                        GetWeek();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("DAY", yesterday4+"%");
                params.put("ID", id);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void GetYesterday5(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().equals("")){
                            tvYesterday5.setText("0");
                        }else {
                            tvYesterday5.setText(response.toString());
                        }
                        GetWeek();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("DAY", yesterday5+"%");
                params.put("ID", id);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void GetYesterday6(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().equals("")){
                            tvYesterday6.setText("0");
                        }else {
                            tvYesterday6.setText(response.toString());
                        }
                        GetWeek();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("DAY", yesterday6+"%");
                params.put("ID", id);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void GetMonth(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ReportActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        tvPriceMonth.setText(response.toString()+"Đ");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("DAY", today.substring(0, 8)+"%");
                params.put("ID", id);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void GetLastMonth(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ReportActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        tvPriceLastMonth.setText(response.toString()+"Đ");
                        GetWeek();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("DAY", lastMonth.substring(0, 8)+"%");
                params.put("ID", id);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void GetWeek(){
        Values.PRICEDAY = Integer.valueOf(tvTotal1.getText().toString());
        Values.PRICEDAY1 = Integer.valueOf(tvTotal2.getText().toString());
        Values.PRICEDAY2 = Integer.valueOf(tvTotal3.getText().toString());
        Values.PRICEDAY3 = Integer.valueOf(tvYesterday3.getText().toString());
        Values.PRICEDAY4 = Integer.valueOf(tvYesterday4.getText().toString());
        Values.PRICEDAY5 = Integer.valueOf(tvYesterday5.getText().toString());
        Values.PRICEDAY6 = Integer.valueOf(tvYesterday6.getText().toString());
        tvPriceWeek.setText(String.valueOf(Values.PRICEDAY+Values.PRICEDAY1+Values.PRICEDAY2+Values.PRICEDAY3+Values.PRICEDAY4+Values.PRICEDAY5+Values.PRICEDAY6)+"Đ");
        //Toast.makeText(ReportActivity.this, String.valueOf(Values.PRICEDAY3+Values.PRICEDAY4+Values.PRICEDAY5+Values.PRICEDAY6), Toast.LENGTH_LONG).show();
    }

}

