package com.example.administrator.ordernow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.example.administrator.ordernow.R.id.imageView;

/**
 * Created by Administrator on 16/12/2019.
 */

public class FoodAdapter extends BaseAdapter {

    private FoodActivity context;
    private int layout;
    public List<Food> foodlist;

    public FoodAdapter(FoodActivity context, int layout, List<Food> foodlist) {
        this.context = context;
        this.layout = layout;
        this.foodlist = foodlist;
    }

    @Override
    public int getCount() {
        return foodlist.size();
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
        TextView tvNameFood, tvNote, tvPrice;
        LinearLayout rvFood;
        ImageView imgFood;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoler holer;
        if(view == null){
            holer = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holer.tvNameFood = (TextView) view.findViewById(R.id.tv_name_food);
            holer.tvNote = (TextView) view.findViewById(R.id.tv_note_food);
            holer.tvPrice = (TextView) view.findViewById(R.id.tv_price_food);
            holer.imgFood = (ImageView) view.findViewById(R.id.img_food);
            holer.rvFood = (LinearLayout) view.findViewById(R.id.rv_food);

            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        final Food food = foodlist.get(i);

        holer.tvNameFood.setText(food.getNAME_FOOD());
        holer.tvNote.setText(food.getNOTE());
        if(food.getIMAGE().equals("")){
            holer.imgFood.setImageResource(R.drawable.ic_image);
        }else {
            Picasso.with(context).load(food.getIMAGE()).into(holer.imgFood);
        }
        holer.tvPrice.setText(String.valueOf(food.getPRICE())+" Đ");
        holer.rvFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showUpdateDialog(String.valueOf(food.getID()),food.getNAME_FOOD(), food.getNOTE(), food.getPRICE());
            }
        });
        holer.rvFood.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Xoa(food.getNAME_FOOD(),food.getID(),food.getIMAGE().replace("http://minhtoi96.me/order/list_food/",""));
                //Toast.makeText(context, food.getIMAGE().replace("http://minhtoi96.me/order/list_food/",""), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }
    private void Xoa(String name, final int id, final String urlImage){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Bạn có muốn xóa "+ name + " không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.Delete(id, urlImage);
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
