package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Util.Utils;
import model.Days5;
import model.Hour3;
import model.Place;
import model.Weather;

/**
 * Created by minkwon on 2016-10-18.
 */
public class JSONWeatherParser {

    public static Weather getWeather (String data) {

        Weather weather = new Weather();


        //Create JsonObject from data

        try {
            JSONObject jsonObject = new JSONObject(data);

            Place place = new Place();

            JSONObject coordObj = Utils.getObject("coord", jsonObject);
            place.setLat(Utils.getFloat("lat", coordObj));
            place.setLon(Utils.getFloat("lon", coordObj));

            //Get the sys obj
            JSONObject sysObj = Utils.getObject("sys", jsonObject);
            place.setCountry(Utils.getString("country", sysObj));
            place.setLastupdate(Utils.getInt("dt", jsonObject));
            place.setSunrise(Utils.getInt("sunrise", sysObj));
            place.setSunset(Utils.getInt("sunset", sysObj));
            place.setCity(Utils.getString("name", jsonObject));
            weather.place = place;


            //Get the weather info
            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utils.getInt("id", jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description", jsonWeather));
            weather.currentCondition.setCondition(Utils.getString("main", jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon", jsonWeather));

            JSONObject mainObj = Utils.getObject("main", jsonObject);
            weather.currentCondition.setHumidity(Utils.getInt("humidity", mainObj));
            weather.currentCondition.setPressure(Utils.getInt("pressure", mainObj));
            weather.currentCondition.setMinTemp(Utils.getFloat("temp_min", mainObj));
            weather.currentCondition.setMaxTemp(Utils.getFloat("temp_max", mainObj));
            weather.currentCondition.setTemperature(Utils.getDouble("temp", mainObj));

            JSONObject windObj = Utils.getObject("wind", jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed", windObj));
            weather.wind.setDeg(Utils.getFloat("deg", windObj));

            JSONObject cloudObj = Utils.getObject("clouds", jsonObject);
            weather.clouds.setPrecipitation(Utils.getInt("all", cloudObj));

            return weather;


        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }

    }

    public static Hour3 get3hour (String data, int index) {

        Hour3 hour3 = new Hour3();


        //Create JsonObject from data

        try {
            JSONObject jsonObject = new JSONObject(data);

            Place place = new Place();

            //Get the weather info
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            JSONObject jsonArray2 = jsonArray.getJSONObject(index);
            JSONObject mainObj = jsonArray2.getJSONObject("main");
            hour3.currentCondition.setTemperature(Utils.getDouble("temp",mainObj));

            //Get Icon
            JSONArray ArrayWeather = jsonArray2.getJSONArray("weather");
            JSONObject WeatherObj = ArrayWeather.getJSONObject(0);
            hour3.currentCondition.setIcon(Utils.getString("icon",WeatherObj));

            //Get Windspeed
            JSONObject windObj = jsonArray2.getJSONObject("wind");
            hour3.wind.setSpeed(Utils.getFloat("speed",windObj));

            //Get HourTime
            place.setLastupdate(Utils.getInt("dt", jsonArray2));
            hour3.place = place;




            return hour3;


        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }

    }

    public static Days5 get5day (String data, int index) {

        Days5 days5 = new Days5();


        //Create JsonObject from data

        try {
            JSONObject jsonObject = new JSONObject(data);

            Place place = new Place();

            //Get the weather info
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            JSONObject jsonArray2 = jsonArray.getJSONObject(index);
            JSONObject mainObj = jsonArray2.getJSONObject("temp");
            days5.currentCondition.setTemperature(Utils.getDouble("day",mainObj));
            days5.currentCondition.setMinTemp(Utils.getFloat("min",mainObj));
            days5.currentCondition.setMaxTemp(Utils.getFloat("max",mainObj));

            //Get Icon
            JSONArray ArrayWeather = jsonArray2.getJSONArray("weather");
            JSONObject WeatherObj = ArrayWeather.getJSONObject(0);
            days5.currentCondition.setIcon(Utils.getString("icon",WeatherObj));


            //Get DaysTime and Speed
            place.setLastupdate(Utils.getInt("dt", jsonArray2));
            place.setSpeed(Utils.getFloat("speed",jsonArray2));
            days5.place = place;






            return days5;


        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }

    }

}
