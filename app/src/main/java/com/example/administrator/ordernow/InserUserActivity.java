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

public class InserUserActivity extends AppCompatActivity {
    String urlInsert = "http://minhtoi96.me/order/user/Insert.php";

    @Bind(R.id.tv_title_ql)
    TextView tvTitle;
    @Bind(R.id.imgBack)
    ImageView imgBack;

    @Bind(R.id.edt_update_username)
    EditText edtUser;
    @Bind(R.id.edt_update_password)
    EditText edtPass;
    @Bind(R.id.edt_update_ngay_sinh)
    EditText edtNgaySinh;
    @Bind(R.id.edt_update_ten)
    EditText edtTen;
    @Bind(R.id.edt_update_dia_chi)
    EditText edtDiaChi;
    @Bind(R.id.edt_update_sdt)
    EditText edtSDT;
    @Bind(R.id.edt_update_email)
    EditText edtEmail;

    @Bind(R.id.spn_gt)
    Spinner spinnerGT;

    @Bind(R.id.btn_insert)
    Button btnInsert;
    @Bind(R.id.btn_huy)
    Button btnHuy;
    String role, id_created, sex, nameStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_user);
        ButterKnife.bind(this);
        tvTitle.setText("Thêm Account");
        String [] gioitinh = {"Chọn giới tính","Nam", "Nữ"};
        ArrayAdapter<String> adapterGT = new ArrayAdapter<String>(this, R.layout.spinner_update, R.id.textSpin, gioitinh);
        spinnerGT.setAdapter(adapterGT);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InserUserActivity.this, ManagerUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBrithday();
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String gt = (String) spinnerGT.getSelectedItem();
                if(edtUser.getText().toString().trim().equals("")
                        ||edtPass.getText().toString().trim().equals("")
                        ||edtTen.getText().toString().trim().equals("")
                        ||edtNgaySinh.getText().toString().trim().equals("")
                        ||edtDiaChi.getText().toString().trim().equals("")
                        ||edtEmail.getText().toString().trim().equals("")
                        ||edtSDT.getText().toString().trim().equals("")
                        ){
                    Toast.makeText(InserUserActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else if(gt.equals("Chọn giới tính")){
                    Toast.makeText(InserUserActivity.this, "Vui lòng chọn giới tính!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Them(urlInsert);
                    Intent intent = new Intent(InserUserActivity.this, ManagerUserActivity.class);
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
                edtNgaySinh.setText(simpleDateFormat.format(calendarBirthday.getTime()));
            }
        },year, month, day);
        datePickerDialog.show();
    }
    private void Them(String url){

        final String user = edtUser.getText().toString().trim();
        final String pass = edtPass.getText().toString().trim();
        final String ten = edtTen.getText().toString().trim();
        final String ngaysinh = edtNgaySinh.getText().toString().trim();
        final String diachi = edtDiaChi.getText().toString().trim();
        final String sdt = edtSDT.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String gt = (String) spinnerGT.getSelectedItem();
        if(gt.equals("Nam")){
            sex = "1";
        }else {
            sex = "0";
        }
        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            id_created = sharedPreferences.getString("id", "90");
            nameStore = sharedPreferences.getString("name_store", "90");
        }
        role = "2";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(InserUserActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(InserUserActivity.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InserUserActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("USER", user);
                params.put("PASSWORD", pass);
                params.put("FULLNAME", ten);
                params.put("BIRTHDAY", ngaysinh);
                params.put("ADDRESS", diachi);
                params.put("PHONE", sdt);
                params.put("SEX", sex);
                params.put("EMAIL", email);
                params.put("NAME_STORE", nameStore);
                params.put("ROLE", role);
                params.put("ID_CREATED", id_created);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
