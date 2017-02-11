package com.seneca.android.weatherthan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Object;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Util.Utils;
import data.CityPreference;
import data.JSONWeatherParser;
import data.WeatherHttpClient;
import model.CurrentCondition;
import model.Days5;
import model.Hour3;
import model.Weather;

import static java.lang.Math.pow;
import static java.security.AccessController.getContext;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private TextView cityName;
    private TextView temp;
    private ImageView iconView;
    private TextView sunrise;
    private TextView sunset;
    private TextView updated;
    private TextView wc;
    private TextView diffV;
    private View mainView;
    private ListView listView;
    private ListView daysView;
    private TextView desc;

    Weather weather = new Weather();
    Hour3 hour3 = new Hour3();
    Days5 days5 = new Days5();
    private HashMap<Integer, Hour3> mHoursMap = new HashMap<Integer, Hour3>();
    private ArrayList<Integer> mHoursMapKey = new ArrayList<Integer>();

    private HashMap<Integer, Days5> mDaysMap = new HashMap<Integer, Days5>();
    private ArrayList<Integer> mDaysMapKey = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = (View) findViewById(R.id.mainActivity);
        cityName = (TextView) findViewById(R.id.cityText);
        iconView = (ImageView) findViewById(R.id.thumbnailIcon);
        temp = (TextView) findViewById(R.id.tempText);
        sunrise = (TextView) findViewById(R.id.riseText);
        sunset = (TextView) findViewById(R.id.setText);
        updated = (TextView) findViewById(R.id.updateText);
        wc = (TextView) findViewById(R.id.windchillText);
        listView = (ListView) findViewById(R.id.hourList);
        daysView = (ListView) findViewById(R.id.daysList);
        diffV = (TextView) findViewById(R.id.diffText);
        desc = (TextView) findViewById(R.id.descText);






        CityPreference cityPreference = new CityPreference(MainActivity.this);


        renderWeatherData(cityPreference.getCity());
        render3hoursWeatherData(cityPreference.getCity());
        render5daysWeatherData(cityPreference.getCity());

        //In Case of Location Error

        //renderWeatherData("Newyork,US");
        //render3hoursWeatherData("Newyork,US");
        //render5daysWeatherData("Newyork,US");



    }

    public void renderWeatherData (String city) {

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&units=metric&APPID=9844ba52ba00b4406f3111e2109d32dd"});


    }

    public void render3hoursWeatherData (String city) {

        HourTask hourTask = new HourTask();
        hourTask.execute(new String[]{city + "&units=metric&APPID=9844ba52ba00b4406f3111e2109d32dd"});

    }

    public void render5daysWeatherData (String city) {

        DaysTask daysTask = new DaysTask();
        daysTask.execute(new String[]{city + "&units=metric&APPID=9844ba52ba00b4406f3111e2109d32dd"});

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private class WeatherTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... params) {

            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

            weather = JSONWeatherParser.getWeather(data);

            Log.v("Data: ", weather.currentCondition.getDescription());

            return weather;
        }



        @Override
        protected void onPostExecute(Weather weather) {

            super.onPostExecute(weather);



            java.text.DateFormat df = java.text.DateFormat.getTimeInstance();

            String sunriseDate = df.format(new Date(weather.place.getSunrise()*1000));
            String sunsetDate = df.format(new Date(weather.place.getSunset()*1000));
            String updateDate = df.format(new Date(weather.place.getLastupdate()*1000));

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());


            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText("" + tempFormat + "°C");
            sunrise.setText("Sunrise: " + sunriseDate);
            sunset.setText("Sunset: " + sunsetDate);
            updated.setText("Last Updated: " + updateDate);
            desc.setText(weather.currentCondition.getDescription());



            String generatedString = "i" + weather.currentCondition.getIcon().toString();


            double windchill = getWindChill(weather.currentCondition.getTemperature(),weather.wind.getSpeed());

            String windchillFormat = decimalFormat.format(windchill);
            wc.setText("Feels like " + windchillFormat + "°C");

            Log.v("GeneratedString: " , generatedString.substring(3));

            changeBackground(weather.currentCondition.getIcon().toString());

            Resources res = getResources();
            int resourceId = res.getIdentifier(
                    generatedString, "drawable", getPackageName() );
            iconView.setImageResource( resourceId );






        }
    }

    private class HourTask extends AsyncTask<String, Void, Hour3> {

        int index = 0;

        @Override
        protected Hour3 doInBackground(String... params) {

            String data = ((new WeatherHttpClient()).getHour3Data(params[0]));
            mHoursMapKey.clear();
            mHoursMap.clear();
            for (index = 0 ; index <= 2; index++) {
                hour3 = JSONWeatherParser.get3hour(data, index);
                mHoursMap.put(index, hour3);
                mHoursMapKey.add(index);
            }

            return hour3;
        }

        @Override
        protected void onPostExecute(Hour3 hour3) {
            super.onPostExecute(hour3);


            /*java.text.DateFormat df = java.text.DateFormat.getTimeInstance();

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String tempFormat = decimalFormat.format(hour3.currentCondition.getTemperature());

            tempFormat += ("" + tempFormat + "°C");

            String generatedString = "i" + hour3.currentCondition.getIcon().toString();

            double windchill = getWindChill(hour3.currentCondition.getTemperature(),hour3.wind.getSpeed());

            String windchillFormat = decimalFormat.format(windchill);

            windchillFormat += ("Feels like " + windchillFormat + "°C");

            Resources res = getResources();
            int resourceId = res.getIdentifier(
                    generatedString, "drawable", getPackageName() );
            hour1iconView.setImageResource( resourceId );

            String hourTime = df.format(new Date(hour3.place.getLastupdate()*1000));*/



            CustomListAdapter adapter = new CustomListAdapter(
                    MainActivity.this,
                    R.layout.list_3hour,  // custom layout of a ListView item (list_item.xml)
                    mHoursMapKey);           // data set: a List of RowItem objects

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(MainActivity.this); // register an event listener!

//            SharedPreferences hourPref = getSharedPreferences ("hour_list", Context.MODE_PRIVATE);
//            SharedPreferences.Editor hourPrefEditor = hourPref.edit();
//
//
//            hourPrefEditor.putString("hourTime"+index,hourTime);
//            hourPrefEditor.putInt("iconId"+index,resourceId);
//            hourPrefEditor.putString("hourTemp"+index,tempFormat);
//            hourPrefEditor.putString("hourWind"+index,windchillFormat);
//
//            hourPrefEditor.commit();





            }





    }

    private class DaysTask extends AsyncTask<String, Void, Days5> {

        int index = 0;

        @Override
        protected Days5 doInBackground(String... params) {

            String data = ((new WeatherHttpClient()).getDays5Data(params[0]));
            mDaysMapKey.clear();
            mDaysMap.clear();
            for (index = 0 ; index <= 4; index++) {
                days5 = JSONWeatherParser.get5day(data, index);
                mDaysMap.put(index, days5);
                mDaysMapKey.add(index);
            }

            return days5;
        }

        @Override
        protected void onPostExecute(Days5 days5) {
            super.onPostExecute(days5);



            DaysListAdapter adapter = new DaysListAdapter(
                    MainActivity.this,
                    R.layout.list_5days,  // custom layout of a ListView item (list_item.xml)
                    mDaysMapKey);           // data set: a List of RowItem objects




            daysView.setAdapter(adapter);

            daysView.setOnItemClickListener(MainActivity.this); // register an event listener!

            double ydayTemp = mDaysMap.get(mDaysMapKey.get(0)).currentCondition.getTemperature();
            float ywindSpeed = mDaysMap.get(mDaysMapKey.get(0)).place.getSpeed();
            double tdayTemp = weather.currentCondition.getTemperature();
            float twindSpeed = weather.wind.getSpeed();

            String diff = getDifference(ydayTemp, ywindSpeed,tdayTemp, twindSpeed);

            diffV.setText(diff);

        }


    }

    public Hour3 getHour3WithIndex(Integer index){

        return mHoursMap.get(index);

    }

    public Days5 getDays5WithIndex(Integer index){

        return mDaysMap.get(index);

    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change City");

        final EditText cityInput = new EditText(MainActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("Toronto,CA");
        builder.setView(cityInput);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CityPreference cityPreference = new CityPreference(MainActivity.this);
                cityPreference.setCity(cityInput.getText().toString());

                String newCity = cityPreference.getCity();

                renderWeatherData(newCity);
                render3hoursWeatherData(newCity);
                render5daysWeatherData(newCity);


            }
        });

        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.change_cityId) {
            showInputDialog();
        }


        return super.onOptionsItemSelected(item);
    }

    public void changeBackground(String condition) {
        if (condition.equals("01d")){
            mainView.setBackgroundResource(R.drawable.sunny);
        }
        else if (condition.equals("01n")){
            mainView.setBackgroundResource(R.drawable.nsunny);
        }
        else if (condition.equals("02d")||condition.equals("03d")||condition.equals("04d")){
            mainView.setBackgroundResource(R.drawable.dcloudy);
        }
        else if (condition.equals("02n")||condition.equals("03n")||condition.equals("04n")){
            mainView.setBackgroundResource(R.drawable.ncloud);
        }
        else if (condition.equals("09d")||condition.equals("10d")){
            mainView.setBackgroundResource(R.drawable.drain);
        }
        else if (condition.equals("09n")||condition.equals("10n")){
            mainView.setBackgroundResource(R.drawable.nrain);
        }
        else if (condition.equals("11d")||condition.equals("11n")){
            mainView.setBackgroundResource(R.drawable.thunderstorm);
        }
        else if (condition.equals("13d")||condition.equals("13n")){
            mainView.setBackgroundResource(R.drawable.sleet);
        }
        else if (condition.equals("50d")||condition.equals("50n")){
            mainView.setBackgroundResource(R.drawable.mist);
        }
        else{
            mainView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        Drawable background = mainView.getBackground();
        background.setAlpha(100);
    }

    public double getWindChill (double temp, double speed){

        double windChill = 0;

        windChill = 13.12 + (0.6215*temp) - (11.37*pow(speed*3.6,0.16)) + (0.3965*temp*pow(speed*3.6,0.16));

        return windChill;
    }

    public String getDifference (double yTemp, float ySpeed, double tTemp, float tSpeed){
        String stringDiff = "";

        double ywindChill = getWindChill(yTemp,ySpeed);
        double twindChill = getWindChill(tTemp,tSpeed);

        Log.v("ywindChill: " , String.valueOf(ywindChill));
        Log.v("twindChill: " , String.valueOf(twindChill));

        double diff = ywindChill - twindChill;
        DecimalFormat df = new DecimalFormat("#.#");
        String diffString = df.format(Math.abs(diff));

        if (diff < 0) { return diffString + "°C warmer than Yesterday";}

        else if (diff > 0) { return diffString + "°C colder than Yesterday";}

        else return "Not Available";
    }
}

