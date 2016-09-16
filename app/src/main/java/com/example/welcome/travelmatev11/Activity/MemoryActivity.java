package com.example.welcome.travelmatev11.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.welcome.travelmatev11.Adapters.CustomMemoryAdapter;
import com.example.welcome.travelmatev11.DBCRUD.EventManager;
import com.example.welcome.travelmatev11.DBCRUD.ExpenseManager;
import com.example.welcome.travelmatev11.DBCRUD.ImageManager;
import com.example.welcome.travelmatev11.Model.EventInfo;
import com.example.welcome.travelmatev11.Model.ExpenseInfo;
import com.example.welcome.travelmatev11.Model.ImageInfo;
import com.example.welcome.travelmatev11.R;
import com.example.welcome.travelmatev11.SharedPreference.MyPreference;

import java.util.ArrayList;

public class MemoryActivity extends AppCompatActivity {
    ExpenseManager manager;
    MyPreference preference;
    int i;
    private ArrayList<ImageInfo> listImage;
    private ImageManager imgManager;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        Intent intent=getIntent();
        i= intent.getIntExtra("EVENTID",-1);
        initialization();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listImage=imgManager.getAllImages(i);

        CustomMemoryAdapter adapter = new CustomMemoryAdapter(listImage, MemoryActivity.this);
        recyclerview.setAdapter(adapter);
   //     Toast.makeText(MemoryActivity.this, "EVENTID:"+ i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void initialization() {
        manager=new ExpenseManager(this);
        preference = new MyPreference(this);
        imgManager = new ImageManager(this);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MemoryActivity.this);
        recyclerview.setLayoutManager(layoutManager);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,EventListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_logOut){
            preference.setUserName("");
            Intent intent=new Intent(this,StartActivity.class);
            startActivity(intent);
            finish();
        }
        /*else if (id==R.id.action_RemainBudget){
            EventManager eM=new EventManager(this);
            int Budget= eM.getEventBudget(listImage.get(i).getEventId());
            Toast.makeText(MemoryActivity.this, "Budget: "+ Budget, Toast.LENGTH_SHORT).show();
            Log.d("Budget: ", Budget+"");
        }*/
        else if (id == R.id.menu_expense) {
            AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.alert_expense, null);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setMessage("Insert your Expenses ");
            dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    EditText titleExpenseEDT=(EditText) dialogView.findViewById(R.id.edtExpenseTitle);
                    EditText amountExpenseEDT=(EditText) dialogView.findViewById(R.id.edtExpenseAmount);

                    String expenseTitle= titleExpenseEDT.getText().toString();
                    Double expenseAmount= Double.parseDouble(amountExpenseEDT.getText().toString());

                    if(expenseTitle.trim().equals(""))
                    {
                        Toast.makeText(MemoryActivity.this, "You have not inserted any expenses :-/", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.d("DATA:",expenseAmount+"");
                        ExpenseInfo expenseInfo=new ExpenseInfo(preference.getUserName(),i,expenseTitle,expenseAmount);

                        boolean bf=manager.addExpenseInfo(expenseInfo);
                        if (bf) {
                            Toast.makeText(MemoryActivity.this, "********Expenses Inserted*******", Toast.LENGTH_SHORT).show();
                            EventManager eventManager= new EventManager(getApplicationContext());
                            int eventBudget=eventManager.getEventBudget(i);
                            Log.d("Budget:",eventBudget+"");
                            ExpenseManager expenseManager=new ExpenseManager(getApplicationContext());
                            int totalExpense=expenseManager.getExpenseTotal(i);
                            Log.d("totalExpense:",totalExpense+"");
                            if(totalExpense> eventBudget){
                                Notification notification=new Notification.Builder(getApplicationContext())
                                        .setContentTitle("Travel Budget")
                                        .setAutoCancel(true)
                                        .setContentText("You have exceed your Budget.")
                                        .setSmallIcon(R.drawable.app_icon)
                                        .build();

                                NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                manager.notify((int) System.currentTimeMillis(),notification);
                            }else if(totalExpense >= eventBudget/2){
                                if(totalExpense> eventBudget){
                                    Notification notification=new Notification.Builder(getApplicationContext())
                                            .setContentTitle("Travel Budget")
                                            .setAutoCancel(true)
                                            .setContentText("You have exceed your half Budget.")
                                            .setSmallIcon(R.drawable.app_icon)
                                            .build();

                                    NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                    manager.notify((int) System.currentTimeMillis(),notification);
                                }
                            }
                        } else {
                            Toast.makeText(MemoryActivity.this, "Expense not Inserted :-( ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MemoryActivity.this, "You have not created new expense :-/", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog b = dialogBuilder.create();
            b.show();
        }
        else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEvent(View view) {
        Intent intent = new Intent(MemoryActivity.this, TakePhotoActivity.class);
        intent.putExtra("EVENTID",i);
        startActivity(intent);
        finish();
    }
}
