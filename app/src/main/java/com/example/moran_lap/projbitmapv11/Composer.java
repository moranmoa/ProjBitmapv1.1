package com.example.moran_lap.projbitmapv11;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gili on 08/04/2016.
 */
public class Composer extends Thread{

    private Spinner mSpinner;
    private Preview mPreview;
    private Bitmap mBitmap;
    private ArrayList<SurfaceComponent> mSurfaceComponents;
    private boolean firstSelectionEvent = true;
    private String[] SourcesStingsArray;

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

            Paint paint = mPreview.getPaint();
            paint.setColor(Color.GREEN);
            Canvas canvas = mPreview.getCanvas();

            switch(item){
                case ("Camera") : //mSurfaceComponents.add(CameraSource);
                    SourcesStingsArray[SourcesStingsArray.length] = "Camera";
                    canvas.drawRect(20F, 300F, 180F, 400F, paint);
                    break;
                case ("Text") : //mSurfaceComponents.add(TextSource);
                    canvas.drawRect(20F, 300F, 180F, 400F, paint);
                    break;
                case ("Screen") :
                    SurfaceComponent screenComponent = new SurfaceComponent(new ScreenSource(),new Position());
                    screenComponent.Enable();
                    mSurfaceComponents.add(screenComponent);
                    //canvas.drawRect(20F, 300F, 180F, 400F, paint);
                    break;
                case ("Image") : //mSurfaceComponents.add(PictureSource);
                    canvas.drawRect(20F, 300F, 180F, 400F, paint);
                    break;
                default:
                    Toast.makeText(parent.getContext(), "No Item Selected", Toast.LENGTH_LONG).show();
            }

            mSpinner.setVisibility(View.GONE);


            // Showing selected spinner item
            // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public Composer(){

        mBitmap = Bitmap.createBitmap(720, 1280,  Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(Color.RED);
        mPreview = new Preview(mBitmap);

        // Spinner element
        mSpinner = (Spinner) ApplicationContext.getActivity().findViewById(R.id.spinner);

        // Spinner click listener
        mSpinner.setOnItemSelectedListener(new ItemSelectedListener());

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        //categories.add("Select Source");
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

    void onPlusButtonClicked(){
        mSpinner.setVisibility(View.VISIBLE);
    }

    public void run(){
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
        for(int i=0; i<720; i++)
            for(int j=0; j<1280; j++) {
                for (SurfaceComponent surfaceComponent : mSurfaceComponents) {
                    if (surfaceComponent.isEnabled()) {
                        if (i>=)
                    }
                }
            }
            */
        for (SurfaceComponent surfaceComponent : mSurfaceComponents) {
            if (surfaceComponent.isEnabled()) {
                mBitmap=surfaceComponent.getBitmap();
            }
        }
    }

}


