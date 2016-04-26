package com.example.moran_lap.projbitmapv11;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gili on 08/04/2016.
 */
public class Composer extends Thread {

    private Spinner mSpinner;
    private Preview mPreview;
    private Bitmap mBitmap;
    private ArrayList<SurfaceComponent> mSurfaceComponents;
    private boolean firstSelectionEvent = true;
    private Paint paint;
    private Canvas canvas;
    private Object mObj;
    private ImageView mImageView;
    private SurfaceComponentAdapter SCadapter;
    class ItemSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (firstSelectionEvent)
            {
                firstSelectionEvent = false;
                return;
            }
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();
            //String item = parent.getSelectedItem().toString();

            paint.setColor(Color.GREEN);

            switch(item){
                case ("Camera") :
                    mSurfaceComponents.add(new SurfaceComponent(new CameraSource()));
                    canvas.drawRect(20F, 300F, 180F, 400F, paint);
                    mImageView.invalidate();
                    //mImageView.refreshDrawableState();
                    break;
                case ("Image") :
                    mSurfaceComponents.add(new SurfaceComponent(new PictureSource()));
                    canvas.drawRect(20F, 300F, 180F, 400F, paint);
                    mImageView.invalidate();
                    break;
                case ("Text") :
                    mSurfaceComponents.add(new SurfaceComponent(new TextSource()));
                    canvas.drawRect(20F, 300F, 180F, 400F, paint);
                    mImageView.invalidate();
                    break;
                case ("Screen") :
                    SurfaceComponent screenComponent = new SurfaceComponent(new ScreenSource(),new Position());
                    //screenComponent.Enable();
                    mSurfaceComponents.add(screenComponent);

//                    synchronized (mObj) {
//                        mObj.notify();
//                    }
                    //canvas.drawRect(20F, 300F, 180F, 400F, paint);
                    break;
                default:
                    Toast.makeText(parent.getContext(), "No Item Selected", Toast.LENGTH_LONG).show();
            }
            ((MainActivity)ApplicationContext.getActivity()).onListviewChanged();
            mSpinner.setVisibility(View.GONE);


            // Showing selected spinner item
            // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public Composer(){

        mBitmap = Bitmap.createBitmap(1280, 720,  Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(Color.RED);
        mPreview = new Preview(mBitmap);
        mImageView = (ImageView)ApplicationContext.getActivity().findViewById(R.id.imageView);
        mSurfaceComponents = new ArrayList();
        mObj = new Object();
        paint = mPreview.getPaint();
        canvas = mPreview.getCanvas();

        initSpinner();
    }

    void onPlusButtonClicked(){
        mSpinner.setVisibility(View.VISIBLE);
    }

    private void initSpinner(){
        // Spinner element
        mSpinner = (Spinner) ApplicationContext.getActivity().findViewById(R.id.spinner);

        // Spinner click listener
        mSpinner.setOnItemSelectedListener(new ItemSelectedListener());

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        //categories.add("Select Source");
        categories.add("");
        categories.add("Camera");
        categories.add("Text");
        categories.add("Screen");
        categories.add("Image");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ApplicationContext.getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mSpinner.setAdapter(dataAdapter);

        // spinner will not be visible until plus button pressed
        mSpinner.setVisibility(View.GONE);
    }

    public ArrayList<SurfaceComponent> getmSurfaceComponents() {
        return mSurfaceComponents;
    }

    public void setSCadapter(SurfaceComponentAdapter SCadapter) {
        this.SCadapter = SCadapter;
    }

    public void run() {
        try {
            synchronized (mObj) {
                mObj.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            if (mSurfaceComponents != null) {
                for (SurfaceComponent surfaceComponent : mSurfaceComponents) {
                    if (surfaceComponent.isEnabled()) {
                        ((Thread) surfaceComponent).start();
                    }
                }

                for (SurfaceComponent surfaceComponent : mSurfaceComponents) {
                    try {
                        ((Thread) surfaceComponent).join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        /*
        for(int i=0; i<1280; i++)
            for(int j=0; j<720; j++) {
                for (SurfaceComponent surfaceComponent : mSurfaceComponents) {
                    if (surfaceComponent.isEnabled()) {
                        if (i>=)
                    }
                }
            }
            */
                for (SurfaceComponent surfaceComponent : mSurfaceComponents) {
                    if (surfaceComponent.isEnabled()) {
                        paint.setColor(Color.GREEN);
                        canvas.drawRect(20F, 300F, 180F, 400F, paint);
                        //mBitmap=surfaceComponent.getBitmap();
                    }
                }
            }
            try {
                sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


