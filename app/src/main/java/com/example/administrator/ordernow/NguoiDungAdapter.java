package com.example.administrator.ordernow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 06/12/2019.
 */

public class NguoiDungAdapter extends BaseAdapter {
    private QuanLyUserActivity context;
    private int layout;
    public List<MangerUser> nguoiDunglist;

    public NguoiDungAdapter(QuanLyUserActivity context, int layout, List<MangerUser> nguoiDunglist) {
        this.context = context;
        this.layout = layout;
        this.nguoiDunglist = nguoiDunglist;
    }

    @Override
    public int getCount() {
        return nguoiDunglist.size();
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
        TextView tvTaiKhoan, tvTen, tvNgheNghiep, tvPass;
        RelativeLayout rvAccount;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoler holer;
        if(view == null){
            holer = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holer.tvTaiKhoan = (TextView) view.findViewById(R.id.tv_account);
            holer.tvPass = (TextView) view.findViewById(R.id.tv_pass);
            holer.tvTen = (TextView) view.findViewById(R.id.tv_ten);
            holer.tvNgheNghiep = (TextView) view.findViewById(R.id.tv_nghenghiep);
            holer.rvAccount = (RelativeLayout) view.findViewById(R.id.rv_account);

            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        final MangerUser nguoiDung = nguoiDunglist.get(i);

        holer.tvTaiKhoan.setText(nguoiDung.getUSER());
        holer.tvPass.setText(nguoiDung.getPASSWORD());
        holer.tvTen.setText(nguoiDung.getFULLNAME());
        holer.tvNgheNghiep.setText(nguoiDung.getNAME_STORE());
        holer.rvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Main2Activity.class);
                intent.putExtra("dataNguoiDung", nguoiDung);
                context.startActivity(intent);
                context.finish();
            }
        });
        holer.rvAccount.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Xoa(nguoiDung.getUSER(),nguoiDung.getID());
                //Toast.makeText(context, "Đang xoá", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }
    private void Xoa(String taikhoan, final int id){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Bạn có muốn xóa tài khoản "+ taikhoan+ " không?");
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
