package com.example.administrator.ordernow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.net.ssl.HttpsURLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.attr.id;
import static android.R.attr.theme;
import static android.media.MediaRecorder.VideoSource.CAMERA;
import static android.provider.Contacts.SettingsColumns.KEY;
import static android.provider.ContactsContract.CommonDataKinds.Note.NOTE;

public class FoodActivity extends AppCompatActivity {
    String urlGetData = "http://minhtoi96.me/order/list_food/food.php";
    String urlDelete = "http://minhtoi96.me/order/list_food/delete.php";
    String urlUpdate = "http://minhtoi96.me/order/list_food/update.php";
    String urlGetDataGoup = "http://minhtoi96.me/order/goup_food/goup.php";
    @Bind(R.id.tv_title_ql)
    TextView tvTitle;
    @Bind(R.id.imgBack)
    ImageView imgBack;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.lvFood)
    ListView list;
//    @Bind(R.id.lvFoodGoup)
//    ListView list2;
    ArrayList<Food> arrayList;
    ArrayList<GoupFood> arrayListGoup;
    FoodAdapter adapter;
    GoupInFoodAdapter adapter2;
    String id;
    int sp = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        ButterKnife.bind(this);
        tvTitle.setText("Quản lý món");

        arrayListGoup = new ArrayList<>();
        arrayList = new ArrayList<>();
        adapter = new FoodAdapter(this, R.layout.list_food, arrayList);
        list.setAdapter(adapter);

        //arrayList = new ArrayList<>();
        //adapter = new FoodAdapter(this, R.layout.list_food, arrayList);
        //list.setAdapter(adapter);
        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            id = sharedPreferences.getString("id", "90");
        }
        GetData( Integer.valueOf(id));
        GetDataGoup(Integer.valueOf(id));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodActivity.this, InsertFoodActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void GetData(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(FoodActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        for (int y = 0; y < response.length(); y++) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject object = jsonArray.getJSONObject(y);
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
                        Toast.makeText(FoodActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
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
    public void Delete(final int id, final String urlImage){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(FoodActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }else {
                            Toast.makeText(FoodActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FoodActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(id));
                params.put("IMAGE", urlImage);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    public void Update(final String name, final String note, final String price, final String goup, final String i){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(FoodActivity.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(FoodActivity.this, "Sửa không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FoodActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                        Log.d("A", "Error!\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", i);
                params.put("NAME_FOOD", name);
                params.put("PRICE", price);
                params.put("NOTE", note);
                params.put("ID_GOUP", goup);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void showUpdateDialog(final String i, String name, String note, int price, String goup) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_update_food, null);
        dialogBuilder.setView(dialogView);

        final EditText edtName = (EditText) dialogView.findViewById(R.id.edit_Name);
        final EditText edtNote = (EditText) dialogView.findViewById(R.id.edit_Note);
        final EditText edtPrice = (EditText) dialogView.findViewById(R.id.edit_Price);
        final Spinner spnGoup = (Spinner) dialogView.findViewById(R.id.spn_goup);

        List<String> categories = new ArrayList<String>();
        categories.add("Chọn nhóm món");

        for (int y = 0; y < arrayListGoup.size(); y++) {
            final GoupFood goupFood = arrayListGoup.get(y);
            categories.add(goupFood.getNAME_GOUP());
            //Toast.makeText(FoodActivity.this, goupFood.getNAME_GOUP(), Toast.LENGTH_SHORT).show();
            if(goupFood.getNAME_GOUP().equals(goup)){
                sp = y + 1;
                //Toast.makeText(FoodActivity.this, String.valueOf(sp), Toast.LENGTH_SHORT).show();
            }
            if (goup.equals("Chọn nhóm món")){
                sp = 0;
                //Toast.makeText(FoodActivity.this, String.valueOf(sp), Toast.LENGTH_SHORT).show();

            }

        }
        ArrayAdapter<String> adapterGT = new ArrayAdapter<String>(this, R.layout.spiner_goup, R.id.textSpin, categories);
        spnGoup.setAdapter(adapterGT);

        spnGoup.setSelection(sp);
        edtName.setText(name);
        edtNote.setText(note);
        edtPrice.setText(String.valueOf(price));

        dialogBuilder.setTitle("SỬA TÊN MÓN");
        dialogBuilder.setMessage("Nhập tên món mới");
        dialogBuilder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Toast.makeText(FoodActivity.this, edt.getText().toString().trim(), Toast.LENGTH_SHORT).show();

                Update(edtName.getText().toString().trim(), edtNote.getText().toString().trim(), edtPrice.getText().toString().trim(), spnGoup.getSelectedItem().toString(), i);
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
    private void GetDataGoup(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetDataGoup,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(FoodActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayListGoup.add(new GoupFood(
                                        object.getInt("ID"),
                                        object.getInt("ID_USER"),
                                        object.getString("NAME_GOUP")
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
                        Toast.makeText(FoodActivity.this, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
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
