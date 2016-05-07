package com.example.moran_lap.projbitmapv11;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dragndroplist.DragNDropListView;

public class MainActivity extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener{

    private Composer mComposer;

    // ImageView - Preview
    private ImageView mImageView;
    private static int RESULT_LOAD_IMG = 1;
    private String imgDecodableString;

    // DragNDropListView - SurfaceComponents with checkboxes
    private DragNDropListView mListView;
    private static SurfaceComponentAdapter SCadapter;
    private ArrayList<SurfaceComponent> mSurfaceComponents;
    private ArrayList<Map<String, String>> mapData;

    // Buttons - StreamButton and plus button to add SurfaceComponents
    private Button mStreamButton;
    private Boolean startStream = true;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set MainActivity to be the global context of this application
        ApplicationContext.setActivity(this);

        // Initialize View components
        mImageView = (ImageView)findViewById(R.id.imageView);

        //final Composer mComposer = new Composer();
        mComposer = new Composer();
        mSurfaceComponents = mComposer.getmSurfaceComponents();
        // Start Composer Thread to work - consider activate on first addition of SurfaceComponent or wait and signal
        //((Thread)mComposer).start();
        mListView = (DragNDropListView) ApplicationContext.getActivity().findViewById(R.id.listView);
        InitializeListView();

        mStreamButton = (Button) findViewById(R.id.streamButton);
        mStreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStream)
                    mStreamButton.setText("Stop Stream");
                else
                    mStreamButton.setText("Start Stream");
                startStream = !startStream;
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, fab);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.plus_popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case (R.id.camera_source) :
                                mSurfaceComponents.add(new SurfaceComponent(new CameraSource()));
                                //paint.setColor(Color.GREEN);
                                //canvas.drawRect(20F, 300F, 180F, 400F, paint); // left top right bottom
                                //mImageView.invalidate();
                                //mImageView.refreshDrawableState();
                                break;
                            case (R.id.image_source) :
                                // Add PictureSource
                                //mSurfaceComponents.add(new SurfaceComponent(new PictureSource()));
                                loadImageFromGallery();

                                // create new Bitmap
                                //ImageBitmap = Bitmap.createBitmap(mComposer.getBitmap(),0,0,600,300);
                                //mImageView.invalidate();
                                //mImageView.refreshDrawableState();
                                break;
                            case (R.id.text_source) :
                                String text = "Test Text Source";
                                Position pos = new Position(20, 500, 180, 400);
                                ImageSource textSource = new TextSource(text,pos);
                                mSurfaceComponents.add(new SurfaceComponent(textSource,pos));
                                break;
                            case (R.id.screen_source) :
                                SurfaceComponent screenComponent = new SurfaceComponent(new ScreenSource(),new Position());
                                //screenComponent.Enable();
                                mSurfaceComponents.add(screenComponent);
                                //synchronized (mObj) {
                                //    mObj.notify();
                                //}
                                //paint.setColor(Color.BLUE);
                                //canvas.drawRect(20F, 300F, 200F, 400F, paint);
                                //mImageView.invalidate();
                                break;
                        }
                        ((MainActivity)ApplicationContext.getActivity()).onListViewChanged();
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });
    }

    public void loadImageFromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst(); // Move to first row
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                File image = new File(imgDecodableString);
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap imageBitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);

                PictureSource pictureSource = new PictureSource();
                //set the original source bitmap with imageBitmap
                pictureSource.setOriginalSourceBitmap(imageBitmap);
                Position pos = new Position(0,100,0,100);
                SurfaceComponent pictureComponent = new SurfaceComponent(pictureSource,pos);
                mSurfaceComponents.add(pictureComponent);
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap,pos.getWidth(),pos.getHeight(),true);
                //set the surface component bitmap with imageBitmap
                pictureComponent.setSurfaceComponentBitmap(imageBitmap);
                ((MainActivity)ApplicationContext.getActivity()).onListViewChanged();
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = mListView.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            SurfaceComponent sp = mSurfaceComponents.get(pos);
            sp.setIsEnabled(isChecked);
            Toast.makeText(ApplicationContext.getActivity(),"Clicked on Source " + sp.getImageSource().getSourceName() +" State is: " + sp.isEnabled(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InitializeListView(){

        mapData = new ArrayList();
        mListView.setOnItemDragNDropListener(new DragNDropListView.OnItemDragNDropListener() {
            @Override
            public void onItemDrag(DragNDropListView parent, View view, int position, long id) {

            }

            @Override
            public void onItemDrop(DragNDropListView parent, View view, int startPosition, int endPosition, long id) {
                SurfaceComponent temp = mSurfaceComponents.get(startPosition);
                if (startPosition < endPosition)
                    for (int i = startPosition; i < endPosition; ++i)
                        mSurfaceComponents.set(i, mSurfaceComponents.get(i + 1));
                else if (endPosition < startPosition)
                    for (int i = startPosition; i > endPosition; --i)
                        mSurfaceComponents.set(i, mSurfaceComponents.get(i - 1));
                mSurfaceComponents.set(endPosition, temp);
            }
        });
    }

    private void refreshSurfaceComponentsList() {
        mapData.clear();
        for (SurfaceComponent sc : mSurfaceComponents) {
            HashMap<String, String> map = new HashMap();
            map.put("sourceName", sc.getSurfaceComponentName());
            mapData.add(map);
        }
        SCadapter = new SurfaceComponentAdapter(mSurfaceComponents,mapData,mComposer);
        mListView.setDragNDropAdapter(SCadapter);
    }

    public void onListViewChanged(){
        refreshSurfaceComponentsList();
        SCadapter.refreshSurfaceComponentsOnBitmap();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
