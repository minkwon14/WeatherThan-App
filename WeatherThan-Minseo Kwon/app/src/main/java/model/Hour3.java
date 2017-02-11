package model;

import android.content.Context;
import android.content.res.Resources;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.pow;

/**
 * Created by minkwon on 2016-10-18.
 */
public class Hour3 {

    public Place place;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Snow snow = new Snow();
    public Clouds clouds = new Clouds();


    /*public CurrentCondition getCurrentCondition(){
        if(currentCondition == null )return new CurrentCondition();
        return currentCondition;
    }*/
    public String getHourText() {
        //java.text.DateFormat df = java.text.DateFormat.getTimeInstance();
        //String hourTime = df.format(new Date(place.getLastupdate()*1000));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
        String hourTime = sdf.format(new Date(place.getLastupdate()*1000));

        return hourTime;
    }

    public int getIconId(Context context) {
        String generatedString = "i" + currentCondition.getIcon().toString();
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(
                generatedString, "drawable", context.getPackageName() );

        return  resourceId;
    }

    public String getTemp() {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String tempFormat = decimalFormat.format(currentCondition.getTemperature()) + "°C";

        return tempFormat;
    }

    public String getWindChill() {

        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        double temp = currentCondition.getTemperature();
        double speed = wind.getSpeed();

        double windChill = 13.12 + (0.6215*temp) - (11.37*pow(speed*3.6,0.16)) + (0.3965*temp*pow(speed*3.6,0.16));;
        String windchillFormat = decimalFormat.format(windChill);
        windchillFormat = ("Feels like " + windchillFormat + "°C");

        return windchillFormat;
    }
}
