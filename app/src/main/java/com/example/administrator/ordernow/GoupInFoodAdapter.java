package com.example.administrator.ordernow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.List;
import java.util.Map;

import static android.R.id.list;
import static com.example.administrator.ordernow.R.drawable.table;

/**
 * Created by Administrator on 10/02/2020.
 */

public class GoupInFoodAdapter extends BaseAdapter {

    private FoodActivity context;
    private int layout;
    public List<GoupFood> gouplist;
    ArrayList<Food> arrayList;
    FoodAdapter adapter;
    //public
    public GoupInFoodAdapter(FoodActivity context, int layout, List<GoupFood> gouplist) {
        this.context = context;
        this.layout = layout;
        this.gouplist = gouplist;
    }

    @Override
    public int getCount() {
        return gouplist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHoler{
        TextView tvNameGoup;
        RelativeLayout rvGoup;
        ListView listFood;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoler holer;

        if(view == null){
            holer = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holer.tvNameGoup = (TextView) view.findViewById(R.id.tv_name_goup_in_food);
            holer.rvGoup = (RelativeLayout) view.findViewById(R.id.rv_goup_in_food);
            holer.listFood = (ListView) view.findViewById(R.id.lvInFood);

            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        final GoupFood goupFood = gouplist.get(i);

        holer.tvNameGoup.setText(goupFood.getNAME_GOUP());

        arrayList = new ArrayList<>();
        adapter = new FoodAdapter(context, R.layout.list_food, arrayList);
        holer.listFood.setAdapter(adapter);

        GetData(goupFood.getID_USER());

        holer.listFood.setVisibility(view.VISIBLE);

        holer.rvGoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        return view;
    }

    public void GetData(final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://minhtoi96.me/order/list_food/food.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
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
                        //Toast.makeText(context, String.valueOf(arrayList.size()), Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Lỗi kết nối sever!", Toast.LENGTH_SHORT).show();
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