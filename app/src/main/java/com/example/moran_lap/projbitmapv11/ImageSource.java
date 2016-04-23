package com.example.moran_lap.projbitmapv11;

/**
 * Created by Gili on 08/04/2016.
 */
public abstract class ImageSource {

    private boolean isEnabled;
    private int originalHeight;
    private int originalWidth;
    //private ImageReader imageReader;

    public ImageSource(){
        isEnabled = true;
        //originalHeight = ...;
        //originalWidth = ...;
        //imageReader = ...;
    }

    public boolean isEnabled() { return isEnabled; }

    public void Enable() { isEnabled = true; }

    public void Disable() { isEnabled = false; }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
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
    public abstract void OpenSource();

    public abstract void CloseSource();

    public abstract void SetupSource();

}
