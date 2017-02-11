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
public class Days5 {

    public Place place;
    public Wind wind = new Wind();
    public CurrentCondition currentCondition = new CurrentCondition();


    /*public CurrentCondition getCurrentCondition(){
        if(currentCondition == null )return new CurrentCondition();
        return currentCondition;
    }*/
    public String getDayText() {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd, E");
        String dayTime = sdf.format(new Date(place.getLastupdate()*1000));

        return dayTime;
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
        String tempDay = decimalFormat.format(currentCondition.getTemperature()) + "°C";

        return tempDay;
    }

    public String getMax() {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String tempMax = "H: " + decimalFormat.format(currentCondition.getMaxTemp()) + "°C";

        return tempMax;
    }

    public String getMin() {

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String tempMin = "L: " + decimalFormat.format(currentCondition.getMinTemp()) + "°C";

        return tempMin;
    }


}
