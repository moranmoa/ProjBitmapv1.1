package com.example.moran_lap.projbitmapv11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Gili on 08/04/2016.
 */
public class Preview {

    private Bitmap mBitmap;
    private Paint mPaint;
    private Canvas mCanvas;
    private ArrayList<SurfaceComponent> surfaceComponents;

    public Preview(){

        mBitmap = Bitmap.createBitmap(1400, 1600,  Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(Color.RED);
        ImageView imageview=(ImageView) ApplicationContext.getActivity().findViewById(R.id.imageView);
        imageview.setImageBitmap(mBitmap);
        mCanvas = new Canvas(mBitmap);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6F);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);

        //mCanvas.drawLine(0F, 0F, 500F, 500F, mPaint);
        //mCanvas.drawText("Hello Graphics", 0, 14, 90, 80, mPaint);
        //mPaint.setColor(Color.GREEN);
        //mCanvas.drawRect(20F, 300F, 180F, 400F, mPaint);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public ArrayList<SurfaceComponent> getSurfaceComponents() {
        return surfaceComponents;
    }

    public Canvas getCanvas() {
        return mCanvas;
    }
}
