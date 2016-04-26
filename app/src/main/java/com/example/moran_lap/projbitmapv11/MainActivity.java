package com.example.moran_lap.projbitmapv11;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener{

    //Composer mComposer;
    //Button mStreamButton;
    private ListView mListView;
    private static SurfaceComponentAdapter SCadapter;
    private ArrayList<SurfaceComponent> mSurfaceComponents;

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
        mListView = (ListView) ApplicationContext.getActivity().findViewById(R.id.listView);
        displaySurfaceComponentsList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Add new ImageSource
                mComposer.onPlusButtonClicked();
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

    private void displaySurfaceComponentsList(){
        //ImageSource im = new ScreenSource();
        //ImageSource imim = new CameraSource();
        //mSurfaceComponents.add(new SurfaceComponent(im));
        //mSurfaceComponents.add(new SurfaceComponent(imim));

        SCadapter = new SurfaceComponentAdapter(mSurfaceComponents);
        mListView.setAdapter(SCadapter);
    }

    public static void onListviewChanged(){
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
