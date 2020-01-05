package com.example.administrator.ordernow;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;



public class InsertFoodActivity extends AppCompatActivity {
    String UPLOAD_URL = "http://minhtoi96.me/order/list_food/Insert.php";
    String urlGetData = "http://minhtoi96.me/order/list_food/food.php";
    @Bind(R.id.tv_title_ql)
    TextView tvTitle;
    @Bind(R.id.imgBack)
    ImageView imgBack;

    @Bind(R.id.edt_insert_name_food)
    EditText edtName;
    @Bind(R.id.edt_insert_note)
    EditText edtNote;
    @Bind(R.id.edt_insert_price)
    EditText edtPrice;
    @Bind(R.id.img_insert)
    ImageView imgFood;

    @Bind(R.id.btn_insert_food)
    Button btnInsert;
    @Bind(R.id.btn_huy_food)
    Button btnHuy;
    String id_user, StringImg;

    Bitmap bitmap, decoded;
    int success;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    private static final String TAG = MainActivity.class.getSimpleName();
    final int min = 0;
    final int max = 999999999;
    final int random = new Random().nextInt((max - min) + 1) + min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_food);
        ButterKnife.bind(this);
        tvTitle.setText("Thêm món");
        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            id_user = sharedPreferences.getString("id", "90");
        }
        imgFood.setImageResource(R.drawable.ic_image);
        imgFood.setTag("default");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertFoodActivity.this, FoodActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFood.setImageResource(0);
                showFileChooser();
                imgFood.setTag("choosed");
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertFoodActivity.this, FoodActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtName.getText().toString().trim().equals("")
                        ||edtNote.getText().toString().trim().equals("")
                        ||edtPrice.getText().toString().trim().equals("")
                        ){
                    Toast.makeText(InsertFoodActivity.this, "Vui lòng nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadImage();
                }
            }
        });
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                Toast.makeText(InsertFoodActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(InsertFoodActivity.this, FoodActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(InsertFoodActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();

                        //menampilkan toast
                        Toast.makeText(InsertFoodActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();

                params.put("ID_USER", id_user);
                params.put("NAME_FOOD", edtName.getText().toString().trim());
                StringImg = String.valueOf(imgFood.getTag());
                if(StringImg.equals("choosed")){
                    params.put("IMAGE", getStringImage(decoded));
                }
                else {
                    params.put("IMAGE", "");
                }
                params.put("PRICE", edtPrice.getText().toString().trim());
                params.put("NUMBER", "0");
                params.put("NOTE", edtNote.getText().toString().trim());
                params.put("RANDOM", String.valueOf(random));

                Log.e(TAG, "" + params);
                return params;
            }
        };

        com.example.administrator.ordernow.app.AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imgFood.setImageBitmap(decoded);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
