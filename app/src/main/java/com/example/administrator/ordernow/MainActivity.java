package com.example.administrator.ordernow;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    String urlGetData = "http://minhtoi96.me/quanlyaccount/admin.php";
    String urlInsert = "http://minhtoi96.me/quanlyaccount/Insert.php";
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_return_login)
    Button btnReturnLogin;
    @Bind(R.id.btn_login_create)
    Button btnLoginCreate;
    @Bind(R.id.btn_create)
    Button btnCreate;
    @Bind(R.id.edt_login_username)
    EditText edtUser;
    @Bind(R.id.edt_login_password)
    EditText edtPass;
    @Bind(R.id.edt_login_retype_password)
    EditText edtRepPass;
    @Bind(R.id.edt_login_ngay_sinh)
    EditText edtNgaySinh;
    @Bind(R.id.edt_login_ten)
    EditText edtTen;
    @Bind(R.id.edt_login_cmnd)
    EditText edtCMND;
    @Bind(R.id.edt_login_dia_chi)
    EditText edtDiaChi;
    @Bind(R.id.edt_login_sdt)
    EditText edtSDT;
    @Bind(R.id.edt_login_email)
    EditText edtEmail;
    @Bind(R.id.cbx)
    CheckBox checkBox;
    @Bind(R.id.ln_sign)
    LinearLayout lnSign;
    @Bind(R.id.ln_login)
    LinearLayout lnLogin;
    @Bind(R.id.spn_gt)
    Spinner spinnerGT;
    @Bind(R.id.spn_nghe)
    Spinner spinnerNN;
    String pass,quyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences= MainActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            String cbx = sharedPreferences.getString("cbx", "0");
            if (cbx.equals("1")){
                String account = sharedPreferences.getString("ac", "90");
                String pass = sharedPreferences.getString("pass", "90");
                edtUser.setText(account);
                edtPass.setText(pass);
                checkBox.setChecked(true);
            }
        }
        edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edtRepPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //arrayList = new ArrayList<>();
        String [] gioitinh = {"Chọn giới tính","Nam", "Nữ"};
        String [] chucvu = {"Chọn nghề nghiệp","Sinh viên","Bác sĩ","Kỹ sư","Lập trình viên","Giáo viên","Nhân vên văn phòng","Cảnh sát giao thông", "Khác"};
        ArrayAdapter<String> adapterGT = new ArrayAdapter<String>(this, R.layout.spinner_update, R.id.textSpin, gioitinh);
        ArrayAdapter<String> adapterCV = new ArrayAdapter<String>(this, R.layout.spinner_update, R.id.textSpin, chucvu);
        spinnerGT.setAdapter(adapterGT);
        spinnerNN.setAdapter(adapterCV);
        getData(urlGetData);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        btnLoginCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnLogin.setVisibility(View.GONE);
                lnSign.setVisibility(View.VISIBLE);
                btnReturnLogin.setVisibility(View.VISIBLE);
                btnCreate.setVisibility(View.VISIBLE);
                edtUser.setText("");
                edtPass.setText("");

            }
        });
        if (lnSign.getVisibility() == View.VISIBLE){
            btnReturnLogin.setVisibility(View.VISIBLE);
            edtUser.setText("");
            edtPass.setText("");
        }

        btnReturnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnLogin.setVisibility(View.VISIBLE);
                lnSign.setVisibility(View.GONE);
                btnReturnLogin.setVisibility(View.GONE);
                btnCreate.setVisibility(View.GONE);
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBrithday();
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
    private void getData(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
