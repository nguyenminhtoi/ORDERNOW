package com.example.administrator.ordernow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Administrator on 12/12/2019.
 */

public class VoucherAdapter extends BaseAdapter {

    private VoucherActivity context;
    private int layout;
    public List<Voucher> voucherlist;

    public VoucherAdapter(VoucherActivity context, int layout, List<Voucher> voucherlist) {
        this.context = context;
        this.layout = layout;
        this.voucherlist = voucherlist;
    }

    @Override
    public int getCount() {
        return voucherlist.size();
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
        TextView tvNameVoucher, tvPrice, tvCode;
        RelativeLayout rvVoucher;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoler holer;
        if(view == null){
            holer = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holer.tvNameVoucher = (TextView) view.findViewById(R.id.tv_name_voucher);
            holer.tvPrice = (TextView) view.findViewById(R.id.tv_price);
            holer.tvCode = (TextView) view.findViewById(R.id.tv_code);
            holer.rvVoucher = (RelativeLayout) view.findViewById(R.id.rv_voucher);

            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        final Voucher voucher = voucherlist.get(i);

        holer.tvNameVoucher.setText(voucher.getNAME_VOUCHER());
        holer.tvPrice.setText(voucher.getPRICE_SALE());
        holer.tvCode.setText(voucher.getCODE_VOUCHER());
        holer.rvVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showUpdateDialog(String.valueOf(voucher.getID()),voucher.getNAME_VOUCHER(),voucher.getCODE_VOUCHER(),voucher.getPRICE_SALE());
            }
        });
        holer.rvVoucher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Xoa(voucher.getNAME_VOUCHER(),voucher.getID());
                //Toast.makeText(context, "Đang xoá", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }
    private void Xoa(String name, final int id){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Bạn có muốn xóa tài khoản "+ name + " không?");
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
