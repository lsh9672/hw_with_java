package com.example.hw10_mobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class hw10_2_1 extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdater;
    private RecyclerView.LayoutManager mLayoutManger;
    private static final int RESULT_OK = 8989;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw10_2_1_layout);

        mRecyclerView =(RecyclerView)findViewById(R.id.mrecycler);

        mLayoutManger = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManger);

        ArrayList<FruitInfo> fruit_data = new ArrayList<FruitInfo>();
        fruit_data.add(new FruitInfo(R.drawable.fruit_image1,"두리안", "10000원","말레이시아","6월~8월"));
        fruit_data.add(new FruitInfo(R.drawable.fruit_image2, "사과","3000원","뉴질랜드","10월~11월"));
        fruit_data.add(new FruitInfo(R.drawable.fruit_image3,"망고", "2000원","태국","5월~9월"));

        mAdater = new MyAdapter(fruit_data);
        mRecyclerView.setAdapter(mAdater);


    }
}
