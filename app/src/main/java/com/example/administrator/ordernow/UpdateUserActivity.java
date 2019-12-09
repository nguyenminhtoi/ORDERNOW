package com.example.administrator.ordernow;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class UpdateUserActivity extends AppCompatActivity {

    String urlUpdate = "http://minhtoi96.me/order/user/update.php";
    String urlXoa = "http://minhtoi96.me/order/user/delete.php";
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
    @Bind(R.id.edt_update_phone)
    EditText edtSDT;
    @Bind(R.id.edt_update_email)
    EditText edtEmail;
    @Bind(R.id.spn_update_gt)
    Spinner spinnerGT;

    @Bind(R.id.btn_update)
    Button btnUpdate;
    @Bind(R.id.btn_xoa)
    Button btnXoa;
    String name_store, sex, user;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        ButterKnife.bind(this);
        tvTitle.setText("Sửa thông tin Account");
        String [] gioitinh = {"Chọn giới tính","Nam", "Nữ"};
        ArrayAdapter<String> adapterGT = new ArrayAdapter<String>(this, R.layout.spinner_update, R.id.textSpin, gioitinh);
        spinnerGT.setAdapter(adapterGT);

        Intent intent = getIntent();
        ManagerUser managerUser = (ManagerUser) intent.getSerializableExtra("dataUser");
        id = managerUser.getID();
        edtUser.setText(managerUser.getUSER());
        edtPass.setText(managerUser.getPASSWORD());
        edtTen.setText(managerUser.getFULLNAME());
        edtNgaySinh.setText(managerUser.getBIRTHDAY());
        edtDiaChi.setText(managerUser.getADDRESS());
        edtEmail.setText(managerUser.getEMAIL());
        edtSDT.setText(String.valueOf(managerUser.getPHONE()));
        if(managerUser.getSEX()==1){
            spinnerGT.setSelection(managerUser.getSEX());
        }else {
            spinnerGT.setSelection(2);
        }


        name_store = managerUser.getNAME_STORE();
        user = managerUser.getUSER();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String gt = (String) spinnerGT.getSelectedItem();
                if (edtUser.getText().toString().trim().equals("")
                        ||edtPass.getText().toString().trim().equals("")
                        ||edtTen.getText().toString().trim().equals("")
                        ||edtNgaySinh.getText().toString().trim().equals("")
                        ||edtDiaChi.getText().toString().trim().equals("")
                        ||edtEmail.getText().toString().trim().equals("")
                        ||edtSDT.getText().toString().trim().equals("")
                        ) {
                    Toast.makeText(UpdateUserActivity.this, "Vui lòng không bỏ trống thông tin!", Toast.LENGTH_SHORT).show();
                }else if(gt.equals("Chọn giới tính")){
                    Toast.makeText(UpdateUserActivity.this, "Vui lòng chọn giới tính!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Update(urlUpdate);
                    Intent intent = new Intent(UpdateUserActivity.this, ManagerUserActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateUserActivity.this);
                dialog.setMessage("Bạn có muốn xóa tài khoản "+ user + " không?");
                dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Delete(id);
                    }
                });
                dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateUserActivity.this, ManagerUserActivity.class);
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
                            Toast.makeText(UpdateUserActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(UpdateUserActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateUserActivity.this, "Lỗi rồi!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(id));
                params.put("PASSWORD", edtPass.getText().toString().trim());
                params.put("FULLNAME", edtTen.getText().toString().trim());
                params.put("BIRTHDAY", edtNgaySinh.getText().toString().trim());
                params.put("ADDRESS", edtDiaChi.getText().toString().trim());
                params.put("NAME_STORE", name_store);
                params.put("PHONE", edtSDT.getText().toString().trim());
                params.put("SEX", sex);
                params.put("EMAIL", edtEmail.getText().toString().trim());
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }
    public void Delete(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlXoa,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(UpdateUserActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateUserActivity.this, ManagerUserActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(UpdateUserActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateUserActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(id));
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

}
