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
import android.widget.Button;
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

public class CreateBillActivity extends AppCompatActivity {
    
    String urlGetData = "http://minhtoi96.me/order/list_food/food.php";

    String urlUpdateBill = "http://minhtoi96.me/order/bill/updateTable.php";

    @Bind(R.id.tv_title_ql)
    TextView tvTitle;
    @Bind(R.id.imgBack)
    ImageView imgBack;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.tv_price_create_bill)
    TextView tvTotal;

    @Bind(R.id.edit_note)
    EditText editNote;

    @Bind(R.id.btn_create_bill)
    Button btnBill;

    @Bind(R.id.lvFood)
    ListView list;
    ArrayList<Food> arrayList;
    CreateBillAdapter adapter;
    String id, idBill, nameBill, food, nameTable, id_created;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);
        ButterKnife.bind(this);

        arrayList = new ArrayList<>();
        adapter = new CreateBillAdapter(this, R.layout.list_food_chose, arrayList);
        list.setAdapter(adapter);
        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            id = sharedPreferences.getString("id", "90");
            id_created = sharedPreferences.getString("iduser", "90");
        }

        Values.PRICE = 0;
        Values.FOOD = "";

        Intent intent = getIntent();
        Bill1 bill1 = (Bill1) intent.getSerializableExtra("dataBill");
        idBill = String.valueOf(bill1.getID());
        nameBill =  String.valueOf(bill1.getID_TABLE()+"|"+bill1.getID());
        nameTable = String.valueOf(bill1.getID_TABLE());
        tvTitle.setText("Tạo đơn "+ String.valueOf(bill1.getID_TABLE()));
        GetData( Integer.valueOf(id));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateBillActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgAdd.setVisibility(View.GONE);
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
                Intent intent = new Intent(CreateBillActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();
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
                                arrayList.add(new Food(
                                        object.getInt("ID"),
                                        object.getInt("ID_USER"),
                                        object.getString("NAME_FOOD"),
                                        object.getString("IMAGE"),
                                        object.getInt("PRICE"),
                                        object.getInt("NUMBER"),
                                        object.getString("NOTE"),
                                        object.getString("ID_GOUP")
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
                        Toast.makeText(CreateBillActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
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

    public void Update(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateBill,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(CreateBillActivity.this, "Tạo đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CreateBillActivity.this, "Tạo đơn hàng không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateBillActivity.this, "Bạn chưa chọn món!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", idBill);
                params.put("NAME_BILL", nameBill);
                params.put("ID_CREATED", id_created);
                params.put("ID_FOOD", food);
                params.put("NOTE", editNote.getText().toString().trim());
                params.put("TOTAL_PRICE", tvTotal.getText().toString().trim());
                params.put("STATUS", "2");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void Delete(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateBill,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(CreateBillActivity.this, "Xóa đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CreateBillActivity.this, "Xóa đơn hàng không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateBillActivity.this, "Lỗi xóa đơn!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", idBill);
                params.put("NAME_BILL", nameBill);
                params.put("ID_CREATED", id_created);
                params.put("ID_FOOD", food);
                params.put("NOTE", editNote.getText().toString().trim());
                params.put("TOTAL_PRICE", tvTotal.getText().toString().trim());
                params.put("STATUS", "1");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void showPrice(int price) {
        tvTotal.setText(String.valueOf(price));
    }
    public void showFood(final String namefood) {
        food = String.valueOf(namefood);
    }

}
