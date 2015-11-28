package com.example.ivantishchenko.parsers;

import android.util.Log;

import com.example.ivantishchenko.models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ivan Tishchenko on 23.11.2015.
 */
public class EventJSONParser {

    public static List<Event> parseFeed(String content) throws JSONException {
        JSONArray arr = new JSONArray(content);
        List<Event> eventList = new ArrayList<>();

        for ( int i = 0; i < arr.length(); i++ ) {

            JSONObject obj = arr.getJSONObject(i);
            Event event = new Event();
            // do pasring

            Iterator iter = obj.keys();
            while(iter.hasNext()){
                String key = (String)iter.next();
                String value = obj.getString(key);

                switch (key) {
                    case "_id":  event.setId(value);
                        break;
                    case "name":  event.setName(value);
                        break;
                    case "place":  event.setPlace(value);
                        break;
                    case "priority":  event.setPriority(value);
                        break;
                    case "with":  event.setWith(value);
                        break;
                    case "created":  event.setCreated(value);
                        break;
                    default:
                        break;
                }

                //if ( key.equalsIgnoreCase("name") ) {
                //    event.setName(value);
                //}
            }

            //event.setName(obj.getString("name"));
            //event.setPlace(obj.getString("place"));
            //event.setPriority(obj.getString("priority"));
            //event.setCreated(obj.getString("created"));

            eventList.add(event);

        }

        return eventList;

    }

}
