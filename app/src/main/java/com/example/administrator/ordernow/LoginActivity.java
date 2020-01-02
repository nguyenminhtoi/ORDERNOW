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

public class LoginActivity extends AppCompatActivity {
    String urlGetData = "http://minhtoi96.me/order/user/admin.php";
    String urlInsert = "http://minhtoi96.me/order/user/Insert.php";
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
    @Bind(R.id.edt_name_store)
    EditText edtNameStore;
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
    String pass, role, id_created, sex;

    ArrayList<User> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences= LoginActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            String cbx = sharedPreferences.getString("cbx", "0");
            if (cbx.equals("1")){
                String account = sharedPreferences.getString("user", "90");
                String pass = sharedPreferences.getString("pass", "90");
                edtUser.setText(account);
                edtPass.setText(pass);
                checkBox.setChecked(true);
            }
        }
        edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edtRepPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        arrayList = new ArrayList<>();
        String [] gioitinh = {"Chọn giới tính","Nam", "Nữ"};
        ArrayAdapter<String> adapterGT = new ArrayAdapter<String>(this, R.layout.spinner_update, R.id.textSpin, gioitinh);
        spinnerGT.setAdapter(adapterGT);
        getData(urlGetData);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(urlGetData);
                for (int i = 0; i < arrayList.size(); i++) {
                    final User user = arrayList.get(i);
                    if (edtUser.getText().toString().equals(user.getUSER())) {
                        pass = user.getPASSWORD();
                        if (edtPass.getText().toString().equals(pass)) {
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("log", "1");

                            editor.putString("user", user.getUSER());
                            editor.putString("pass", user.getPASSWORD());
                            editor.putString("fullname", user.getFULLNAME());
                            editor.putString("name_store", user.getNAME_STORE());
                            editor.putString("birthday", user.getBIRTHDAY());
                            editor.putString("address", user.getADDRESS());
                            editor.putString("sex", String.valueOf(user.getSEX()));
                            editor.putString("phone", String.valueOf(user.getPHONE()));
                            editor.putString("email", user.getEMAIL());
                            editor.putString("role", String.valueOf(user.getROLE()));
                            if(String.valueOf(user.getID_CREATED()).equals("1")){
                                editor.putString("id", String.valueOf(user.getID()));
                                editor.putString("iduser", String.valueOf(user.getID()));
                            }else {
                                editor.putString("iduser", String.valueOf(user.getID()));
                                editor.putString("id", String.valueOf(user.getID_CREATED()));
                            }

                            if (checkBox.isChecked()) {
                                editor.putString("cbx", "1");
                            } else {
                                editor.putString("cbx", "0");
                            }
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        } else if (edtPass.getText().toString().equals("")) {
                            edtPass.setError("Bạn chưa nhập mật khẩu");
                        } else {
                            edtPass.setError("Sai mật khẩu");
                        }
                    } else if (edtUser.getText().toString().equals("")) {
                        edtUser.setError("Bạn chưa nhập tài khoản");
                    } else {
                        edtUser.setError("Tài khoản không tồn tại");
                    }
                }
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
                final String gt = (String) spinnerGT.getSelectedItem();
                if (!edtRepPass.getText().toString().trim().equals(edtPass.getText().toString().trim())) {
                    Toast.makeText(LoginActivity.this, "Nhập lại mật khẩu không khóp", Toast.LENGTH_SHORT).show();
                }else if (edtUser.getText().toString().trim().equals("")
                        ||edtPass.getText().toString().trim().equals("")
                        ||edtRepPass.getText().toString().trim().equals("")
                        ||edtTen.getText().toString().trim().equals("")
                        ||edtNgaySinh.getText().toString().trim().equals("")
                        ||edtNameStore.getText().toString().trim().equals("")
                        ||edtDiaChi.getText().toString().trim().equals("")
                        ||edtEmail.getText().toString().trim().equals("")
                        ||edtSDT.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else if(gt.equals("Chọn giới tính")){
                    Toast.makeText(LoginActivity.this, "Vui lòng chọn giới tính!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Them(urlInsert);
                    getData(urlGetData);
                }
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
    private void Them(String url){

        final String user = edtUser.getText().toString().trim();
        final String pass = edtPass.getText().toString().trim();
        final String ten = edtTen.getText().toString().trim();
        final String ngaysinh = edtNgaySinh.getText().toString().trim();
        final String diachi = edtDiaChi.getText().toString().trim();
        final String nameStore = edtNameStore.getText().toString().trim();
        final String sdt = edtSDT.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String gt = (String) spinnerGT.getSelectedItem();
        if(gt.equals("Nam")){
            sex = "1";
        }else {
            sex = "0";
        }
        role = "1";
        id_created = "1";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(LoginActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            lnLogin.setVisibility(View.VISIBLE);
                            lnSign.setVisibility(View.GONE);
                            btnReturnLogin.setVisibility(View.GONE);
                            btnCreate.setVisibility(View.GONE);
                        }else {
                            Toast.makeText(LoginActivity.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
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
                params.put("NAME_STORE", nameStore);
                params.put("PHONE", sdt);
                params.put("SEX", sex);
                params.put("EMAIL", email);
                params.put("ROLE", role);
                params.put("ID_CREATED", id_created);
                return params;
            }
        };
        requestQueue.add(stringRequest);
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
                                arrayList.add(new User(
                                        object.getInt("ID"),
                                        object.getString("USER"),
                                        object.getString("PASSWORD"),
                                        object.getString("FULLNAME"),
                                        object.getString("NAME_STORE"),
                                        object.getString("BIRTHDAY"),
                                        object.getString("ADDRESS"),
                                        object.getInt("SEX"),
                                        object.getInt("PHONE"),
                                        object.getString("EMAIL"),
                                        object.getInt("ROLE"),
                                        object.getInt("ID_CREATED")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        Toast.makeText(LoginActivity.this, "Lỗi kết nối sever!" + error, Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

}
