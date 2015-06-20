package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    String LOG_TAG = ForecastFragment.FetchWeatherTask.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Save the unit type from the shared preferences
        /*String theme = sharedPrefs.getString(getString(R.string.pref_theme_key),"");
        if (theme.equals("light"))
        {
            this.setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar);

        }
        else if (theme.equals("dark"))
        {
            this.setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);
        }*/


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }

        //Assign the default settings values the first time the app is opened. Setting the third
        //argument to 'false' makes it only run if it has never run before. Setting it to 'true'
        //causes it to run every time the app is opened
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        Toast toast = Toast.makeText(this, "MainActivity", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String[] coords = new String[2];

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.v(ForecastFragment.FetchWeatherTask.class.getSimpleName(), "Settings button pressed");
            Intent intent = new Intent(this, SettingsActivity.class);
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        else if (id == R.id.action_map){
            //TODO URI: http://maps.googleapis.com/maps/api/geocode/json?components=postal_code:23456&sensor=false
            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences((this));
            String zip = sharedPrefs.getString(getString(R.string.pref_location_key), "");
            Log.v(LOG_TAG, "Map button pressed");
            openPreferredLocationInMap(zip);


            //Master master = new Master();
            //master.updateCoords(zip);



        }


        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap(String zip)
    {

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q",zip)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
        else
            Log.d(LOG_TAG, "Couldn't find a map app!");
    }

    //Long, convoluted way to open a map of the users location using AsyncTask to turn a zip into
    //coordinates


    /*public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }*/

    /*public class Master {
        String[] coords = new String[2];
        final String LOG_TAG = Master.class.getSimpleName();

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
                showMap((gmmIntentUri));

            }
        }

        public String[] updateCoords(String zip)
        {
            new fetchCoordsTask().execute(zip);
            Log.v(LOG_TAG, "RETURNING: " + coords[0] + " " + coords[1]);
            return coords;
        }
    }*/

}
