package com.example.administrator.ordernow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import static android.provider.Telephony.Carriers.PASSWORD;

/**
 * Created by Administrator on 13/12/2019.
 */

public class CustomerAdapter extends BaseAdapter {
    private CustomerActivity context;
    private int layout;
    public List<Customer> customerlist;

    public CustomerAdapter(CustomerActivity context, int layout, List<Customer> customerlist) {
        this.context = context;
        this.layout = layout;
        this.customerlist = customerlist;
    }

    @Override
    public int getCount() {
        return customerlist.size();
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
        TextView tvName, tvBirth, tvCode, tvPrice;
        RelativeLayout rvCustomer;
        ImageView imgLevel;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoler holer;
        if(view == null){
            holer = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holer.tvName = (TextView) view.findViewById(R.id.tv_name_customer);
            holer.imgLevel = (ImageView) view.findViewById(R.id.ic_level);
            holer.tvBirth = (TextView) view.findViewById(R.id.tv_birthday);
            holer.tvCode = (TextView) view.findViewById(R.id.tv_code_sale);
            holer.tvPrice = (TextView) view.findViewById(R.id.tv_price_sale);
            holer.rvCustomer = (RelativeLayout) view.findViewById(R.id.rv_customer);

            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        final Customer customer = customerlist.get(i);

        holer.tvName.setText(customer.getNAME_CUSTOMER());
        if(customer.getLEVEL() == 2){
            holer.imgLevel.setImageResource(R.drawable.icon_gold);
        }else if(customer.getLEVEL() == 1){
            holer.imgLevel.setImageResource(R.drawable.icon_sliver);
        }else {
            holer.imgLevel.setImageResource(R.drawable.icon_wood);
        }

        holer.tvBirth.setText(customer.getBIRTHDAY());
        holer.tvCode.setText(customer.getCODE_CUSTOMER());
        holer.tvPrice.setText(String.valueOf(customer.getPRICE_SALE()));
        holer.rvCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateCustomerActivity.class);
                intent.putExtra("dataCustomer", customer);
                context.startActivity(intent);
                context.finish();
            }
        });
        holer.rvCustomer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Xoa(customer.getNAME_CUSTOMER(),customer.getID());
                //Toast.makeText(context, "Đang xoá", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }
    private void Xoa(String name, final int id){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Bạn có muốn xóa khách hàng "+ name + " không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.Delete(id);
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }
}
