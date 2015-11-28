package com.example.ivantishchenko.cloud;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivantishchenko.models.Event;
import com.example.ivantishchenko.parsers.EventJSONParser;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    public static final String SINGLE_EVENT = "singleEvent";
    private TextView output;
    private ProgressBar bar;
    private List list;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getOverflowMenu();

        bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setVisibility(View.INVISIBLE);

        list = new ArrayList<>();
        // get All of the events
        if ( isOnline() ) {
            //
            //http://82.130.17.8:8080/
            requestData("http://130.233.42.100:8080/events/");
        }
        else {
            Toast.makeText(this, "No network connection", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_do_task) {
            if ( isOnline() ) {
                //
                //http://82.130.17.8:8080/events/
                //requestData("http://82.130.17.8:8080/events/");
                requestData("http://130.233.42.100:8080/events");
            }
            else {
                Toast.makeText(this, "No network connection", Toast.LENGTH_LONG).show();
            }
        }
        // add screen
        if (id == R.id.action_add) {
            Intent di = new Intent(this, AddActivity.class);
            startActivity(di);
        }
        // delete all
        if (id == R.id.action_delete_all) {
            DeleteTask task = new DeleteTask();
            // http://82.130.17.8:8080/events/
            //task.execute("http://82.130.17.8:8080/events/");
            task.execute("http://130.233.42.100:8080/events");
            Toast.makeText(this, "All events deleted", Toast.LENGTH_LONG).show();
            requestData("http://130.233.42.100:8080/events");
        }
        return false;
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if ( info != null && info.isConnectedOrConnecting() ) return true;
        else return false;
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    protected void updateDisplay() {

        final EventAdapter adapter = new EventAdapter(this, R.layout.item_event, eventList);

        //setListAdapter(adapter);

        ListView list = getListView();
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //list.setItemChecked(0, true);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent di = new Intent(MainActivity.this, SingleEventActivity.class);

                Event ev = adapter.getItem(position);
                di.putExtra(SINGLE_EVENT, ev);

                startActivity(di);

            }
        });
    }


    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class DeleteTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                HttpManager.deleteAllEvents(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            if ( list.size() == 0 ) {
                bar.setVisibility(View.VISIBLE);
            }
            list.add(this);
        }

        @Override
        protected String doInBackground(String... params) {
            String content = null;
            try {
                    content = HttpManager.getData(params[0]);
            } catch (IOException e) {
               return content;
            }
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            if ( s != null ) {
                try {
                    eventList = EventJSONParser.parseFeed(s);
                } catch (JSONException e) {
                }

                updateDisplay();
            }

            list.remove(this);


            //testUpdateDisplay("SIZE is : " + list.size() );
            if ( list.size() == 0 ) {
                bar.setVisibility(View.INVISIBLE);
            }

        }

    }


}
