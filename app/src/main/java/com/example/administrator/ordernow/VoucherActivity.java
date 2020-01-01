package com.example.administrator.ordernow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VoucherActivity extends AppCompatActivity {
    String urlGetData = "http://minhtoi96.me/order/voucher/voucher.php";
    String urlDelete = "http://minhtoi96.me/order/voucher/delete.php";
    String urlInsert = "http://minhtoi96.me/order/voucher/Insert.php";
    String urlUpdate = "http://minhtoi96.me/order/voucher/update.php";
    @Bind(R.id.tv_title_ql)
    TextView tvTitle;
    @Bind(R.id.imgBack)
    ImageView imgBack;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.lvVoucher)
    ListView list;
    ArrayList<Voucher> arrayList;
    VoucherAdapter adapter;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        ButterKnife.bind(this);
        tvTitle.setText("Quản lý mã giảm giá");
        arrayList = new ArrayList<>();
        adapter = new VoucherAdapter(VoucherActivity.this, R.layout.list_voucher, arrayList);
        list.setAdapter(adapter);
        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            id = sharedPreferences.getString("id", "90");
        }
        GetData( Integer.valueOf(id));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInsertDialog();
            }
        });
    }
    private void GetData(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayList.add(new Voucher(
                                        object.getInt("ID"),
                                        object.getInt("ID_USER"),
                                        object.getString("NAME_VOUCHER"),
                                        object.getString("CODE_VOUCHER"),
                                        object.getInt("PRICE_SALE")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VoucherActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(id));
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }
    public void Delete(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(VoucherActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }else {
                            Toast.makeText(VoucherActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VoucherActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
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
    private void Insert(final String name, final String code, final int price){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsert,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(VoucherActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(VoucherActivity.this, "Thêm không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VoucherActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID_USER", id);
                params.put("NAME_VOUCHER", name);
                params.put("CODE_VOUCHER", code);
                params.put("PRICE_SALE", String.valueOf(price));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void Update(final String name, final String code, final int price,final String i){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(VoucherActivity.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(VoucherActivity.this, "Sửa không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VoucherActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", i);
                params.put("ID_USER", id);
                params.put("NAME_VOUCHER", name);
                params.put("CODE_VOUCHER", code);
                params.put("PRICE_SALE", String.valueOf(price));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void showInsertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_insert_voucher, null);
        dialogBuilder.setView(dialogView);

        final EditText edtName = (EditText) dialogView.findViewById(R.id.edit_Name);
        final EditText edtCode = (EditText) dialogView.findViewById(R.id.edit_Code);
        final EditText edtPrice = (EditText) dialogView.findViewById(R.id.edit_Price);

        dialogBuilder.setTitle("THÊM VOUCHER");
        dialogBuilder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Toast.makeText(VoucherActivity.this, edt.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                if( Integer.valueOf(edtPrice.getText().toString())>100){
                    Toast.makeText(VoucherActivity.this, "Không thể giảm giá hơn 100% !", Toast.LENGTH_SHORT).show();
                }else {
                    Insert(edtName.getText().toString().trim(), edtCode.getText().toString().trim(),  Integer.valueOf(edtPrice.getText().toString()));
                }

            }
        });
        dialogBuilder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    public void showUpdateDialog(final String i, String name, String code, int price) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_update_voucher, null);
        dialogBuilder.setView(dialogView);

        final EditText edtName = (EditText) dialogView.findViewById(R.id.edit_Name_update);
        final EditText edtCode = (EditText) dialogView.findViewById(R.id.edit_Code_update);
        final EditText edtPrice = (EditText) dialogView.findViewById(R.id.edit_Price_update);

        edtName.setText(name);
        edtCode.setText(code);
        edtPrice.setText(String.valueOf(price));

        dialogBuilder.setTitle("SỬA TÊN VOUCHER");
        dialogBuilder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Toast.makeText(VoucherActivity.this, edt.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                if( Integer.valueOf(edtPrice.getText().toString())>100){
                    Toast.makeText(VoucherActivity.this, "Không thể giảm giá hơn 100% !", Toast.LENGTH_SHORT).show();
                }else {
                    Update(edtName.getText().toString().trim(), edtCode.getText().toString().trim(), Integer.valueOf(edtPrice.getText().toString()), i);
                }
            }
        });
        dialogBuilder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
