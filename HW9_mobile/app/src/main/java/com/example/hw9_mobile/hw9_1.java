package com.example.hw9_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class hw9_1 extends AppCompatActivity {
    final static int LINE=1,CIRCLE=2,RECT=3;
    static int curShape = LINE;
    static int color_check =Color.RED;
    static boolean finish_check=false;
    static List<MyShape> myShape = new ArrayList<MyShape>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_hw9_1,null);
        addContentView(v, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
        ImageView btn_red = v.findViewById(R.id.btn_red);
        ImageView btn_blue = v.findViewById(R.id.btn_blue);
        ImageView btn_black = v.findViewById(R.id.btn_black);
        btn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_check=Color.RED;
            }
        });
        btn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_check=Color.BLUE;
            }
        });
        btn_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_check=Color.BLACK;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(item.getItemId()){
            case R.id.itemline:
                curShape=LINE;
                return true;
            case R.id.itemcircle:
                curShape=CIRCLE;
                return true;
            case R.id.itemrect:
                curShape=RECT;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private static class MyGraphicView extends View {
        int startX = -1, startY=-1,stopX=-1,stopY=-1;
        public MyGraphicView(Context context){super(context);}

        public boolean onTouchEvent(MotionEvent event){
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN: //터치시
                    startX = (int)event.getX();
                    startY = (int)event.getY();
                    finish_check=false;
                    break;
                case MotionEvent.ACTION_MOVE: //움직일떄
                    stopX=(int)event.getX();
                    stopY=(int)event.getY();
                    finish_check=false;
                    this.invalidate();
                    break;
                case MotionEvent.ACTION_UP: //손을 떼는경우

                    MyShape mshape = new MyShape();
                    mshape.shapeType = curShape;
                    mshape.startX = startX;
                    mshape.startY = startY;
                    mshape.stopX = stopX;
                    mshape.stopY = stopY;
                    mshape.color = color_check;
                    myShape.add(mshape);
                    finish_check=true;
                    this.invalidate();

                    break;
            }
                return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            for(int i =0;i<myShape.size();i++){
                MyShape kshape=myShape.get(i);
                paint.setColor(kshape.color);
                switch(kshape.shapeType){
                    case LINE:
                        canvas.drawLine(kshape.startX,kshape.startY,kshape.stopX,kshape.stopY,paint);
                        break;
                    case CIRCLE:
                        int radius = (int)Math.sqrt(Math.pow(kshape.stopX -kshape.startX,2)
                                + Math.pow(kshape.stopY-kshape.startY,2));
                        canvas.drawCircle(kshape.startX,kshape.startY,radius,paint);
                        break;
                    case RECT:
                        Rect rect = new Rect(kshape.startX,kshape.startY,kshape.stopX,kshape.stopY);
                        canvas.drawRect(rect,paint);
                        break;
                }
            }

            if(finish_check==false) {
                paint.setColor(color_check);
                switch (curShape) {
                    case LINE:
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        break;
                    case CIRCLE:
                        int radius = (int) Math.sqrt(Math.pow(stopX - startX, 2) + Math.pow(stopY - startY, 2));
                        canvas.drawCircle(startX, startY, radius, paint);
                        break;
                    case RECT:
                        Rect rect = new Rect(startX, startY, stopX, stopY);
                        canvas.drawRect(rect, paint);
                        break;
                }
            }
        }
    }
    public static class MyShape{
            int shapeType;
            int startX,startY,stopX,stopY;
            int color;
    }
}
