package com.example.administrator.ordernow;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 26/12/2019.
 */

public class Order3Adapter extends BaseAdapter {

    private OrderActivity context;
    private int layout;
    public List<Bill3> tablelist;

    public Order3Adapter(OrderActivity context, int layout, List<Bill3> tablelist) {
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
        TextView tvNameTable, tvStatus, tvPrice, tvTime;
        RelativeLayout rvBill;
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
            holer.tvPrice = (TextView) view.findViewById(R.id.tv_price_bill);
            holer.tvTime = (TextView) view.findViewById(R.id.tv_time);
            holer.rvBill = (RelativeLayout) view.findViewById(R.id.rv_bill);

            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        final Bill3 bill3 = tablelist.get(i);

        holer.tvNameTable.setText(bill3.getNAME_BILL());
        holer.tvTime.setText(bill3.getTIME_CREATED());
        holer.tvTime.setTextColor(Color.RED);
        holer.tvStatus.setTextColor(Color.RED);
        holer.tvNameTable.setTextColor(Color.RED);
        holer.tvStatus.setText("Đã thanh toán");
        holer.tvPrice.setText(String.valueOf(bill3.getTOTAL_PRICE()));
        holer.rvBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //context.showUpdateDialog(String.valueOf(table.getID()),table.getNAME_TABLE());
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