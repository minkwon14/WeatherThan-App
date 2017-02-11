package com.seneca.android.weatherthan;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import data.JSONWeatherParser;
import data.WeatherHttpClient;
import model.CurrentCondition;
import model.Hour3;

import static java.lang.Math.pow;

/**
 * Created by minkwon on 2016-11-18.
 */



public class CustomListAdapter extends ArrayAdapter<Integer> {

    private TextView hourtime;
    private TextView hourtemp;
    private ImageView houriconView;
    private TextView hourwc;

    private Context context;
    Hour3 hour3 = new Hour3();

    private ArrayList<Integer> mIndexItems;
    public CustomListAdapter( Context context,
                              int resourceId,
                              ArrayList<Integer> items ) {

        super( context, resourceId, items );
        this.context = context;
        mIndexItems = items;
    }


    private class ViewHolder {
        TextView txtHour;
        ImageView imageView;
        TextView txtTemp;
        TextView txtWindchill;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;


        int index = mIndexItems.get(position);
        Hour3 hour3 = ((MainActivity) context).getHour3WithIndex(index);


        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(
                        Activity.LAYOUT_INFLATER_SERVICE);

        //the view holder pattern: reuse existing views of ListView items

        if (convertView == null) { // the old/recycled view does NOT exist

            // create a view of the ListView item
            //  - use the CUSTOM layout of a ListView item (list_item.xml)

            convertView = mInflater.inflate(R.layout.list_3hour, null);

            // create a view holder to reference inner views of the ListView item
            //  - expensive system operations: findViewById( ) is called three times

            holder = new ViewHolder();

            holder.txtHour = (TextView) convertView.findViewById(R.id.hourText);
            holder.imageView = (ImageView) convertView.findViewById(R.id.hourIcon);
            holder.txtTemp = (TextView) convertView.findViewById(R.id.hourTempText);
            holder.txtWindchill = (TextView) convertView.findViewById(R.id.hourwindchillText);

            convertView.setTag(holder); // associate the view holder with the new View
        } else
            holder = (ViewHolder) convertView.getTag(); // retrieve the view holder of
        // the old/recycled view


        CurrentCondition currentCondition = hour3.currentCondition;
        // MAP data to the inner views of the ListView item
        //  - update the data objects referenced by the view holder
        holder.txtHour.setText( hour3.getHourText() );
        holder.imageView.setImageResource( hour3.getIconId(context));
        holder.txtTemp.setText( hour3.getTemp() );
        holder.txtWindchill.setText( hour3.getWindChill() );

        return convertView;
    }


}

/*public class CustomListAdapter extends ArrayAdapter<RowItem> {

    private TextView hourtime;
    private TextView hourtemp;
    private ImageView houriconView;
    private TextView hourwc;

    private Context context;
    Hour3 hour3 = new Hour3();

    public CustomListAdapter( Context context,
                              int resourceId,
                              List<RowItem> items ) {

        super( context, resourceId, items );
        this.context = context;
    }


    private class ViewHolder {
        TextView txtHour;
        ImageView imageView;
        TextView txtTemp;
        TextView txtWindchill;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        RowItem rowItem = getItem(position); // retrieve from the List (data set)

        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(
                        Activity.LAYOUT_INFLATER_SERVICE);

        //the view holder pattern: reuse existing views of ListView items

        if (convertView == null) { // the old/recycled view does NOT exist

            // create a view of the ListView item
            //  - use the CUSTOM layout of a ListView item (list_item.xml)

            convertView = mInflater.inflate(R.layout.list_3hour, null);

            // create a view holder to reference inner views of the ListView item
            //  - expensive system operations: findViewById( ) is called three times

            holder = new ViewHolder();

            holder.txtHour = (TextView) convertView.findViewById(R.id.hourText);
            holder.imageView = (ImageView) convertView.findViewById(R.id.hourIcon);
            holder.txtTemp = (TextView) convertView.findViewById(R.id.hourTempText);
            holder.txtWindchill = (TextView) convertView.findViewById(R.id.hourwindchillText);

            convertView.setTag(holder); // associate the view holder with the new View
        } else
            holder = (ViewHolder) convertView.getTag(); // retrieve the view holder of
        // the old/recycled view



        // MAP data to the inner views of the ListView item
        //  - update the data objects referenced by the view holder
        holder.txtHour.setText( rowItem.getHourText() );
        holder.imageView.setImageResource( rowItem.getIconId());
        holder.txtTemp.setText( rowItem.getTemp() );
        holder.txtWindchill.setText( rowItem.getWindChill() );

        return convertView;
    }


}*/


