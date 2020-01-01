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

public class InsertCustomerActivity extends AppCompatActivity {
    String urlInsert = "http://minhtoi96.me/order/customer/Insert.php";

    @Bind(R.id.tv_title_ql)
    TextView tvTitle;
    @Bind(R.id.imgBack)
    ImageView imgBack;

    @Bind(R.id.edt_insert_name_customer)
    EditText edtName;
    @Bind(R.id.edt_insert_code)
    EditText edtCode;
    @Bind(R.id.edt_insert_brithday)
    EditText edtBrithday;
    @Bind(R.id.edt_insert_phone)
    EditText edtPhone;
    @Bind(R.id.edt_insert_email_customer)
    EditText edtEmail;
    @Bind(R.id.edt_insert_score)
    EditText edtScore;
    @Bind(R.id.edt_insert_price_customer)
    EditText edtPrice;

    @Bind(R.id.spn_sex)
    Spinner spinnerSex;
    @Bind(R.id.spn_level)
    Spinner spinnerLevel;

    @Bind(R.id.btn_insert_customer)
    Button btnInsert;
    @Bind(R.id.btn_huy_customer)
    Button btnHuy;
    String id_user, sex, level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer);
        ButterKnife.bind(this);
        tvTitle.setText("Thêm Account");

        String [] sex = {"Chọn giới tính","Nam", "Nữ"};
        ArrayAdapter<String> adapterSex = new ArrayAdapter<String>(this, R.layout.spinner_update, R.id.textSpin, sex);
        spinnerSex.setAdapter(adapterSex);

        String [] level = {"Chọn loại khách hàng","Thường", "Bạc", "Vàng"};
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<String>(this, R.layout.spinner_update, R.id.textSpin, level);
        spinnerLevel.setAdapter(adapterLevel);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertCustomerActivity.this, CustomerActivity.class);
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
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sex = (String) spinnerSex.getSelectedItem();
                final String level = (String) spinnerSex.getSelectedItem();

                if(edtName.getText().toString().trim().equals("")
                        ||edtCode.getText().toString().trim().equals("")
                        ||edtScore.getText().toString().trim().equals("")
                        ||edtPrice.getText().toString().trim().equals("")
                        ||edtPhone.getText().toString().trim().equals("")
                        ){
                    Toast.makeText(InsertCustomerActivity.this, "Vui lòng nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                }
                else if(sex.equals("Chọn giới tính")){
                    Toast.makeText(InsertCustomerActivity.this, "Vui lòng chọn giới tính !", Toast.LENGTH_SHORT).show();
                }else if( Integer.valueOf(edtPrice.getText().toString())>100){
                    Toast.makeText(InsertCustomerActivity.this, "Không thể giảm giá hơn 100% !", Toast.LENGTH_SHORT).show();
                }
                else if(level.equals("Chọn loại khách hàng")){
                    Toast.makeText(InsertCustomerActivity.this, "Vui lòng chọn loại khách hàng !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Insert(urlInsert);
                    Intent intent = new Intent(InsertCustomerActivity.this, CustomerActivity.class);
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
    private void Insert(String url){

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

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            id_user = sharedPreferences.getString("id", "90");
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(InsertCustomerActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(InsertCustomerActivity.this, "Thêm không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InsertCustomerActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID_USER", id_user);
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