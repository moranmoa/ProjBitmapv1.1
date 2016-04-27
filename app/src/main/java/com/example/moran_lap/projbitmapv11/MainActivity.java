package com.example.moran_lap.projbitmapv11;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dragndroplist.DragNDropListView;

public class MainActivity extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener{

    private Button mStreamButton;
    private Boolean startStream = true;
    private DragNDropListView mListView;
    private static SurfaceComponentAdapter SCadapter;
    private ArrayList<SurfaceComponent> mSurfaceComponents;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ApplicationContext.setActivity(this);
        final Composer mComposer = new Composer();
        mSurfaceComponents = mComposer.getmSurfaceComponents();
        //((Thread)mComposer).start();
        mListView = (DragNDropListView) ApplicationContext.getActivity().findViewById(R.id.listView);
        displaySurfaceComponentsList();

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
                        //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        // On selecting a spinner item

                        switch(item.getItemId()){
                            case (R.id.camera_source) :
                                mSurfaceComponents.add(new SurfaceComponent(new CameraSource()));
//                                paint.setColor(Color.GREEN);
//                                canvas.drawRect(20F, 300F, 180F, 400F, paint); // left top right bottom
//                                mImageView.invalidate();
                                //mImageView.refreshDrawableState();
                                break;
                            case (R.id.image_source) :
                                mSurfaceComponents.add(new SurfaceComponent(new PictureSource()));
//                                paint.setColor(Color.RED);
//                                canvas.drawRect(40F, 300F, 180F, 400F, paint);
//                                mImageView.invalidate();
                                break;
                            case (R.id.text_source) :
                                mSurfaceComponents.add(new SurfaceComponent(new TextSource()));
//                                paint.setColor(Color.YELLOW);
//                                canvas.drawRect(20F, 600F, 180F, 400F, paint);
//                                mImageView.invalidate();
                                break;
                            case (R.id.screen_source) :
                                SurfaceComponent screenComponent = new SurfaceComponent(new ScreenSource(),new Position());
                                //screenComponent.Enable();
                                mSurfaceComponents.add(screenComponent);

            //                  synchronized (mObj) {
            //                      mObj.notify();
            //                  }
//                                paint.setColor(Color.BLUE);
//                                canvas.drawRect(20F, 300F, 200F, 400F, paint);
//                                mImageView.invalidate();
                                break;
//                            default:
//                                Toast.makeText(parent.getContext(), "No Item Selected", Toast.LENGTH_LONG).show();
                        }
                        ((MainActivity)ApplicationContext.getActivity()).onListviewChanged();

                        return true;
                    }
                });

                popup.show();//showing popup menu
                //Add new ImageSource
                //mComposer.onPlusButtonClicked();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
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

    private void displaySurfaceComponentsList()
    {
        ArrayList<Map<String, String>> mapData = new ArrayList();

        for (SurfaceComponent sc : mSurfaceComponents) {
            HashMap<String, String> map = new HashMap();
            map.put("sourceName", sc.getSurfaceComponentName());
            mapData.add(map);
        }

        SCadapter = new SurfaceComponentAdapter(mSurfaceComponents,mapData);

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
        mListView.setDragNDropAdapter(SCadapter);
    }

    public void onListviewChanged(){
        displaySurfaceComponentsList();
        SCadapter.notifyDataSetChanged();
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
