package com.example.administrator.ordernow;

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
import android.app.AlertDialog;

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

public class TableActivity extends AppCompatActivity {
    String urlGetData = "http://minhtoi96.me/order/list_table/table.php";
    String urlDelete = "http://minhtoi96.me/order/list_table/delete.php";
    String urlInsert = "http://minhtoi96.me/order/list_table/Insert.php";
    String urlUpdate = "http://minhtoi96.me/order/list_table/update.php";

    String urlInsertBill = "http://minhtoi96.me/order/bill/Insert.php";
    String urlDeleteBill = "http://minhtoi96.me/order/bill/delete.php";
    String urlUpdateBill = "http://minhtoi96.me/order/bill/updateBill.php";

    @Bind(R.id.tv_title_ql)
    TextView tvTitle;
    @Bind(R.id.imgBack)
    ImageView imgBack;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.lvTable)
    ListView list;
    ArrayList<Table> arrayList;
    TableAdapter adapter;
    String id, id_created;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ButterKnife.bind(this);
        tvTitle.setText("Quản lý bàn");
        arrayList = new ArrayList<>();
        adapter = new TableAdapter(TableActivity.this, R.layout.list_table, arrayList);
        list.setAdapter(adapter);
        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            id = sharedPreferences.getString("id", "90");
            id_created = sharedPreferences.getString("iduser", "90");
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
                                arrayList.add(new Table(
                                        object.getInt("ID"),
                                        object.getInt("ID_USER"),
                                        object.getString("NAME_TABLE")
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
                        Toast.makeText(TableActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
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
    public void Delete(final String name, final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(TableActivity.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
                            DeleteBill(name);
                            Toast.makeText(TableActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }else {
                            Toast.makeText(TableActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TableActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
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
    private void Insert(final String edit){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsert,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(TableActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            GetData( Integer.valueOf(id));
                            InsertBill(edit);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(TableActivity.this, "Thêm không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TableActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID_USER", id);
                params.put("NAME_TABLE", edit);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void Update(final String edit, final String i){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(TableActivity.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(TableActivity.this, "Sửa không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TableActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", i);
                params.put("NAME_TABLE", edit);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void showInsertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_insert_table, null);
        dialogBuilder.setView(dialogView);

        final EditText edtInsert = (EditText) dialogView.findViewById(R.id.edit_insert);

        dialogBuilder.setTitle("THÊM BÀN");
        dialogBuilder.setMessage("Nhập tên bàn");
        dialogBuilder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Toast.makeText(TableActivity.this, edt.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                Insert(edtInsert.getText().toString().trim());
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
    public void showUpdateDialog(final String i,final String name) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_update_table, null);
        dialogBuilder.setView(dialogView);

        final EditText edtUpdate = (EditText) dialogView.findViewById(R.id.edit_update);
        edtUpdate.setText(name);
        dialogBuilder.setTitle("SỬA TÊN BÀN");
        dialogBuilder.setMessage("Nhập tên bàn mới");
        dialogBuilder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Toast.makeText(TableActivity.this, edt.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                UpdateBill(name, edtUpdate.getText().toString().trim());
                Update(edtUpdate.getText().toString().trim(), i);

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

    private void InsertBill(final String table){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertBill,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                        }else {
                            Toast.makeText(TableActivity.this, "Thêm không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TableActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID_USER", id);
                params.put("NAME_BILL", "no");
                params.put("ID_TABLE", table);
                params.put("ID_FOOD", "0");
                params.put("ID_VOUCHER", "0");
                params.put("ID_CUSTOMER", "0");
                params.put("ID_CREATED", id_created);
                params.put("NOTE", "xxx");
                params.put("TOTAL_PRICE", "0");
                params.put("STATUS", "1");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void UpdateBill(final String table, final String nametable ){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateBill,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                        }else {
                            Toast.makeText(TableActivity.this, "Sửa bill không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TableActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("NAME_TABLE", table);
                params.put("ID_TABLE",nametable);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void DeleteBill(final String name){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteBill,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){

                        }else {
                            Toast.makeText(TableActivity.this, "Xóa bill không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TableActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", name);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }
}
