package com.seneca.android.weatherthan;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import model.CurrentCondition;
import model.Days5;
import model.Hour3;

/**
 * Created by minkwon on 2016-11-18.
 */



public class DaysListAdapter extends ArrayAdapter<Integer> {


    private Context context;

    private ArrayList<Integer> dIndexItems;
    public DaysListAdapter(Context context,
                           int resourceId,
                           ArrayList<Integer> items ) {

        super( context, resourceId, items );
        this.context = context;
        dIndexItems = items;
    }


    private class ViewHolder {
        TextView txtDay;
        ImageView imageView;
        TextView txtMax;
        TextView txtMin;
        TextView txtTemp;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;


        int index = dIndexItems.get(position);
        Days5 days5 = ((MainActivity) context).getDays5WithIndex(index);


        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(
                        Activity.LAYOUT_INFLATER_SERVICE);



        //the view holder pattern: reuse existing views of ListView items

        if (convertView == null) { // the old/recycled view does NOT exist

            // create a view of the ListView item
            //  - use the CUSTOM layout of a ListView item (list_item.xml)

            convertView = mInflater.inflate(R.layout.list_5days, null);

            // create a view holder to reference inner views of the ListView item
            //  - expensive system operations: findViewById( ) is called three times

            holder = new ViewHolder();

            holder.txtDay = (TextView) convertView.findViewById(R.id.dayText);
            holder.imageView = (ImageView) convertView.findViewById(R.id.dayIcon);
            holder.txtMax = (TextView) convertView.findViewById(R.id.dayMaxTempText);
            holder.txtMin = (TextView) convertView.findViewById(R.id.dayMinTempText);
            holder.txtTemp = (TextView) convertView.findViewById(R.id.dayTempText);

            convertView.setTag(holder); // associate the view holder with the new View
        } else
            holder = (ViewHolder) convertView.getTag(); // retrieve the view holder of
        // the old/recycled view


        CurrentCondition currentCondition = days5.currentCondition;
        // MAP data to the inner views of the ListView item
        //  - update the data objects referenced by the view holder
        holder.txtDay.setText( days5.getDayText() );
        holder.txtTemp.setText( days5.getTemp() );
        holder.imageView.setImageResource( days5.getIconId(context));
        holder.txtMax.setText( days5.getMax() );
        holder.txtMin.setText( days5.getMin() );

        return convertView;
    }


}



