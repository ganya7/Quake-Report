package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

//Created by Arvind on 12/12/17.


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Quake>> {


    //log tag
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    //update the ListView. The only way to update the contents of the list is to update the data
    //set within the EarthquakeAdapter. To access and modify the instance of the EarthquakeAdapter,
    //we need to make it a global variable in the EarthquakeActivity.
    // Constant value for the earthquake loader ID. We can choose any integer.
    // This really only comes into play if you're using multiple loaders.
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01" +
                    "&endtime=2016-05-02&minfelt=50&minmagnitude=5";
    private QuakeAdapter mQuakeAdapter;//When we get to the onPostExecute() method, we need to

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        mQuakeAdapter = new QuakeAdapter(this, new ArrayList<Quake>());
        earthquakeListView.setAdapter(mQuakeAdapter);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        android.app.LoaderManager loaderManager = getLoaderManager();
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

    }

    @Override
    public Loader<List<Quake>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new QuakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Quake>> loader, List<Quake> earthquakes) {
        // Clear the adapter of previous earthquake data
        mQuakeAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mQuakeAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Quake>> loader) {
        // Loader reset, so we can clear out our existing data.
        mQuakeAdapter.clear();
    }
}
