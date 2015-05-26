package com.example.android.sunshine.app;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences((this));
        //Save the unit type from the shared preferences
        /*String theme = sharedPrefs.getString(getString(R.string.pref_theme_key),"");
        if (theme.equals("light"))
        {
            //getActionBar().setTitle("TEST");
            //ActionBar actionBar;
            //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#879f38")));

            this.setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar);
            //int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
            //TextView abTitle = (TextView) findViewById(titleId);
            //abTitle.setTextColor(getResources().getColor(R.color.myColor));
        }
        else if (theme.equals("dark"))
        {
            this.setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        Toast toast = Toast.makeText(this, "DetailActivity", Toast.LENGTH_SHORT);
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
                startActivity(intent);
                return true;
        }

        //TODO
        if (id == R.id.action_map){
            Log.v(ForecastFragment.FetchWeatherTask.class.getSimpleName(), "Map button pressed");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //intent.setData(geoLocation);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //Set activity text to the value passed from the intent extra
            Intent intent = this.getActivity().getIntent();

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
                String forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                Toast toast = Toast.makeText(this.getActivity(), "Intent Toast: " + forecastStr, Toast.LENGTH_SHORT);
                toast.show();
                ((TextView) rootView.findViewById(R.id.detail_text)).setText(forecastStr);
            }


            return rootView;
        }
    }
}
