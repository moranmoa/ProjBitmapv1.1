package com.example.moran_lap.projbitmapv11;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.TextView;

/**
 * Created by Gili on 25/04/2016.
 */
public class TextSource extends ImageSource {

    private String text;
    private Bitmap bitmap;
    private Position position;

    public TextSource(String text, Bitmap originalBitmap, Position pos){
        sourceName = "Text";
        this.text = text;
        bitmap = originalBitmap;
        position = pos;
    }

    @Override
    public Bitmap getImage() {
        return drawTextToBitmap(bitmap,text,position);
    }

    public Bitmap drawTextToBitmap(Bitmap originalBitmap, String text, Position position){
        Resources resources = ApplicationContext.getActivity().getResources();
        float scale = resources.getDisplayMetrics().density;
        Canvas canvas = new Canvas(originalBitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE); // Text Color
        paint.setStrokeWidth(12); // Text Size

        paint.setTextSize((int) (14 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        //int x = (originalBitmap.getWidth() - bounds.width())/2;
        //int y = (originalBitmap.getHeight() + bounds.height())/2;

        canvas.drawText(text, position.getxStart(), position.getyStart(), paint);
        //canvas.drawRect(position.getxStart(),position.getyStart(),position.getxEnd(),position.getyEnd(),paint);
        return originalBitmap;
    }

    @Override
    public void OpenSource() {

    }

    @Override
    public void CloseSource() {

    }

    @Override
    public void SetupSource() {

    }
}
