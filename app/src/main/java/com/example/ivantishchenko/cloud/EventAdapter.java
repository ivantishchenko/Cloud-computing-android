package com.example.ivantishchenko.cloud;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ivantishchenko.models.Event;

import java.util.List;

/**
 * Created by Ivan Tishchenko on 27.11.2015.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    private Context context;
    private List<Event> eventList;

    public EventAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        this.context = context;
        this.eventList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_event, parent, false);



        //Display event name in the TextView widget

        Event ev = eventList.get(position);
        TextView tvName = (TextView) view.findViewById(R.id.event_name);
        TextView tvId = (TextView) view.findViewById(R.id.event_id);


        tvName.setText(ev.getName());
        tvId.setText(ev.getId());

        final String test = ev.getName();

        return view;
    }


}
