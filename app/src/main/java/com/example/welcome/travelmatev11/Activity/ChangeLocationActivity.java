package com.example.welcome.travelmatev11.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.welcome.travelmatev11.R;
import com.example.welcome.travelmatev11.SharedPreference.CustomSharedPreference;
import com.example.welcome.travelmatev11.SharedPreference.Helper;
import com.example.welcome.travelmatev11.SharedPreference.MyPreference;


public class ChangeLocationActivity extends AppCompatActivity {

    private AutoCompleteTextView cityactv;
    private Button change;
    private CustomSharedPreference sharedPreference;
    private String[] cities = {"Dubai", "Venice", "Tokyo", "Seoul", "Singapore", "Beijing", "Kathmandu"};
    private int flag = 0;
    MyPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        setTitle(Helper.MANAGER_CITY);
        preference=new MyPreference(this);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        cityactv = (AutoCompleteTextView) findViewById(R.id.cityAutoCompleteTextView);
        change = (Button) findViewById(R.id.change_location);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,cities);
        cityactv.setAdapter(adapter);
        cityactv.setThreshold(1);

        sharedPreference = new CustomSharedPreference(ChangeLocationActivity.this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
            if (id == android.R.id.home) {
                onBackPressed();
            }else if (id == R.id.action_logIn) {
                Intent intentLogin= new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intentLogin);
                finish();
            }else if (id == R.id.action_logout) {
                preference.setUserName("");
            }

            return super.onOptionsItemSelected(item);
        }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish(); // to simulate "restart" of the activity.

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.event_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if(preference.getUserName().equals("")){
            menu.getItem(1).setVisible(false);
        }else{
            menu.getItem(0).setVisible(false);
        }
        menu.getItem(2).setVisible(false);
        return true;
    }

    public void saveCity(View view) {
        String enteredLocation = cityactv.getText().toString();

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityactv.getWindowToken(), 0);

        if (TextUtils.isEmpty(enteredLocation)) {
            Toast.makeText(ChangeLocationActivity.this, Helper.LOCATION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            for(int i=0; i<cities.length; i++) {
                if (enteredLocation.equals(cities[i])) {
                    flag = 1;
                    sharedPreference.setLocationInPreference(cityactv.getText().toString());
                    Intent intent = new Intent(ChangeLocationActivity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            if(flag == 0)
                Toast.makeText(ChangeLocationActivity.this, Helper.LOCATION_VALIDATION_MESSAGE, Toast.LENGTH_SHORT).show();
            Log.d("flag",flag+"");

        }

    }

    public void goCurrentLocation(View view) {
        sharedPreference.setLocationInPreference("");
        Intent intent = new Intent(ChangeLocationActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
