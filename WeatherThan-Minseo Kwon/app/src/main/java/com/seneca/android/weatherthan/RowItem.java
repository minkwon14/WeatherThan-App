package com.seneca.android.weatherthan;

/**
 * Created by minkwon on 2016-11-18.
 */

public class RowItem {
    private String temp;
    private String windChill;
    private String hourText;
    private int iconId;

    public RowItem(String hourText, int iconId, String temp, String windChill){
        this.hourText = hourText;
        this.iconId = iconId;
        this.temp = temp;
        this.windChill = windChill;
    }

    public String getHourText() {
        return hourText;
    }

    public void setHourText(String hourText) {
        this.hourText = hourText;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWindChill() {
        return windChill;
    }

    public void setWindChill(String windChill) {
        this.windChill = windChill;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
