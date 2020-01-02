package com.example.administrator.ordernow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class PaidActivity extends AppCompatActivity {
    String urlGetDataVoucher = "http://minhtoi96.me/order/voucher/voucher.php";
    String urlGetDataCustomer = "http://minhtoi96.me/order/customer/customer.php";
    String urlGetDataUser = "http://minhtoi96.me/order/user/admin.php";

    @Bind(R.id.tv_title_ql)
    TextView tvTitle;
    @Bind(R.id.imgBack)
    ImageView imgBack;
    @Bind(R.id.img_add)
    ImageView imgAdd;

    @Bind(R.id.tv_name_table_pay)
    TextView tvNameTable;
    @Bind(R.id.tv_name_bill)
    TextView tvNameBill;
    @Bind(R.id.tv_name_user)
    TextView tvNameUser;
    @Bind(R.id.tv_time_order)
    TextView tvTime;
    @Bind(R.id.tv_food_order)
    TextView tvFood;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_price_sale_pay)
    TextView tvPriceSale;
    @Bind(R.id.tv_total_price)
    TextView tvTotalPrice;
    @Bind(R.id.tv_price_total)
    TextView tvPricePay;
    @Bind(R.id.tv_note_food_pay)
    TextView tvNote;

    @Bind(R.id.edt_customer)
    TextView edtCustomer;
    @Bind(R.id.edt_voucher)
    TextView edtVoucher;
    @Bind(R.id.btn_pay_bill)
    Button btnPay;

    ArrayList<Customer> arrayListCustomer;
    ArrayList<Voucher> arrayListVoucher;
    ArrayList<ManagerUser> arrayListUser;

    String id, idBill, nameTable;
    int priceCustomer, priceVoucher , idVoucher, idCustomer, idCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid);
        ButterKnife.bind(this);
        tvTitle.setText("Lịch sử thanh toán");
        imgAdd.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaidActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            id = sharedPreferences.getString("id", "90");
        }
        priceCustomer = 0;
        priceVoucher = 0;

        Intent intent = getIntent();
        Bill3 bill3 = (Bill3) intent.getSerializableExtra("dataPaid");
        idBill = String.valueOf(bill3.getID());
        nameTable = bill3.getID_TABLE();
        tvNameTable.setText(String.valueOf(bill3.getID_TABLE()));
        tvNameBill.setText(String.valueOf(bill3.getNAME_BILL()));
        tvTime.setText(String.valueOf(bill3.getTIME_CREATED()));
        tvFood.setText(String.valueOf(bill3.getID_FOOD()));
        tvNote.setText(String.valueOf(bill3.getNOTE()));

        idVoucher = bill3.getID_VOUCHER();
        idCustomer = bill3.getID_CUSTOMER();
        idCreate = bill3.getID_CREATED();

        //tvPrice.setText(String.valueOf(bill3.getTOTAL_PRICE()));
        tvTotalPrice.setText(String.valueOf(bill3.getTOTAL_PRICE()));
        tvPricePay.setText(String.valueOf(bill3.getTOTAL_PRICE()));

        arrayListCustomer = new ArrayList<>();
        arrayListVoucher = new ArrayList<>();
        arrayListUser = new ArrayList<>();

        GetDataCustomer( Integer.valueOf(id));
        GetDataVoucher( Integer.valueOf(id));
        GetDataUser( Integer.valueOf(id));


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
    private void ShowData(){
        GetPriceCustomer();
        GetPriceVoucher();
        tvPriceSale.setText(String.valueOf(Integer.valueOf(tvTotalPrice.getText().toString())/(100-priceCustomer-priceVoucher)*(priceCustomer+priceVoucher)));
        tvPrice.setText(String.valueOf(Integer.valueOf(tvTotalPrice.getText().toString()) + Integer.valueOf(tvPriceSale.getText().toString())));
    }
    private void GetPriceCustomer(){
        for (int i = 0; i < arrayListCustomer.size(); i++) {
            final Customer customer = arrayListCustomer.get(i);
            if (String.valueOf(idCustomer).equals(String.valueOf(customer.getID()))) {
                priceCustomer = customer.getPRICE_SALE();
                edtCustomer.setText("( " + customer.getNAME_CUSTOMER() + " )"+ "( " + "- " + String.valueOf(priceCustomer) + "%"+" )");
            }
        }
    }
    private void GetPriceVoucher(){
        for (int i = 0; i < arrayListVoucher.size(); i++) {
            final Voucher voucher = arrayListVoucher.get(i);
            if (String.valueOf(idVoucher).equals(String.valueOf(voucher.getID()))) {
                priceVoucher = voucher.getPRICE_SALE();
                edtVoucher.setText("( " + voucher.getNAME_VOUCHER() + " )"+ "( " + "- " + String.valueOf(priceVoucher) + "%"+" )");
            }
        }
    }

    private void GetDataCustomer(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetDataCustomer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //arrayList.clear();
                        //Toast.makeText(PaidActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayListCustomer.add(new Customer(
                                        object.getInt("ID"),
                                        object.getInt("ID_USER"),
                                        object.getString("NAME_CUSTOMER"),
                                        object.getString("CODE_CUSTOMER"),
                                        object.getInt("PHONE"),
                                        object.getString("EMAIL"),
                                        object.getString("BIRTHDAY"),
                                        object.getInt("SEX"),
                                        object.getInt("SCORE"),
                                        object.getInt("LEVEL"),
                                        object.getInt("PRICE_SALE")
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
                        Toast.makeText(PaidActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("USER", String.valueOf(id));
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }
    private void GetDataVoucher(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetDataVoucher,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(PaidActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayListVoucher.add(new Voucher(
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaidActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
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
    private void GetDataUser(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetDataUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //arrayList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayListUser.add(new ManagerUser(
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
                        for (int i = 0; i < arrayListUser.size(); i++) {
                            final ManagerUser managerUser = arrayListUser.get(i);
                            if (String.valueOf(idCreate).equals(String.valueOf(managerUser.getID()))) {
                                tvNameUser.setText(String.valueOf(managerUser.getFULLNAME()));
                            }
                        }
                        ShowData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaidActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
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