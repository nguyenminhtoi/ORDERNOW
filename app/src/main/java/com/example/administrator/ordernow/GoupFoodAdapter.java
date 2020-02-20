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

import static com.example.administrator.ordernow.R.drawable.table;

/**
 * Created by Administrator on 10/02/2020.
 */

public class GoupFoodAdapter extends BaseAdapter {

    private GoupFoodActivity context;
    private int layout;
    public List<GoupFood> gouplist;

    public GoupFoodAdapter(GoupFoodActivity context, int layout, List<GoupFood> gouplist) {
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
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoler holer;
        if(view == null){
            holer = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holer.tvNameGoup = (TextView) view.findViewById(R.id.tv_name_goup);
            holer.rvGoup = (RelativeLayout) view.findViewById(R.id.rv_goup);

            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        final GoupFood goupFood = gouplist.get(i);

        holer.tvNameGoup.setText(goupFood.getNAME_GOUP());
        holer.rvGoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showUpdateDialog(String.valueOf(goupFood.getID()),goupFood.getNAME_GOUP());
            }
        });
        holer.rvGoup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Xoa(goupFood.getNAME_GOUP(),goupFood.getID());
                //Toast.makeText(context, "Đang xoá", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }
    private void Xoa(final String name, final int id){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Bạn có muốn xóa "+ name + " không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.Delete(name,id);
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