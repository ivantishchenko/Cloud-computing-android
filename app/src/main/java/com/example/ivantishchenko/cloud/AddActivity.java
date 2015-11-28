package com.example.ivantishchenko.cloud;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivantishchenko.parsers.EventJSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class AddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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


    public void sendEvent(View view) throws JSONException {

        MyTask task = new MyTask();

        JSONObject json = new JSONObject();


        EditText name = (EditText) findViewById(R.id.name);
        EditText place = (EditText) findViewById(R.id.place);
        EditText priority = (EditText) findViewById(R.id.priority);
        EditText with = (EditText) findViewById(R.id.with);

        json.put("name", name.getText().toString());
        json.put("place", place.getText().toString());
        json.put("priority", priority.getText().toString());
        json.put("with", with.getText().toString());


        //http://82.130.17.8:8080/events/
        //task.execute("http://82.130.17.8:8080/events/create", json.toString());
        task.execute("http://130.233.42.100:8080/events/create", json.toString());

        Toast.makeText(this, "New event added", Toast.LENGTH_LONG).show();

        finish();

    }

    private class MyTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                HttpManager.addData(params[0], params[1]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

    }

}
