package com.example.welcome.travelmatev11.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.welcome.travelmatev11.Model.EventInfo;
import com.example.welcome.travelmatev11.R;

import java.util.ArrayList;

/**
 * Created by Welcome on 8/22/2016.
 */
public class CustomEventAdapter extends ArrayAdapter {
    private EventInfo eventInfo;
    private TextView eventNameAdptxt;
    private TextView eventDateFromAdptxt;
    private TextView eventDateToAdptxt;
    private TextView eventBudgetAdptxt;
    private ArrayList<EventInfo> eventList;
    private Context context;
    public CustomEventAdapter(Context context, ArrayList<EventInfo> eventList) {
        super(context, R.layout.row_style,eventList);
        this.context=context;
        this.eventList=eventList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_style,null);
        eventNameAdptxt=(TextView)convertView.findViewById(R.id.txtEventNameAdp);
        eventDateFromAdptxt=(TextView)convertView.findViewById(R.id.txtEventDateFromAdp);
        eventDateToAdptxt=(TextView)convertView.findViewById(R.id.txtEventDateToAdp);
        eventBudgetAdptxt=(TextView)convertView.findViewById(R.id.txtEventBudgetAdp);

        eventNameAdptxt.setText(eventList.get(position).getEventName());
        eventDateFromAdptxt.setText("From: " + eventList.get(position).getEventDateFrom());
        eventDateToAdptxt.setText("To: " + eventList.get(position).getEventDateTo());
        eventBudgetAdptxt.setText("Budget: " + String.valueOf(eventList.get(position).getEventBudget()));

        return convertView;
    }
}
