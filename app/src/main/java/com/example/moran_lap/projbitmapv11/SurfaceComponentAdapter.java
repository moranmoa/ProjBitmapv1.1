package com.example.moran_lap.projbitmapv11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
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
    private List<SurfaceComponentsHolder> holders;
    private MainActivity mainActivity;

    public SurfaceComponentAdapter(List<SurfaceComponent> surfaceComponents,ArrayList<Map<String, String>> mapData, MainActivity mainActivity){
        super(ApplicationContext.getActivity(), mapData,R.layout.single_listview_item, new String[]{"text"},new int[]{R.id.sourcename},R.id.sourcename);
        this.surfaceComponents = surfaceComponents;
        context = ApplicationContext.getActivity();
        holders = new ArrayList<>();
        this.mainActivity = mainActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        SurfaceComponentsHolder holder = new SurfaceComponentsHolder();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.single_listview_item,null);
            holder.SourceName = (TextView) v.findViewById(R.id.sourcename);
            holder.optionsButton = (ImageButton) v.findViewById(R.id.options_button);
            holder.optionsButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(mainActivity, v);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.list_item_popup_view, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch(item.getItemId()){
                                case (R.id.set_position) :

                                    break;
                                case (R.id.configure_surface_component) :

                                    break;
                                case (R.id.delete_surface_component) :

                                    break;
                            }
                            ((MainActivity)ApplicationContext.getActivity()).onListViewChanged();
                            return true;
                        }
                    });
                    popup.show();//showing popup menu
                }
            });
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
                        //mainActivity.onListViewChanged();
                        //mainActivity.refreshSurfaceComponentsList();
                        mainActivity.refreshSurfaceComponentsOnBitmap();
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
        holders.add(holder);
        return v;
    }

    class SurfaceComponentsHolder{
        public TextView SourceName;
        public ImageButton optionsButton;
        public CheckBox checkbox;
    }

}
