package com.example.administrator.ordernow;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    String urlUpdate = "http://minhtoi96.me/order/user/update.php";
    @Bind(R.id.ds_ban)
    LinearLayout lnDsBan;
    @Bind(R.id.ds_mon)
    LinearLayout lnDsMon;
    @Bind(R.id.ds_khach_hang)
    LinearLayout lnDsKhach;
    @Bind(R.id.doanh_thu)
    LinearLayout lnDoanhThu;
    @Bind(R.id.quan_ly)
    LinearLayout lnQuanLy;
    @Bind(R.id.ma_giam_gia)
    LinearLayout lnMaGiamGia;
    @Bind(R.id.order)
    LinearLayout lnOrder;


    @Bind(R.id.btn_log_out)
    Button btnLogOut;
    @Bind(R.id.btn_update)
    Button btnUpdate;

    @Bind(R.id.imgInfo)
    ImageView imgInfo;
    @Bind(R.id.imgUpdate)
    ImageView imgUpdate;
    @Bind(R.id.imgShow)
    ImageView imgShow;
    @Bind(R.id.imgHide)
    ImageView imgHide;

    @Bind(R.id.edt_user)
    EditText edtUser;
    @Bind(R.id.edt_pass)
    EditText edtPass;
    @Bind(R.id.edt_fullname)
    EditText edtTen;
    @Bind(R.id.edt_ngaysinh)
    EditText edtNgaySinh;
    @Bind(R.id.edt_sdt)
    EditText edtSdt;
    @Bind(R.id.edt_email)
    EditText edtEmail;
    @Bind(R.id.edt_name_store)
    EditText edtNameStore;
    @Bind(R.id.edt_dia_chi)
    EditText edtDiaChi;
    @Bind(R.id.edt_quyen)
    TextView edtQuyen;
    @Bind(R.id.edt_gt)
    TextView edtGioiTinh;

    @Bind(R.id.dashboard_drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.info_fragment)
    View viewNavigation;

    @Bind(R.id.spn_gioitinh)
    Spinner spinnerGT;


    boolean t;
    String id, user, pass, fullname, name_store, birthday, address, sex, phone, email, role, id_created;
    ArrayList<User> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        t = false;
        String [] gioitinh = {"Chọn giới tính","Nam", "Nữ"};
        ArrayAdapter<String> adapterGT = new ArrayAdapter<String>(this, R.layout.spinner_info, R.id.textSpin, gioitinh);
        spinnerGT.setAdapter(adapterGT);
        arrayList = new ArrayList<>();
        edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        setEventClick();
        setEventInfo();
        thongTin();
    }
    private void thongTin(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            String login = sharedPreferences.getString("log", "90");
            id = sharedPreferences.getString("id", "90");
            user = sharedPreferences.getString("user", "90");
            pass = sharedPreferences.getString("pass", "90");
            fullname = sharedPreferences.getString("fullname", "90");
            name_store = sharedPreferences.getString("name_store", "90");
            birthday = sharedPreferences.getString("birthday", "90");
            address = sharedPreferences.getString("address", "90");
            sex = sharedPreferences.getString("sex", "90");
            phone = sharedPreferences.getString("phone", "90");
            email = sharedPreferences.getString("email", "90");
            role = sharedPreferences.getString("role", "90");
            id_created = sharedPreferences.getString("id_created", "90");

            edtUser.setText(user);
            edtPass.setText(pass);
            edtTen.setText(fullname);
            edtNgaySinh.setText(birthday);
            edtNameStore.setText(name_store);
            edtDiaChi.setText(address);
            edtSdt.setText(phone);
            edtEmail.setText(email);
            if(sex.equals("1")){
                edtGioiTinh.setText("Nam");
            }
            else {
                edtGioiTinh.setText("Nữ");
            }

            if(role.equals("2")){
                edtQuyen.setText("Nhân Viên");
            }else if (role.equals("1")){
                edtQuyen.setText("Quản lý");
            }else {
                edtQuyen.setText("ADMIN");
            }

            if (login.equals("1")) {
            }else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
    private void setEventInfo(){
           edtTen.setFocusable(false);
           edtPass.setFocusable(false);
           edtNgaySinh.setFocusable(false);
           edtNameStore.setFocusable(false);
           edtDiaChi.setFocusable(false);
           edtSdt.setFocusable(false);
           edtEmail.setFocusable(false);
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBrithday();
            }
        });
        imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgShow.setVisibility(View.GONE);
                imgHide.setVisibility(View.VISIBLE);
                edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        imgHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgHide.setVisibility(View.GONE);
                imgShow.setVisibility(View.VISIBLE);
                edtPass.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
    }
    private void setEventClick(){
        imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtGioiTinh.setVisibility(View.GONE);
                edtTen.setFocusableInTouchMode(true);
                edtPass.setFocusableInTouchMode(true);
                edtNgaySinh.setFocusableInTouchMode(true);
                edtNameStore.setFocusableInTouchMode(true);
                edtDiaChi.setFocusableInTouchMode(true);
                edtSdt.setFocusableInTouchMode(true);
                edtEmail.setFocusableInTouchMode(true);
            }
        });
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(viewNavigation);
            }
        });
        lnDsBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        lnDsKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lnDsMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        lnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        lnMaGiamGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        lnQuanLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(role.equals("2")){
                    Toast.makeText(MainActivity.this, "Quyền truy cập bị hạn chế", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this, ManagerUserActivity.class);
                    startActivity(intent);
                }
            }
        });
        lnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= MainActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("log", "");
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String gt = (String) spinnerGT.getSelectedItem();
                if (edtPass.getText().toString().trim().equals("")
                        ||edtTen.getText().toString().trim().equals("")
                        ||edtNgaySinh.getText().toString().trim().equals("")
                        ||edtNameStore.getText().toString().trim().equals("")
                        ||edtDiaChi.getText().toString().trim().equals("")
                        ||edtEmail.getText().toString().trim().equals("")
                        ||edtSdt.getText().toString().trim().equals("")
                        ||edtGioiTinh.getText().toString().trim().equals("")
                        ) {
                    Toast.makeText(MainActivity.this, "Vui lòng không bỏ trống thông tin!", Toast.LENGTH_SHORT).show();
                }else if(gt.equals("Chọn giới tính")){
                    Toast.makeText(MainActivity.this, "Vui lòng chọn giới tính!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Update(urlUpdate);
                    edtTen.setFocusable(false);
                    edtPass.setFocusable(false);
                    edtNgaySinh.setFocusable(false);
                    edtNameStore.setFocusable(false);
                    edtDiaChi.setFocusable(false);
                    edtSdt.setFocusable(false);
                    edtEmail.setFocusable(false);
                    edtGioiTinh.setText(gt);

                    SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("pass", edtPass.getText().toString().trim());
                    editor.putString("ten", edtTen.getText().toString().trim());
                    editor.putString("ngaysinh", edtNgaySinh.getText().toString().trim());
                    editor.putString("namestore", edtNameStore.getText().toString().trim());
                    editor.putString("diachi", edtDiaChi.getText().toString().trim());
                    editor.putString("gioitinh", gt);
                    editor.putString("sdt", edtSdt.getText().toString().trim());
                    editor.putString("email", edtEmail.getText().toString().trim());
                    editor.apply();
                    
                    if(role.equals("2")){
                        edtQuyen.setText("Chỉ xem");
                    }else {
                        edtQuyen.setText("Quản lý");
                    }
                    edtGioiTinh.setVisibility(View.VISIBLE);
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
    private void Update(String url){
        final String gt = (String) spinnerGT.getSelectedItem();
        if(gt.equals("Nam")){
            sex = "1";
        }else {
            sex = "0";
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi rồi!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", id);
                params.put("PASSWORD", edtPass.getText().toString().trim());
                params.put("FULLNAME", edtTen.getText().toString().trim());
                params.put("BIRTHDAY", edtNgaySinh.getText().toString().trim());
                params.put("ADDRESS", edtDiaChi.getText().toString().trim());
                params.put("NAME_STORE", edtNameStore.getText().toString().trim());
                params.put("PHONE", edtSdt.getText().toString().trim());
                params.put("SEX", sex);
                params.put("EMAIL", edtEmail.getText().toString().trim());
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

}
