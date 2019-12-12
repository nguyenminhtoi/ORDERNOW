package com.example.administrator.ordernow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.AlertDialog;

import java.util.List;

import static android.R.attr.id;

/**
 * Created by Administrator on 22/11/2019.
 */

public class TableAdapter extends BaseAdapter{

    private TableActivity context;
    private int layout;
    public List<Table> tablelist;

    public TableAdapter(TableActivity context, int layout, List<Table> tablelist) {
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
        TextView tvNameTable, tvNameStore;
        RelativeLayout rvTable;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoler holer;
        if(view == null){
            holer = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holer.tvNameTable = (TextView) view.findViewById(R.id.tv_name_table);
            holer.tvNameStore = (TextView) view.findViewById(R.id.tv_name_store);
            holer.rvTable = (RelativeLayout) view.findViewById(R.id.rv_table);

            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        final Table table = tablelist.get(i);

        holer.tvNameTable.setText(table.getNAME_TABLE());
        holer.tvNameStore.setText(Values.ID);
        holer.rvTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showUpdateDialog(String.valueOf(table.getID()),table.getNAME_TABLE());
            }
        });
        holer.rvTable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Xoa(table.getNAME_TABLE(),table.getID());
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
