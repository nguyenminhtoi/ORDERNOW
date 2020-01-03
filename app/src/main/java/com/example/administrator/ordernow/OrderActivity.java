package com.example.administrator.ordernow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity {
    String urlGetData1 = "http://minhtoi96.me/order/bill/bill1.php";
    String urlGetData2 = "http://minhtoi96.me/order/bill/bill2.php";
    String urlGetData3 = "http://minhtoi96.me/order/bill/bill3.php";

    @Bind(R.id.back)
    ImageView imgBack;
    @Bind(R.id.btn_order)
    Button btnOrder;
    @Bind(R.id.btn_pay)
    Button btnPay;
    @Bind(R.id.lv_order)
    ListView lvOrder;
    @Bind(R.id.lv_bill)
    ListView lvBill;
    @Bind(R.id.lv_pay)
    ListView lvPay;
    @Bind(R.id.ln_order)
    LinearLayout lnOrder;
    @Bind(R.id.ln_pay)
    LinearLayout lnPay;

    ArrayList<Bill1> arrayList1;
    ArrayList<Bill2> arrayList2;
    ArrayList<Bill3> arrayList3;
    Order1Adapter adapter1;
    Order2Adapter adapter2;
    Order3Adapter adapter3;
    String id, role;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            id = sharedPreferences.getString("id", "90");
            role = sharedPreferences.getString("role", "90");
        }

        GetData1( Integer.valueOf(id));
        GetData2( Integer.valueOf(id));

        arrayList1 = new ArrayList<>();
        adapter1 = new Order1Adapter(OrderActivity.this, R.layout.list_order, arrayList1);
        lvOrder.setAdapter(adapter1);

        arrayList2 = new ArrayList<>();
        adapter2 = new Order2Adapter(OrderActivity.this, R.layout.list_order, arrayList2);
        lvBill.setAdapter(adapter2);

        arrayList3 = new ArrayList<>();
        adapter3 = new Order3Adapter(OrderActivity.this, R.layout.list_order, arrayList3);
        lvPay.setAdapter(adapter3);

        lvOrder.setVisibility(View.VISIBLE);
        lvBill.setVisibility(View.VISIBLE);
        lvPay.setVisibility(View.GONE);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvOrder.setVisibility(View.VISIBLE);
                lvBill.setVisibility(View.VISIBLE);
                lvPay.setVisibility(View.GONE);

                GetData1( Integer.valueOf(id));
                GetData2( Integer.valueOf(id));

                btnPay.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnPay.setTextColor(getResources().getColor(R.color.white));

                btnOrder.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnOrder.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvOrder.setVisibility(View.GONE);
                lvBill.setVisibility(View.GONE);
                lvPay.setVisibility(View.VISIBLE);

                GetData3( Integer.valueOf(id));
                arrayList3 = new ArrayList<>();
                adapter3 = new Order3Adapter(OrderActivity.this, R.layout.list_order, arrayList3);
                lvPay.setAdapter(adapter3);

                btnPay.setBackgroundColor(getResources().getColor(R.color.white));
                btnPay.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnOrder.setTextColor(getResources().getColor(R.color.white));
                btnOrder.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(role.equals("2")){
                    Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    finish();
                }
            }
        });
        
}
    private void GetData1(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        arrayList1.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayList1.add(new Bill1(
                                        object.getInt("ID"),
                                        object.getInt("ID_USER"),
                                        object.getString("NAME_BILL"),
                                        object.getString("ID_TABLE"),
                                        object.getString("ID_FOOD"),
                                        object.getInt("ID_VOUCHER"),
                                        object.getInt("ID_CUSTOMER"),
                                        object.getInt("ID_CREATED"),
                                        object.getString("NOTE"),
                                        object.getInt("TOTAL_PRICE"),
                                        object.getInt("STATUS"),
                                        object.getString("TIME_CREATED")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter1.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
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
    private void GetData2(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        arrayList2.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayList2.add(new Bill2(
                                        object.getInt("ID"),
                                        object.getInt("ID_USER"),
                                        object.getString("NAME_BILL"),
                                        object.getString("ID_TABLE"),
                                        object.getString("ID_FOOD"),
                                        object.getInt("ID_VOUCHER"),
                                        object.getInt("ID_CUSTOMER"),
                                        object.getInt("ID_CREATED"),
                                        object.getString("NOTE"),
                                        object.getInt("TOTAL_PRICE"),
                                        object.getInt("STATUS"),
                                        object.getString("TIME_CREATED")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter2.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
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
    private void GetData3(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayList3.add(new Bill3(
                                        object.getInt("ID"),
                                        object.getInt("ID_USER"),
                                        object.getString("NAME_BILL"),
                                        object.getString("ID_TABLE"),
                                        object.getString("ID_FOOD"),
                                        object.getInt("ID_VOUCHER"),
                                        object.getInt("ID_CUSTOMER"),
                                        object.getInt("ID_CREATED"),
                                        object.getString("NOTE"),
                                        object.getInt("TOTAL_PRICE"),
                                        object.getInt("STATUS"),
                                        object.getString("TIME_CREATED")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter3.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_LONG).show();
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

}
