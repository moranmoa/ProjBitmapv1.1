package com.example.moran_lap.projbitmapv11;

import android.graphics.Bitmap;
import android.view.View;


/**
 * Created by Moran-Lap on 23/04/2016.
 */
public class CameraSource extends ImageSource {

    Camera2BasicFragment camera2BasicFragment;

    public CameraSource() {
        sourceName = "Camera";
        camera2BasicFragment = Camera2BasicFragment.newInstance();
        ApplicationContext.getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.container, camera2BasicFragment)
                .commit();
    }


    @Override
    public Bitmap getImage() {
        View v1;
        v1 = ApplicationContext.getActivity().getWindow().getDecorView().getRootView();
        //v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);

        return bitmap;

        //return Camera2BasicFragment.getImage();
       // return null;
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

    @Override
    public void EditSource() {

    }
}
