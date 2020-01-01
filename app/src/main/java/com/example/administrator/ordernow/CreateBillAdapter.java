package com.example.administrator.ordernow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;

import static android.R.attr.x;
import static com.example.administrator.ordernow.Values.FOOD;

/**
 * Created by Administrator on 30/12/2019.
 */

public class CreateBillAdapter extends BaseAdapter {

    private CreateBillActivity context;
    private int layout;
    public List<Food> foodlist;

    public CreateBillAdapter(CreateBillActivity context, int layout, List<Food> foodlist) {
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
        ImageView icRemove;
        ImageView icAdd;
        TextView tvNumber;

    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHoler holer;
        if(view == null){
            holer = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holer.tvNameFood = (TextView) view.findViewById(R.id.tv_name_food);
            holer.tvNote = (TextView) view.findViewById(R.id.tv_note_food);
            holer.tvPrice = (TextView) view.findViewById(R.id.tv_price_food);
            holer.tvNumber = (TextView) view.findViewById(R.id.tv_number_food);

            holer.imgFood = (ImageView) view.findViewById(R.id.img_food);
            holer.icRemove = (ImageView) view.findViewById(R.id.ic_remove_food);
            holer.icAdd = (ImageView) view.findViewById(R.id.ic_add_food);
            holer.rvFood = (LinearLayout) view.findViewById(R.id.rv_food);

            view.setTag(holer);
        }else {
            holer = (ViewHoler) view.getTag();
        }
        final Food food = foodlist.get(i);
        holer.icRemove.setVisibility(View.GONE);
        holer.tvNumber.setVisibility(View.GONE);
        holer.tvNameFood.setText(food.getNAME_FOOD());
        holer.tvNote.setText(food.getNOTE());
        if(food.getIMAGE().equals("")){
            holer.imgFood.setImageResource(R.drawable.ic_image);
        }else {
            Picasso.with(context).load(food.getIMAGE()).into(holer.imgFood);
        }
        holer.tvPrice.setText(String.valueOf(food.getPRICE())+" Đ");
        holer.icAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                food.setNUMBER(food.getNUMBER() + 1);
                Values.PRICE = Values.PRICE + food.getPRICE();
                if(Values.FOOD.contains(food.getNAME_FOOD()) == false){
                    Values.FOOD = Values.FOOD + food.getNAME_FOOD() + "(" + "x" + String.valueOf(food.getNUMBER()) + ")"+ " ";
                }else {
                    Values.FOOD = Values.FOOD.replace(food.getNAME_FOOD() + "(" + "x" + String.valueOf(food.getNUMBER() - 1) + ")"+ " ", food.getNAME_FOOD() + "(" + "x" + String.valueOf(food.getNUMBER()) + ")"+ " ");
                }

                holer.tvNumber.setText(String.valueOf(food.getNUMBER()));
                holer.icRemove.setVisibility(View.VISIBLE);
                holer.tvNumber.setVisibility(View.VISIBLE);

                context.showPrice(Values.PRICE);
                context.showFood(Values.FOOD);
            }
        });
        holer.icRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Values.PRICE = Values.PRICE - food.getPRICE();

                food.setNUMBER(food.getNUMBER() - 1);
                if(food.getNUMBER() == 0){
                    Values.FOOD = Values.FOOD.replace(food.getNAME_FOOD() + "(" + "x" + String.valueOf(food.getNUMBER() + 1) + ")"+ " ", "");

                    holer.icRemove.setVisibility(View.GONE);
                    holer.tvNumber.setVisibility(View.GONE);
                    context.showPrice(Values.PRICE);
                    context.showFood(Values.FOOD);
                }else {
                    Values.FOOD = Values.FOOD.replace(food.getNAME_FOOD() + "(" + "x" + String.valueOf(food.getNUMBER() + 1) + ")"+ " ", food.getNAME_FOOD() + "(" + "x" + String.valueOf(food.getNUMBER()) + ")"+ " ");
                    holer.tvNumber.setText(String.valueOf(food.getNUMBER()));
                    context.showPrice(Values.PRICE);
                    context.showFood(Values.FOOD);

                }
            }
        });
        return view;
    }

}
