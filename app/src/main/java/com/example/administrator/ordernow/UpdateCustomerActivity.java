package com.example.administrator.ordernow;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UpdateCustomerActivity extends AppCompatActivity {
    String urlUpdate = "http://minhtoi96.me/order/customer/update.php";

    @Bind(R.id.tv_title_ql)
    TextView tvTitle;
    @Bind(R.id.imgBack)
    ImageView imgBack;

    @Bind(R.id.edt_update_name_customer)
    EditText edtName;
    @Bind(R.id.edt_update_code)
    EditText edtCode;
    @Bind(R.id.edt_update_brithday)
    EditText edtBrithday;
    @Bind(R.id.edt_update_phone_customer)
    EditText edtPhone;
    @Bind(R.id.edt_update_email_customer)
    EditText edtEmail;
    @Bind(R.id.edt_update_score)
    EditText edtScore;
    @Bind(R.id.edt_update_price_customer)
    EditText edtPrice;

    @Bind(R.id.spn_sex)
    Spinner spinnerSex;
    @Bind(R.id.spn_level)
    Spinner spinnerLevel;

    @Bind(R.id.btn_update_customer)
    Button btnupdate;
    @Bind(R.id.btn_huy_customer)
    Button btnHuy;
    String id, sex, level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);
        ButterKnife.bind(this);
        tvTitle.setText("Sửa khách hàng");

        String [] sex = {"Chọn giới tính","Nam", "Nữ"};
        ArrayAdapter<String> adapterSex = new ArrayAdapter<String>(this, R.layout.spinner_update, R.id.textSpin, sex);
        spinnerSex.setAdapter(adapterSex);

        String [] level = {"Chọn loại khách hàng","Thường", "Bạc", "Vàng"};
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<String>(this, R.layout.spinner_update, R.id.textSpin, level);
        spinnerLevel.setAdapter(adapterLevel);

        Intent intent = getIntent();
        Customer customer = (Customer) intent.getSerializableExtra("dataCustomer");
        id = String.valueOf(customer.getID());
        edtName.setText(customer.getNAME_CUSTOMER());
        edtCode.setText(customer.getCODE_CUSTOMER());
        edtBrithday.setText(customer.getBIRTHDAY());
        edtPhone.setText(String.valueOf(customer.getPHONE()));
        edtScore.setText(String.valueOf(customer.getSCORE()));
        edtEmail.setText(customer.getEMAIL());
        edtPrice.setText(String.valueOf(customer.getPRICE_SALE()));

        if(customer.getSEX()==1){
            spinnerSex.setSelection(customer.getSEX());
        }else {
            spinnerSex.setSelection(2);
        }

        if(customer.getLEVEL()==1){
            spinnerLevel.setSelection(2);
        }
        else if(customer.getSEX()==2){
            spinnerLevel.setSelection(3);
        }
        else {
            spinnerLevel.setSelection(1);
        }
        
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateCustomerActivity.this, CustomerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        edtBrithday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBrithday();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sex = (String) spinnerSex.getSelectedItem();
                final String level = (String) spinnerSex.getSelectedItem();

                if(edtName.getText().toString().trim().equals("")
                        ||edtCode.getText().toString().trim().equals("")
                        ||edtScore.getText().toString().trim().equals("")
                        ||edtPhone.getText().toString().trim().equals("")
                        ){
                    Toast.makeText(UpdateCustomerActivity.this, "Vui lòng nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                }else if(sex.equals("Chọn giới tính")){
                    Toast.makeText(UpdateCustomerActivity.this, "Vui lòng chọn giới tính !", Toast.LENGTH_SHORT).show();
                }
                else if(level.equals("Chọn loại khách hàng")){
                    Toast.makeText(UpdateCustomerActivity.this, "Vui lòng chọn loại khách hàng !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Update(urlUpdate);
                    Intent intent = new Intent(UpdateCustomerActivity.this, CustomerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
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
                edtBrithday.setText(simpleDateFormat.format(calendarBirthday.getTime()));
            }
        },year, month, day);
        datePickerDialog.show();
    }
    private void Update(String url){

        final String x = (String) spinnerSex.getSelectedItem();
        final String lv = (String) spinnerLevel.getSelectedItem();
        if(x.equals("Nam")){
            sex = "1";
        }else {
            sex = "0";
        }
        if(lv.equals("Vàng")){
            level = "2";
        }else if(lv.equals("Bạc")) {
            level = "1";
        }
        else {
            level = "0";
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(UpdateCustomerActivity.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(UpdateCustomerActivity.this, "Sửa không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateCustomerActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", id);
                params.put("NAME_CUSTOMER", edtName.getText().toString().trim());
                params.put("CODE_CUSTOMER", edtCode.getText().toString().trim());
                params.put("PHONE", edtPhone.getText().toString().trim());
                params.put("EMAIL", edtEmail.getText().toString().trim());
                params.put("BIRTHDAY", edtBrithday.getText().toString().trim());
                params.put("SEX", sex);
                params.put("SCORE", edtScore.getText().toString().trim());
                params.put("LEVEL", level);
                params.put("PRICE_SALE", edtPrice.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}