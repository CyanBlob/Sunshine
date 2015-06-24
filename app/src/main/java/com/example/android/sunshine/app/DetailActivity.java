package com.example.android.sunshine.app;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
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

    public static String forecastStr;
    ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences((this));
        String zip = sharedPrefs.getString(getString(R.string.pref_location_key), "");

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
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        MenuItem item = menu.findItem(R.id.action_share);

        //mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(getShareIntent());
        mShareActionProvider.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener() {
            @Override
            public boolean onShareTargetSelected(ShareActionProvider actionProvider, Intent intent) {
                Log.v(ForecastFragment.FetchWeatherTask.class.getSimpleName(), "Share button pressed");

                //Uri mUri = Uri.parse("TEST");
                //Intent intent2 = new Intent(Intent.ACTION_SEND);
                //intent2.setData(mUri);
                //intent2.setType("text/plain");
                //intent2.putExtra("Data", "TEST");
                startActivity(intent);
                //mShareActionProvider.setShareIntent(intent2);
                return false;
            }
        });

        return true;
    }

    public Intent getShareIntent()
    {
        //Log.v(ForecastFragment.FetchWeatherTask.class.getSimpleName(), "Share button pressed");

        Uri mUri = Uri.parse("TEST");
        Intent intent2 = new Intent(Intent.ACTION_SEND);
        intent2.setData(mUri);
        intent2.setType("text/plain");

        intent2.putExtra(android.content.Intent.EXTRA_SUBJECT, "My current weather!");
        intent2.putExtra(android.content.Intent.EXTRA_TEXT, forecastStr + " #Sunshine App");
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);


        //startActivity(intent2);
        //mShareActionProvider.setShareIntent(intent2);
        return intent2;
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //Set activity text to the value passed from the intent extra
            Intent intent = this.getActivity().getIntent();

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
                forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                Toast toast = Toast.makeText(this.getActivity(), "Intent Toast: " + forecastStr, Toast.LENGTH_SHORT);
                toast.show();
                ((TextView) rootView.findViewById(R.id.detail_text)).setText(forecastStr);
            }


            return rootView;
        }
    }
}
