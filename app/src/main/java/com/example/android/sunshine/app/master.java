/**************************** THIS FILE IS NOT IN USE. IT WAS COPIED INTO MainActivity.java
package com.example.android.sunshine.app;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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

/*
public class master {
     String[] coords = new String[2];
     final String LOG_TAG = master.class.getSimpleName();

    public void showMap(Uri geoLocation) {

    }

     class fetchCoordsTask extends AsyncTask<String,Void,String[]> {

        private String[] getCoordsFromJson(String zipJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_RESULTS = "results";
            final String OWM_GEOMETRY = "geometry";
            final String OWM_LOCATION = "location";
            final String OWM_LAT = "lat";
            final String OWN_LNG = "lng";




            JSONObject coordJson = new JSONObject(zipJsonStr).getJSONArray(OWM_RESULTS).getJSONObject(0).getJSONObject(OWM_GEOMETRY).getJSONObject(OWM_LOCATION);
            String lat = coordJson.getString(OWM_LAT);
            String lng = coordJson.getString(OWN_LNG);

            coords[0] = lat;
            coords[1] = lng;

            Log.v(LOG_TAG,"RESULTS: Latitude: " + coords[0] + " Longitude: " + coords[1]);
            //zipJson = zipJson.getJSONObject(OWN_LOCATION);

            return coords;
        }

        @Override
        protected String[] doInBackground(String... zip)
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

                //Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                //        .appendQueryParameter("75077",null)
                //        .appendQueryParameter("",END_URL)
                //        .build();
                StringBuilder builtUri = new StringBuilder();
                builtUri.append(BASE_URL).append(zip[0]).append(END_URL);

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
                //coordsLong = getCoordsFromJson(zipJsonStr);
                //Log.v(LOG_TAG, "Weather Data: " + JSONLong[0]);
                return getCoordsFromJson(zipJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }


            return coords;


        }


         @Override
         protected void onPostExecute(String[] coords)
         {

             StringBuilder geo = new StringBuilder();
             geo.append("geo:").append(coords[0]).append(",").append(coords[1]);
             Uri gmmIntentUri = Uri.parse(geo.toString());
             MainActivity main = new MainActivity();
             main.showMap((gmmIntentUri));
             //showMap(gmmIntentUri);

             //Intent intent = new Intent(Intent.ACTION_VIEW);
             //intent.setData(gmmIntentUri);
             //if (intent.resolveActivity(main.getPackageManager()) != null) {
             //    startActivity(intent);
             //}

         }
    }

    public String[] updateCoords(String zip)
    {
        new fetchCoordsTask().execute(zip);
        Log.v(LOG_TAG, "RETURNING: " + coords[0] + " " + coords[1]);
        return coords;
    }
}
*******************************/