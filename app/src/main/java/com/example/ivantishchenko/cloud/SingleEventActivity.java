package com.example.ivantishchenko.cloud;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ivantishchenko.models.Event;


public class SingleEventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_event);

        Event event = (Event) getIntent().getSerializableExtra(MainActivity.SINGLE_EVENT);
        TextView tv = (TextView) findViewById(R.id.single_name);
        if (event.getName() != null && !event.getName().isEmpty() ) tv.append(event.getName() + '\n');
        if (event.getPlace() != null  && !event.getPlace().isEmpty()) tv.append(event.getPlace() + '\n');
        if ( event.getPriority() != null  && !event.getPriority().isEmpty()) tv.append(event.getPriority() + '\n');
        if ( event.getWith() != null && !event.getWith().isEmpty()) tv.append(event.getWith() + '\n');
        if ( event.getCreated() != null && !event.getCreated().isEmpty()) tv.append(event.getCreated() + '\n');
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_event, menu);
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
}
