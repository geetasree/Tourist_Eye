package com.example.welcome.travelmatev11.Activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.welcome.travelmatev11.Adapters.CustomEventAdapter;
import com.example.welcome.travelmatev11.DBCRUD.EventManager;
import com.example.welcome.travelmatev11.DBCRUD.ExpenseManager;
import com.example.welcome.travelmatev11.Model.EventInfo;
import com.example.welcome.travelmatev11.R;
import com.example.welcome.travelmatev11.SharedPreference.MyPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class EventListActivity extends AppCompatActivity {
    EventManager manager;
    ExpenseManager managerExpense;
    ArrayList<EventInfo> arrEventInfoList;
    CustomEventAdapter eventAdapter;
    ListView lstEventData;
    View dialogView;
    MyPreference preference;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog datePickerDialogFrom;
    private DatePickerDialog datePickerDialogTo;
    EditText nameEventEDT;
    EditText budgetEventEDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list2);
        initialization();
        //setDatePickerFrom();
        //setDatePickerTo();
        listData();
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lstEventData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent memoryIntent= new Intent(EventListActivity.this, MemoryActivity.class);
                memoryIntent.putExtra("EVENTID",arrEventInfoList.get(i).getEventId());
                startActivity(memoryIntent);
                finish();
                return true;
            }
        });

        lstEventData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            //    Toast.makeText(EventListActivity.this, "item clicked: "+ i, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(EventListActivity.this);
                LayoutInflater inflater = EventListActivity.this.getLayoutInflater();
                dialogView = inflater.inflate(R.layout.alert_event, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setMessage("You can update the Event ");

                nameEventEDT = (EditText) dialogView.findViewById(R.id.edtEventName);
                budgetEventEDT = (EditText) dialogView.findViewById(R.id.edtEventAmount);
                fromDateEtxt = (EditText) dialogView.findViewById(R.id.edtEventFrom);
                toDateEtxt = (EditText) dialogView.findViewById(R.id.edtEventTo);

                nameEventEDT.setText(arrEventInfoList.get(i).getEventName());
                budgetEventEDT.setText(arrEventInfoList.get(i).getEventBudget()+"");
                fromDateEtxt.setText(arrEventInfoList.get(i).getEventDateFrom()+"");
                toDateEtxt.setText(arrEventInfoList.get(i).getEventDateTo()+"");

                dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String eventName = nameEventEDT.getText().toString();
                        Double eventBudget = Double.parseDouble(budgetEventEDT.getText().toString());
                        String fromDateE= fromDateEtxt.getText().toString();
                        String toDateE= toDateEtxt.getText().toString();

                        if (eventName.trim().equals("")) {
                            Toast.makeText(EventListActivity.this, "Please Put the event name", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("DATA:", eventBudget + "");
                            EventInfo eventInfo = new EventInfo(arrEventInfoList.get(i).getEventId() ,preference.getUserName(), eventName, eventBudget, fromDateE, toDateE);
                            boolean bf = manager.updateEventInfo(eventInfo);
                            if (bf) {
                                Toast.makeText(EventListActivity.this, "********Event Updated*******", Toast.LENGTH_SHORT).show();
                                reStart();
                            } else {
                                Toast.makeText(EventListActivity.this, "Event Not Uodates  ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(EventListActivity.this, "You have not updated the event :-/", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog b = dialogBuilder.create();
                b.show();

            }
        });


    }

    private void reStart() {
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_logout) {
            preference.setUserName("");
            Intent intent=new Intent(this,StartActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,StartActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.event_list, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.getItem(0).setVisible(false);
        menu.getItem(2).setVisible(false);

        return true;
    }

    private void initialization() {

        manager= new EventManager(this);
        lstEventData=(ListView)findViewById(R.id.lstEventList);
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        preference=new MyPreference(this);
    }

    public void listData(){
        arrEventInfoList=manager.getEventInfo(preference.getUserName());
        eventAdapter=new CustomEventAdapter(EventListActivity.this, arrEventInfoList);
        lstEventData.setAdapter(eventAdapter);
    }

    public void addEvent(View view) {
        AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.alert_event, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setMessage("Create a new Event ");
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                nameEventEDT=(EditText) dialogView.findViewById(R.id.edtEventName);
                budgetEventEDT=(EditText) dialogView.findViewById(R.id.edtEventAmount);
                fromDateEtxt = (EditText) dialogView.findViewById(R.id.edtEventFrom);
                toDateEtxt = (EditText) dialogView.findViewById(R.id.edtEventTo);

                String eventName= nameEventEDT.getText().toString();
                String fromDateE= fromDateEtxt.getText().toString();
                String toDateE= toDateEtxt.getText().toString();
                Double eventBudget= Double.parseDouble(budgetEventEDT.getText().toString());

                if(eventName.trim().equals(""))
                {
                    Toast.makeText(EventListActivity.this, "You have not created new event :-/", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("DATA:",eventBudget+"");
                    EventInfo eventInfo = new EventInfo(preference.getUserName(), eventName, eventBudget, fromDateE, toDateE);
                    boolean bf=manager.addEventInfo(eventInfo);
                    if (bf) {
                        Toast.makeText(EventListActivity.this, "********Event Created*******", Toast.LENGTH_SHORT).show();
                        reStart();

                    } else {
                        Toast.makeText(EventListActivity.this, "Event Not Created :-( ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(EventListActivity.this, "You have not created new event :-/", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
        //reStart();
    }

    public void selectDateFrom(View view) {
        Calendar newCalendar = Calendar.getInstance();
        fromDateEtxt = (EditText) dialogView.findViewById(R.id.edtEventFrom);

        datePickerDialogFrom = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialogFrom.show();
    }

    public void selectDateTo(View view) {
        Calendar newCalendar = Calendar.getInstance();
        toDateEtxt = (EditText) dialogView.findViewById(R.id.edtEventTo);
        datePickerDialogTo = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialogTo.show();
    }
}
