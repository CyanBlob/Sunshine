package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

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

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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
            Log.v(ForecastFragment.FetchWeatherTask.class.getSimpleName(), "Map button pressed");
            master.updateCoords(zip);
            Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
            showMap(gmmIntentUri);
            /*Intent intent = new Intent(Intent.ACTION_VIEW);
            //intent.setData(geoLocation);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            return true;*/

        }




        return super.onOptionsItemSelected(item);
    }

    /*private String[] getCoordFromZip(String zip)
            throws JSONException
    {

        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        final String OWM_DATETIME = "dt";
        final String OWM_DESCRIPTION = "main";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        String[] resultStrs = new String[numDays];
        for(int i = 0; i < weatherArray.length(); i++) {
            // For now, using the format "Day, description, hi/low"
            String day;
            String description;
            String highAndLow;

            // Get the JSON object representing the day
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            // The date/time is returned as a long.  We need to convert that
            // into something human-readable, since most people won't read "1400356800" as
            // "this saturday".
            long dateTime = dayForecast.getLong(OWM_DATETIME);
            day = getReadableDateString(dateTime);

            // description is in a child array called "weather", which is 1 element long.
            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            // Temperatures are in a child object called "temp".  Try not to name variables
            // "temp" when working with temperature.  It confuses everybody.
            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            double high = temperatureObject.getDouble(OWM_MAX);
            double low = temperatureObject.getDouble(OWM_MIN);

            highAndLow = formatHighLows(high, low);
            resultStrs[i] = day + " - " + description + " - " + highAndLow;
        }

        //for (String a; resultStrs)
        //   Log.v(LOG_TAG, "Forecast Entry: " + a);
        return resultStrs;
    }*/

}
