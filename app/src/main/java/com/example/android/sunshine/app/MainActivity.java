package com.example.android.sunshine.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    /*public static class PlaceholderFragment extends Fragment {

        private ArrayAdapter<String> mForecastAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //Initialize the fake data for listview
            String[] forecastArray = {
                    "Today - Sunny - 88/63",
                    "Tomorrow - Foggy - 70/46",
                    "Weds - Cloudy - 72/63",
                    "Thurs - Rainy - 64/51",
                    "Fri - Foggy - 70/46",
                    "Sat - Sunny - 76/68"
            };

            List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

            //Possible alternative
            //ArrayList weekForecast = new ArrayList();
            //weekForecast.add("Today - Sunny - 88/64");
            //weekForecast.add("Tomorrow - Foggy - 70/46");
            //weekForecast.add("Weds - Cloudy - 72/63");
            //weekForecast.add("Thurs - Rainy - 64/51");
            //weekForecast.add("Fri - Foggy - 70/46");
            //weekForecast.add("Sat - Sunny - 76/68");



            //Create an ArrayAdapter which will take data and use it to populate the listview it is attached to
            mForecastAdapter =
                new ArrayAdapter<String>(
                    //Current context
                    getActivity(),
                    //ID of list item layout
                    R.layout.list_item_forecast,
                    //ID of the textview to populate
                    R.id.list_item_forecast_textview,
                    //Data
                    weekForecast);



            //NETWORKING

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("ForecastFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                forecastJsonStr = null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("ForecastFragment", "Error closing stream", e);
                    }
                }
            }

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);


            //Get a reference to the ListView, and attach the adapter to it
            ListView listView = (ListView) rootView.findViewById(
                    R.id.listview_forecast);
            listView.setAdapter(mForecastAdapter);

            return rootView;

        }
    }*/
}
