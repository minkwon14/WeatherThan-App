package com.seneca.android.weatherthan;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;

public class hourView extends Activity
        implements AdapterView.OnItemClickListener {

    private ListView listView;
    private List<RowItem> rowItems;  // data set for ArrayAdapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create an ArrayList of RowItem objects (i.e. the data set)
        // - each element in the data set has an image and two strings

        SharedPreferences hourPref = getSharedPreferences ("hour_list", Context.MODE_PRIVATE);



        String[] hourData = {hourPref.getString("hourTime0",null)};


/*
        rowItems = new ArrayList<RowItem>();
        String [] listdesc = getResources().getStringArray(R.array.desc);

        for (int i = 0; i < listdesc.length; i++) {
            RowItem item = new RowItem(R.drawable.min,listdesc[i]);
            rowItems.add(item);
        }
*/
        // create a ListView object
//        listView = (ListView) findViewById(R.id.hourList);
//
//        // use the CUSTOM layout of a ListView item: list_item.xml ***
//        // the Adapter MAPS data to ListView items ***
//
//        CustomListAdapter adapter = new CustomListAdapter(
//                this,
//                R.layout.list_3hour,  // custom layout of a ListView item (list_item.xml)
//                rowItems);           // data set: a List of RowItem objects
//
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(this); // register an event listener!
    } // end onCreate

    // implementation of the event handler for clicking a ListView item
    // - onItemClick( ) is declared in AdapterView.OnItemClickListener
    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {
    }
}


