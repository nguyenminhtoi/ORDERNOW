package com.example.administrator.ordernow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;

import static android.R.attr.id;
import static com.example.administrator.ordernow.R.drawable.table;

/**
 * Created by Administrator on 25/12/2019.
 */

public class Order1Adapter extends BaseAdapter {
    String urlGetData = "http://minhtoi96.me/order/list_table/table.php";

    private OrderActivity context;
    private int layout;
    public List<Bill1> tablelist;

    public Order1Adapter(OrderActivity context, int layout, List<Bill1> tablelist) {
        this.context = context;
        this.layout = layout;
        this.tablelist = tablelist;
    }

    @Override
    public int getCount() {
        return tablelist.size();
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
        TextView tvNameTable, tvStatus;
        RelativeLayout rvBill;
        ImageView icTable;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHoler holer;
        if(view == null){
            holer = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holer.tvNameTable = (TextView) view.findViewById(R.id.tv_name_table_bill);
            holer.tvStatus = (TextView) view.findViewById(R.id.tv_status);
            holer.rvBill = (RelativeLayout) view.findViewById(R.id.rv_bill);
            holer.icTable = (ImageView) view.findViewById(R.id.ic_table);

            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        final Bill1 bill1 = tablelist.get(i);

        holer.tvNameTable.setText(String.valueOf(bill1.getID_TABLE()));
        holer.tvStatus.setText("Trống");
        holer.icTable.setImageResource(R.drawable.ic_list_table2);
        holer.rvBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CreateBillActivity.class);
                intent.putExtra("dataBill", bill1);
                context.startActivity(intent);
                context.finish();
            }
        });
        holer.rvBill.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Xoa(bill1.getNAME_BILL(),bill1.getID());
                //Toast.makeText(context, "Đang xoá", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }
}