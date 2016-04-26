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

    public Preview(Bitmap bitmap){

        mBitmap = bitmap;
        ImageView imageview=(ImageView) ApplicationContext.getActivity().findViewById(R.id.imageView);
        imageview.setImageBitmap(mBitmap);
        mCanvas = new Canvas(mBitmap);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6F);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
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
