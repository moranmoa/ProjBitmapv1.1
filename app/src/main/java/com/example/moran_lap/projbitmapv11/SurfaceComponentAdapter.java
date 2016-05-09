package com.example.moran_lap.projbitmapv11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import dragndroplist.DragNDropListView;
import dragndroplist.DragNDropSimpleAdapter;

/**
 * Created by Gili on 25/04/2016.
 */
public class SurfaceComponentAdapter extends DragNDropSimpleAdapter {

    private List<SurfaceComponent> surfaceComponents;
    private Context context;
    private Composer composer;

    public SurfaceComponentAdapter(List<SurfaceComponent> surfaceComponents,ArrayList<Map<String, String>> mapData, Composer comp){
        super(ApplicationContext.getActivity(), mapData,R.layout.single_listview_item, new String[]{"text"},new int[]{R.id.sourcename},R.id.sourcename);
        this.surfaceComponents = surfaceComponents;
        context = ApplicationContext.getActivity();
        composer = comp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        SurfaceComponentsHolder holder = new SurfaceComponentsHolder();
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.single_listview_item,null);
            holder.SourceName = (TextView) v.findViewById(R.id.sourcename);
            holder.checkbox = (CheckBox) v.findViewById(R.id.checkbox);
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    DragNDropListView listView = (DragNDropListView) ApplicationContext.getActivity().findViewById(R.id.listView);
                    int pos = listView.getPositionForView(buttonView);
                    if (pos != ListView.INVALID_POSITION) {
                        SurfaceComponent sp = surfaceComponents.get(pos);
                        sp.setIsEnabled(isChecked);
                        Toast.makeText(ApplicationContext.getActivity(), "Clicked on Source " + sp.getImageSource().getSourceName() + " State is: " + sp.isEnabled(), Toast.LENGTH_SHORT).show();
                        refreshSurfaceComponentsOnBitmap();
                    }
                }
            });
            v.setTag(holder);
        }
        else{
            holder = (SurfaceComponentsHolder) v.getTag();
        }
        SurfaceComponent sp = surfaceComponents.get(position);
        holder.SourceName.setText(sp.getSurfaceComponentName());
        return v;
    }

    class SurfaceComponentsHolder{
        public TextView SourceName;
        public CheckBox checkbox;
    }

    public void refreshSurfaceComponentsOnBitmap(){
        composer.initBitmap();
        notifyDataSetChanged();
        for (SurfaceComponent sc : surfaceComponents){
            if (sc.isEnabled()){

                Bitmap bitmapToDraw = sc.DrawSurfaceComponentOnBitmap();
                Canvas canvas = new Canvas(composer.getBitmap());
                canvas.drawBitmap(bitmapToDraw, sc.getImagePositionOnSurface().getxStart(), sc.getImagePositionOnSurface().getyStart(), null);
                composer.getImageView().invalidate();
            }
        }
        notifyDataSetChanged();
    }

}
