/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;

public class EarthquakeActivity extends AppCompatActivity {

    private QuakeAdapter mQuakeAdapter;/*When we get to the onPostExecute() method, we need to
    update the ListView. The only way to update the contents of the list is to update the data
    set within the EarthquakeAdapter. To access and modify the instance of the EarthquakeAdapter,
     we need to make it a global variable in the EarthquakeActivity.*/

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01" +
                    "&endtime=2016-05-02&minfelt=50&minmagnitude=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
    /*    ArrayList<String> earthquakes = new ArrayList<>();
        earthquakes.add("San Francisco");
        earthquakes.add("London");
        earthquakes.add("Tokyo");
        earthquakes.add("Mexico City");
        earthquakes.add("Moscow");
        earthquakes.add("Rio de Janeiro");
        earthquakes.add("Paris");

*/
        //ArrayList<Quake> earthquakes = new ArrayList<Quake>();
        //final ArrayList<Quake> earthquakes = QueryUtils.extractEarthquakes();
        //final ArrayList<Quake> earthquakes = QueryUtils.extractFeatureFromJson();
        /*earthquakes.add(new Quake(4.7,"USA","25 march 1987"));
        earthquakes.add(new Quake(4.7,"USA","25 march 1987"));
        earthquakes.add(new Quake(4.7,"USA","25 march 1987"));
        earthquakes.add(new Quake(4.7,"USA","25 march 1987"));
        earthquakes.add(new Quake(4.7,"USA","25 march 1987"));
        earthquakes.add(new Quake(4.7,"USA","25 march 1987"));
        earthquakes.add(new Quake(4.7,"USA","25 march 1987"));*/


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        mQuakeAdapter = new QuakeAdapter(this, new ArrayList<Quake>());
        earthquakeListView.setAdapter(mQuakeAdapter);

        // Create a new {@link ArrayAdapter} of earthquakes
      /*  ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, earthquakes);*/
        QuakeAsyncTask task = new QuakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
        //final QuakeAdapter quakeAdapter = new QuakeAdapter(this, earthquakes);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            /*We override the onItemClick() method, so that when a list item is clicked, we find
            the corresponding Earthquake object from the adapter. (Note that we also had to add the
            “final” modifier on the EarthquakeAdapter local variable, so that we could access the
            adapter variable within the OnItemClickListener.)
             */
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Quake currentQuake = mQuakeAdapter.getItem(i);
                Log.v("EarthquakeActivity", "getting the url: " + currentQuake.getmUrl());
                Uri earthquakeUri = Uri.parse(currentQuake.getmUrl());
                //Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentQuake
                // .getmUrl()));
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentQuake.getmUrl
                        ()));
                //sendIntent.setData(Uri.parse(currentQuake.getmUrl()));

                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent);
                }
            }
        });
    }

    private class QuakeAsyncTask extends AsyncTask<String, Void, List<Quake>> {
        @Override
        protected List<Quake> doInBackground(String... urls) {
            if (urls.length == 0 || urls[0] == null)
                return null;
            List<Quake> earthquake = QueryUtils.fetchEarthquakeData(urls[0]);
            return earthquake;
        }

        @Override
        protected void onPostExecute(List<Quake> data) {
            //super.onPostExecute(quake);
            // Clear the adapter of previous earthquake data
            mQuakeAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mQuakeAdapter.addAll(data);
            }
        }
    }
}
