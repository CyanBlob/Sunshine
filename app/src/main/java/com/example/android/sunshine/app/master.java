package com.example.android.sunshine.app;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Andrew on 6/18/2015.
 */
public class master
{
    static final String LOG_TAG = master.class.getSimpleName();
    static class fetchCoordsTask extends AsyncTask<String,Long[],Long[]> {

        private Long[] getCoordsFromJson(String zipJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_RESULTS = "results";
            final String OWM_GEOMETRY = "geometry";
            final String OWM_LOCATION = "location";
            final String OWM_LAT = "lat";
            final String OWN_LNG = "lng";

            String lat;
            String lng;

            String output;


            JSONObject zipJson = new JSONObject(zipJsonStr).getJSONArray(OWM_RESULTS).getJSONObject(0).getJSONArray(OWM_GEOMETRY).getJSONObject(0).getJSONArray(OWM_LOCATION).getJSONObject(0);
            //JSONArray results = (JSONArray) zipJson.get(OWM_RESULTS);

            lat = zipJson.getString(OWM_LAT);
            lng = zipJson.getString(OWN_LNG);

            //for (Object elem : results) {
            //    lat = ((JSONObject) elem).get(OWM_LAT).toString();
            //    lng = ((JSONObject) elem).get(OWM_LOCATION).toString();
            //}

            Log.v(LOG_TAG,"RESULTS: " + lat + " " + lng);
            //zipJson = zipJson.getJSONObject(OWN_LOCATION);

            return null;
        }

        Long[] JSONLong;

        @Override
        protected Long[] doInBackground(String... zip)

        //protected Long[] doInBackground(String zip)

        {

            //final String LOG_TAG = master.class.getSimpleName();
            //NETWORKING

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String zipJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

                //TODO URI: http://maps.googleapis.com/maps/api/geocode/json?components=postal_code:23456&sensor=false

                final String BASE_URL = "" +
                        "http://maps.googleapis.com/maps/api/geocode/json?components=postal_code:";


                final String END_URL = "&sensor=false";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter("75077","75077")
                        .appendQueryParameter(END_URL, END_URL)
                        .build();

                //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI: " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                zipJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Forecast JSON String" + zipJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                JSONLong = getCoordsFromJson(zipJsonStr);
                Log.v(LOG_TAG, "Weather Data: " + JSONLong[0]);
                return getCoordsFromJson(zipJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;


        }
    }
    public static void updateCoords(String zip)
    {
        new fetchCoordsTask();
        new fetchCoordsTask().execute();
    }
}
