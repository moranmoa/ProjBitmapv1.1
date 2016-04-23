package com.example.moran_lap.projbitmapv11;

import android.graphics.Bitmap;

/**
 * Created by Gili on 08/04/2016.
 */
public class SurfaceComponent {

    private ImageSource imageSource;
    private Bitmap mBitmap;
    private Position imagePositionOnSurface;

    public SurfaceComponent(ImageSource source, Position position){
        this.imageSource = source;
        this.imagePositionOnSurface = position;
    }

    public SurfaceComponent(ImageSource source){
        this.imageSource = source;
        this.imagePositionOnSurface = new Position();
    }

    public ImageSource getImageSource() {
        return imageSource;
    }

    public void setImageSource(ImageSource imageSource) {
        this.imageSource = imageSource;
    }

    public Position getImagePositionOnSurface() {
        return imagePositionOnSurface;
    }

    public void setImagePositionOnSurface(Position position) {
        this.imagePositionOnSurface = position;
    }
}
