package com.example.administrator.ordernow;

import android.app.Activity;
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
    String urlUpdate = "http://minhtoi96.me/quanlyaccount/update.php";
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
    @Bind(R.id.edt_ten)
    EditText edtTen;
    @Bind(R.id.edt_ngaysinh)
    EditText edtNgaySinh;
    @Bind(R.id.edt_sdt)
    EditText edtSdt;
    @Bind(R.id.edt_email)
    EditText edtEmail;
    @Bind(R.id.edt_cmnd)
    EditText edtCmnd;
    @Bind(R.id.edt_dia_chi)
    EditText edtDiaChi;
    @Bind(R.id.edt_quyen)
    TextView edtQuyen;
    @Bind(R.id.edt_gt)
    TextView edtGioiTinh;
    @Bind(R.id.edt_nghe)
    TextView edtNghe;

    @Bind(R.id.dashboard_drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.info_fragment)
    View viewNavigation;

    @Bind(R.id.spn_gioitinh)
    Spinner spinnerGT;
    @Bind(R.id.spn_chuc_vu)
    Spinner spinnerNN;

    boolean t;
    String id, ac, pass, ten, ngaysinh, cmnd, diachi, gt, sdt, nghe, email,quyen;
    ArrayList<NguoiDung> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        t = false;
        String [] gioitinh = {"Chọn giới tính","Nam", "Nữ"};
        String [] chucvu = {"Chọn nghề nghiệp","Sinh viên","Bác sĩ","Kỹ sư","Lập trình viên","Giáo viên","Nhân vên văn phòng","Cảnh sát giao thông", "Khác"};
        ArrayAdapter<String> adapterGT = new ArrayAdapter<String>(this, R.layout.spinner_info, R.id.textSpin, gioitinh);
        ArrayAdapter<String> adapterCV = new ArrayAdapter<String>(this, R.layout.spinner_info, R.id.textSpin, chucvu);
        spinnerGT.setAdapter(adapterGT);
        spinnerNN.setAdapter(adapterCV);
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
            ac = sharedPreferences.getString("ac", "90");
            pass = sharedPreferences.getString("pass", "90");
            ten = sharedPreferences.getString("ten", "90");
            ngaysinh = sharedPreferences.getString("ngaysinh", "90");
            cmnd = sharedPreferences.getString("cmnd", "90");
            diachi = sharedPreferences.getString("diachi", "90");
            gt = sharedPreferences.getString("gioitinh", "90");
            sdt = sharedPreferences.getString("sdt", "90");
            nghe = sharedPreferences.getString("nghe", "90");
            email = sharedPreferences.getString("email", "90");
            quyen = sharedPreferences.getString("quyen", "90");

            edtUser.setText(ac);
            edtPass.setText(pass);
            edtTen.setText(ten);
            edtNgaySinh.setText(ngaysinh);
            edtCmnd.setText(cmnd);
            edtDiaChi.setText(diachi);
            edtSdt.setText(sdt);
            edtEmail.setText(email);
            edtGioiTinh.setText(gt);
            edtNghe.setText(nghe);

            if(quyen.equals("2")){
                edtQuyen.setText("Xem, thêm, sửa, xóa");
            }else if(quyen.equals("3")){
                edtQuyen.setText("Chỉ xem");
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
           edtCmnd.setFocusable(false);
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
                if (edtQuyen.equals("ADMIN")) {
                    edtNghe.setVisibility(View.VISIBLE);
                }else {
                    edtNghe.setVisibility(View.GONE);
                }
                edtGioiTinh.setVisibility(View.GONE);
                edtTen.setFocusableInTouchMode(true);
                edtPass.setFocusableInTouchMode(true);
                edtNgaySinh.setFocusableInTouchMode(true);
                edtCmnd.setFocusableInTouchMode(true);
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
                final String nghe = (String) spinnerNN.getSelectedItem();
                if (edtPass.getText().toString().trim().equals("")
                        ||edtTen.getText().toString().trim().equals("")
                        ||edtNgaySinh.getText().toString().trim().equals("")
                        ||edtCmnd.getText().toString().trim().equals("")
                        ||edtDiaChi.getText().toString().trim().equals("")
                        ||edtEmail.getText().toString().trim().equals("")
                        ||edtSdt.getText().toString().trim().equals("")
                        ||edtGioiTinh.getText().toString().trim().equals("")
                        ||edtNghe.getText().toString().trim().equals("")
                        ) {
                    Toast.makeText(MainActivity.this, "Vui lòng không bỏ trống thông tin!", Toast.LENGTH_SHORT).show();
                }else if(gt.equals("Chọn giới tính")){
                    Toast.makeText(MainActivity.this, "Vui lòng chọn giới tính!", Toast.LENGTH_SHORT).show();
                }else if(nghe.equals("Chọn nghề nghiệp")){
                    Toast.makeText(MainActivity.this, "Vui lòng chọn nghề nghiệp!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Update(urlUpdate);
                    edtTen.setFocusable(false);
                    edtPass.setFocusable(false);
                    edtNgaySinh.setFocusable(false);
                    edtCmnd.setFocusable(false);
                    edtDiaChi.setFocusable(false);
                    edtSdt.setFocusable(false);
                    edtEmail.setFocusable(false);
                    edtGioiTinh.setText(gt);
                    edtNghe.setText(nghe);
                    if(nghe.equals("Cảnh sát giao thông")){
                        quyen = "2";
                    }else {
                        quyen = "3";
                    }
                    SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("pass", edtPass.getText().toString().trim());
                    editor.putString("ten", edtTen.getText().toString().trim());
                    editor.putString("ngaysinh", edtNgaySinh.getText().toString().trim());
                    editor.putString("cmnd", edtCmnd.getText().toString().trim());
                    editor.putString("diachi", edtCmnd.getText().toString().trim());
                    editor.putString("gioitinh", gt);
                    editor.putString("sdt", edtSdt.getText().toString().trim());
                    editor.putString("nghe", nghe);
                    editor.putString("email", edtEmail.getText().toString().trim());
                    editor.putString("quyen", quyen);
                    editor.apply();
                    
                    if(quyen.equals("2")){
                        edtQuyen.setText("Xem, thêm, sửa, xóa");
                    }else if(quyen.equals("3")){
                        edtQuyen.setText("Chỉ xem");
                    }else {
                        edtQuyen.setText("ADMIN");
                    }
                    edtNghe.setVisibility(View.VISIBLE);
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
        final String nghe = (String) spinnerNN.getSelectedItem();
        if(nghe.equals("Cảnh sát giao thông")){
            quyen = "2";
        }else {
            quyen = "3";
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "cập nhật thất bại", Toast.LENGTH_SHORT).show();
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
                params.put("TAIKHOAN", edtUser.getText().toString().trim());
                params.put("MATKHAU", edtPass.getText().toString().trim());
                params.put("TEN", edtTen.getText().toString().trim());
                params.put("NGAYSINH", edtNgaySinh.getText().toString().trim());
                params.put("DIACHI", edtDiaChi.getText().toString().trim());
                params.put("CMND", edtCmnd.getText().toString().trim());
                params.put("SDT", edtSdt.getText().toString().trim());
                params.put("GIOITINH", gt);
                params.put("NGHENGHIEP", nghe);
                params.put("EMAIL", edtEmail.getText().toString().trim());
                params.put("QUYEN", quyen);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

}
