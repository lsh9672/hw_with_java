package com.example.hw8_mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class myPictureView extends View {//view를 상속받았기 때문에 xml에 패키지명.이 클래스명으로 쓸수있음
    String imagePath=null;

    public myPictureView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);

    }//생성자

    @Override
    protected void onDraw(Canvas canvas){//캔버스에 그림을 그리는 것을 생각
        super.onDraw(canvas);
        if(imagePath!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//이미지의 경로를 넣으면 비트맵으로 만듦
            canvas.scale(2,2,0,0);
            canvas.drawBitmap(bitmap,0,0,null);//비트맵으로 만든것을 canvas에 draw함
            bitmap.recycle();//비트맵을 버림(재활용해라)-캔버스에서 그렸기때문에 더 쓸필요가 없어서 리턴한다 생각
        }

    }



}
//onDraw()가 불리는 것은 invalidate라는 메소드가 있는데 invalidate라고 쓰면 onDraw()메소드가 불리게 됨
