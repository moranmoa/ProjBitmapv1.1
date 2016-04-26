package com.example.moran_lap.projbitmapv11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import dragndroplist.DragNDropSimpleAdapter;

/**
 * Created by Gili on 25/04/2016.
 */
public class SurfaceComponentAdapter extends DragNDropSimpleAdapter {

    List<SurfaceComponent> surfaceComponents;
    Context context;

    public SurfaceComponentAdapter(List<SurfaceComponent> surfaceComponents,ArrayList<Map<String, String>> mapData){//, Cursor cursor) {
        super(ApplicationContext.getActivity(), mapData,R.layout.single_listview_item, new String[]{"text"},new int[]{R.id.sourcename},R.id.sourcename);
        //super(ApplicationContext.getActivity(),R.layout.single_listview_item, cursor,new String[]{"text"},new int[]{R.id.sourcename},R.id.sourcename);
        this.surfaceComponents = surfaceComponents;
        context = ApplicationContext.getActivity();
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
            holder.checkbox.setOnCheckedChangeListener((MainActivity) context);
            v.setTag(holder);
        }
        else{
            holder = (SurfaceComponentsHolder) v.getTag();
        }
        SurfaceComponent sp = surfaceComponents.get(position);
        holder.SourceName.setText(sp.getSurfaceComponentName());
        //holder.checkbox.setChecked(sp.isEnabled());
        //holder.checkbox.setTag(sp);
        //((MainActivity)ApplicationContext.getActivity()).onListviewChanged();

        return v;
    }

    class SurfaceComponentsHolder{
        public TextView SourceName;
        public CheckBox checkbox;
    }
}