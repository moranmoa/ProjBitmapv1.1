package com.example.moran_lap.projbitmapv11;

import android.graphics.Bitmap;

/**
 * Created by Gili on 08/04/2016.
 */
public abstract class ImageSource {

    protected String sourceName;
    protected int originalHeight;
    protected int originalWidth;
    //protected ImageReader imageReader;

    public ImageSource(){
        //sourceName=...;
        //originalHeight = ...;
        //originalWidth = ...;
        //imageReader = ...;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(int originalHeight) {
        this.originalHeight = originalHeight;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public void setOriginalWidth(int originalWidth) {
        this.originalWidth = originalWidth;
    }

    // abstract methods
    public abstract Bitmap getImage();

    public abstract void OpenSource();

    public abstract void CloseSource();

    public abstract void SetupSource();//init the source

    public String getSourceName() {
        return sourceName;
    }
}
