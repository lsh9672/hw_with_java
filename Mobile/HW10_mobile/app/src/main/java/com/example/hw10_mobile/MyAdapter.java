package com.example.hw10_mobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<FruitInfo> fruit_data;
    public MyAdapter(ArrayList<FruitInfo> mydata){
        fruit_data = mydata;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    } //view create
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.item_image.setImageResource(fruit_data.get(position).resImage);
        holder.item_name.setText(fruit_data.get(position).fruitName);
        holder.item_price.setText(fruit_data.get(position).fruitPrice);

        final Context mycontext = holder.itemView.getContext();
        holder.item_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycontext,hw10_2_2.class);
                intent.putExtra("image",fruit_data.get(position).resImage);
                intent.putExtra("name",fruit_data.get(position).fruitName);
                intent.putExtra("price",fruit_data.get(position).fruitPrice);
                intent.putExtra("origin",fruit_data.get(position).fruitOrigin);
                intent.putExtra("time",fruit_data.get(position).fruitTime);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
               mycontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fruit_data.size();
    }

}
