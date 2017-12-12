package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Arvind on 11/12/17.
 */

public class QuakeLoader extends AsyncTaskLoader<List<Quake>> {
    private static final String LOG_TAG = QuakeLoader.class.getName();  //tag for log messages

    private String mUrl;

    public QuakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        //super.onStartLoading();
        forceLoad(); // Force an asynchronous load. Unlike startLoading() this will ignore a previously loaded data
        // set and load a new one.
    }

    @Override
    public List<Quake> loadInBackground() {
        if (mUrl == null)
            return null;
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Quake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}
