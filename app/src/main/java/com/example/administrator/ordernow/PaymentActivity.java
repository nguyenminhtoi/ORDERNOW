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

public class PaymentActivity extends AppCompatActivity {
    String urlUpdateBill = "http://minhtoi96.me/order/bill/updatePay.php";
    String urlInsertBill = "http://minhtoi96.me/order/bill/Insert.php";
    String urlGetDataVoucher = "http://minhtoi96.me/order/voucher/voucher.php";
    String urlGetDataCustomer = "http://minhtoi96.me/order/customer/customer.php";
    String urlGetDataUser = "http://minhtoi96.me/order/user/admin.php";
    String urlUpdateCustomer = "http://minhtoi96.me/order/customer/updateScore.php";

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
    EditText edtCustomer;
    @Bind(R.id.edt_voucher)
    EditText edtVoucher;

    @Bind(R.id.btn_pay_bill)
    Button btnPay;

    ArrayList<Customer> arrayListCustomer;
    ArrayList<Voucher> arrayListVoucher;
    ArrayList<ManagerUser> arrayListUser;

    String id, idBill, idVoucher, idCustomer, nameTable , id_created, score;
    int priceCustomer, priceVoucher , idCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        tvTitle.setText("Thanh toán");
        imgAdd.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            id = sharedPreferences.getString("id", "90");
            id_created = sharedPreferences.getString("iduser", "90");
        }
        priceCustomer = 0;
        priceVoucher = 0;
        idVoucher = "0";
        idCustomer = "0";
        score = "0";

        arrayListCustomer = new ArrayList<>();
        arrayListVoucher = new ArrayList<>();
        arrayListUser = new ArrayList<>();

        GetDataCustomer( Integer.valueOf(id));
        GetDataVoucher( Integer.valueOf(id));
        GetDataUser( Integer.valueOf(id));

        Intent intent = getIntent();
        Bill2 bill2 = (Bill2) intent.getSerializableExtra("dataPay");
        idBill = String.valueOf(bill2.getID());
        idCreate = bill2.getID_CREATED();
        nameTable = bill2.getID_TABLE();
        tvNameTable.setText(String.valueOf(bill2.getID_TABLE()));
        tvNameBill.setText(String.valueOf(bill2.getNAME_BILL()));
        tvTime.setText(String.valueOf(bill2.getTIME_CREATED()));
        tvFood.setText(String.valueOf(bill2.getID_FOOD()));
        tvNote.setText(String.valueOf(bill2.getNOTE()));

        tvTotalPrice.setText(String.valueOf(bill2.getTOTAL_PRICE()));
        tvPrice.setText(String.valueOf(bill2.getTOTAL_PRICE()));
        tvPricePay.setText(String.valueOf(bill2.getTOTAL_PRICE()));

        //Toast.makeText(PaymentActivity.this, idBill, Toast.LENGTH_SHORT).show();
        edtCustomer.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean b) {
                for (int i = 0; i < arrayListCustomer.size(); i++) {
                    final Customer customer = arrayListCustomer.get(i);
                    if (edtCustomer.getText().toString().equals(customer.getCODE_CUSTOMER())) {
                        priceCustomer = customer.getPRICE_SALE();
                        edtCustomer.setText("( " + customer.getNAME_CUSTOMER() + " )"+ "( " + "- " + String.valueOf(priceCustomer) + "%"+" )");
                        tvPriceSale.setText(String.valueOf(Float.valueOf( Integer.valueOf(tvPrice.getText().toString())*(priceCustomer+priceVoucher)/100)));
                        tvTotalPrice.setText(String.valueOf(Float.valueOf( tvPrice.getText().toString())-Float.valueOf( tvPriceSale.getText().toString())));
                        tvPricePay.setText(tvTotalPrice.getText().toString());
                        idCustomer = String.valueOf(customer.getID());
                        score = String.valueOf(customer.getSCORE() + Integer.valueOf(String.valueOf(Math.round(Float.valueOf(tvPricePay.getText().toString())))));
                    }
                }
            }
        });
        edtVoucher.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean b) {
                for (int i = 0; i < arrayListVoucher.size(); i++) {
                    final Voucher voucher = arrayListVoucher.get(i);
                    if (edtVoucher.getText().toString().equals(voucher.getCODE_VOUCHER())) {
                        priceVoucher = voucher.getPRICE_SALE();
                        edtVoucher.setText("( " + voucher.getNAME_VOUCHER() + " )"+ "( " + "- " + String.valueOf(priceVoucher) + "%"+" )");
                        tvPriceSale.setText(String.valueOf(Float.valueOf( Integer.valueOf(tvPrice.getText().toString())*(priceCustomer+priceVoucher)/100)));
                        tvTotalPrice.setText(String.valueOf(Float.valueOf( tvPrice.getText().toString())-Float.valueOf( tvPriceSale.getText().toString())));
                        tvPricePay.setText(tvTotalPrice.getText().toString());
                        idVoucher = String.valueOf(voucher.getID());
                    }
                }
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(idCustomer.equals("0")){

                }else {
                    UpdateCustomer();
                }

                UpdateBill();
                InsertBill(nameTable);
                Intent intent = new Intent(PaymentActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();

                //Toast.makeText(PaymentActivity.this, idBill + " "+ id + " "+ idVoucher +" " +idCustomer + " "+ String.valueOf(Math.round(Float.valueOf(tvPricePay.getText().toString()))), Toast.LENGTH_SHORT).show();


            }
        });

    }
    private void GetDataCustomer(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetDataCustomer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //arrayList.clear();
                        //Toast.makeText(PaymentActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PaymentActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
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
                        //Toast.makeText(PaymentActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PaymentActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
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
                        //Toast.makeText(PaymentActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
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

    public void UpdateBill(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateBill,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(PaymentActivity.this, "Thanh toán đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(PaymentActivity.this, "Thanh toán đơn hàng không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", idBill);
                params.put("ID_CREATED", id_created);
                params.put("ID_VOUCHER", idVoucher);
                params.put("ID_CUSTOMER", idCustomer);
                params.put("TOTAL_PRICE", String.valueOf(Math.round(Float.valueOf(tvPricePay.getText().toString()))));
                params.put("STATUS", "3");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void InsertBill(final String table){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertBill,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                        }else {
                            Toast.makeText(PaymentActivity.this, "Thêm không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
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

    private void UpdateCustomer(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateCustomer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                        }else {
                            Toast.makeText(PaymentActivity.this, "Sửa điểm không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", idCustomer);
                params.put("SCORE", score);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
