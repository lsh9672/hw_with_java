package com.example.hw10_mobile;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView item_name, item_price;
    ImageView item_image;
    Button item_select;
    public  MyViewHolder(View itemView){
        super(itemView);

        item_image = (ImageView)itemView.findViewById(R.id.item_image);
        item_name = (TextView)itemView.findViewById(R.id.item_name);
        item_price = (TextView)itemView.findViewById(R.id.item_price);
        item_select = (Button)itemView.findViewById(R.id.item_select);


    }

}
