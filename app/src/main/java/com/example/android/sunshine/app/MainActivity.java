package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences((this));
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.v(ForecastFragment.FetchWeatherTask.class.getSimpleName(), "Settings button pressed");
            Intent intent = new Intent(this, SettingsActivity.class);
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
