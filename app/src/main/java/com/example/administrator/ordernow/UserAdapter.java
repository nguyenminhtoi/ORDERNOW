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

import java.util.List;

/**
 * Created by Administrator on 06/12/2019.
 */

public class UserAdapter extends BaseAdapter {
    private ManagerUserActivity context;
    private int layout;
    public List<ManagerUser> userlist;

    public UserAdapter(ManagerUserActivity context, int layout, List<ManagerUser> userlist) {
        this.context = context;
        this.layout = layout;
        this.userlist = userlist;
    }

    @Override
    public int getCount() {
        return userlist.size();
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
        TextView tvTaiKhoan, tvTen, tvNgaySinh, tvPass;
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
            holer.tvNgaySinh = (TextView) view.findViewById(R.id.tv_ngaysinh);
            holer.rvAccount = (RelativeLayout) view.findViewById(R.id.rv_account);

            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        final ManagerUser managerUser = userlist.get(i);

        holer.tvTaiKhoan.setText(managerUser.getUSER());
        holer.tvPass.setText(managerUser.getPASSWORD());
        holer.tvTen.setText(managerUser.getFULLNAME());
        holer.tvNgaySinh.setText(managerUser.getBIRTHDAY());
        holer.rvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateUserActivity.class);
                intent.putExtra("dataUser", managerUser);
                context.startActivity(intent);
                context.finish();
            }
        });
        holer.rvAccount.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Xoa(managerUser.getUSER(),managerUser.getID());
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
