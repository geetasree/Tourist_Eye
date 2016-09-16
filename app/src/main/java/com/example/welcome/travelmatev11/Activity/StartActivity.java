package com.example.welcome.travelmatev11.Activity;

import android.Manifest;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.welcome.travelmatev11.Adapters.RecyclerViewAdapter;
import com.example.welcome.travelmatev11.DBCRUD.EventManager;
import com.example.welcome.travelmatev11.Model.WeatherObject;

import com.example.welcome.travelmatev11.R;
import com.example.welcome.travelmatev11.Service.LocationService;
import com.example.welcome.travelmatev11.SharedPreference.CustomSharedPreference;
import com.example.welcome.travelmatev11.SharedPreference.Helper;
import com.example.welcome.travelmatev11.SharedPreference.MyPreference;
import com.example.welcome.travelmatev11.json.FiveWeathers;
import com.example.welcome.travelmatev11.json.Forecast;
import com.example.welcome.travelmatev11.json.LocationMapObject;
import com.github.pavlospt.CircleView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MyPreference preference;
    private RecyclerView recyclerView;
    private TextView cityCountry;
    private TextView currentDate;
    private ImageView weatherImage;
    private CircleView circleTitle;
    private TextView windResult;
    private TextView humidityResult;
    private LocationManager locationManager;
    private final int REQUEST_LOCATION = 200;
    private Location location;
    private double longitude;
    private double latitude;
    private String apiUrl;
    private String storedCityName;
    private LocationMapObject locationMapObject;
    private RecyclerViewAdapter recyclerViewAdapter;
    private String isLocationSaved;
    private CustomSharedPreference sharedPreference;
    String url;
    EventManager  eManager;
    NavigationView navigationView;
    View header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        initializement();
        Intent service=new Intent(this, LocationService.class);
        startService(service);

        userLoginCheck();

 //       Toast.makeText(StartActivity.this, "LOGIN AS:" + preference.getUserName(), Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(StartActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            if (isLocationSaved.equals("")) {
                // make API call with longitude and latitude
                apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat=" + preference.getLatitude() + "&lon=" + preference.getLongitude() + "&APPID=caa1dfd44d096f6b4d9fc03c9d0fd285&units=metric";
                makeJsonObject(apiUrl);

            } else {
                // make API call with city name
                storedCityName = sharedPreference.getLocationInPreference();
                if (storedCityName != null && !storedCityName.equals("")) {
                    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + storedCityName + "&APPID=caa1dfd44d096f6b4d9fc03c9d0fd285&units=metric";
                    makeJsonObject(url);
                }
            }
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(StartActivity.this, 5);
        recyclerView = (RecyclerView) findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void userLoginCheck() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header= ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        TextView headUsrNameTxt= (TextView) header.findViewById(R.id.txtUsrNameHead);
        TextView headUsrEmailTxt= (TextView)header.findViewById(R.id.txtUsrEmailHead);
        if(preference.getUserName().equals("")){
            headUsrNameTxt.setText("Hello");
        }else{
            headUsrNameTxt.setText(preference.getUserName());
            ImageView userImage= (ImageView)header.findViewById(R.id.imageView);
            File imgFile = new  File(preference.getPICPATH());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                userImage.setImageBitmap(myBitmap);
            }
        }
        headUsrEmailTxt.setText("welcome to Tourist Eye");
    }

    private void initializement() {
        cityCountry = (TextView) findViewById(R.id.city_country);
        currentDate = (TextView) findViewById(R.id.current_date);
        weatherImage = (ImageView) findViewById(R.id.weather_icon);
        circleTitle = (CircleView) findViewById(R.id.weather_result);
        windResult = (TextView) findViewById(R.id.wind_result);
        humidityResult = (TextView) findViewById(R.id.humidity_result);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        sharedPreference = new CustomSharedPreference(StartActivity.this);
        isLocationSaved = sharedPreference.getLocationInPreference();
        preference=new MyPreference(this);
        latitude=preference.getLatitude();
        longitude=preference.getLongitude();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        }menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logIn) {
            Intent intentLogin= new Intent(getApplicationContext(), LogInActivity.class);
            startActivity(intentLogin);
            finish();
        }else if (id == R.id.action_logout) {
            preference.setUserName("");
            reStart();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_maps) {
            Intent intent = new Intent(StartActivity.this, LocationMapsActivity.class);
            Log.d(" getLatitude():", String.valueOf(preference.getLatitude()));
            Log.d(" getLongitude():",  preference.getLongitude()+"");
            intent.putExtra("latitude", preference.getLatitude()); //ekhane Latitude Longitude boshate hobe service theke niye
            intent.putExtra("longitude", preference.getLongitude());
            intent.putExtra("city", storedCityName);
            intent.putExtra("INFO", 4);
            startActivity(intent);
            return true;
        }else if (id == R.id.nav_changeCity) {
            Intent intent = new Intent(StartActivity.this, ChangeLocationActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else if (id == R.id.nav_logout) {
//            Double latitude= preference.getLatitude();
//            Double longitude= preference.getLongitude();
//            Toast.makeText(StartActivity.this, "latitude: "+ latitude, Toast.LENGTH_SHORT).show();
//            Toast.makeText(StartActivity.this, "longitude: "+ longitude, Toast.LENGTH_SHORT).show();
             preference.setUserName("");
            reStart();
            //Toast.makeText(StartActivity.this, "loginStatus: "+ preference.getUserName(), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_hospital) {
            url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude + "&radius=500&type=hospital&name=hospital&key=AIzaSyAdw3Th3y1dO5GbO-6fCdha_31Xw-69Ce4";
            setNearBy();
            ArrayList<Double> locationMap= StoreData.locationData;
            if(locationMap.size()==0){
                Toast.makeText(StartActivity.this,"Service Not Available,Sorry Please try again!", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(this, LocationMapsActivity.class);
                intent.putExtra("INFO", 1);
                startActivity(intent);
            }
        } else if (id == R.id.nav_hotel) {
            url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude + "&radius=500&type=hotel&name=hotel&key=AIzaSyAdw3Th3y1dO5GbO-6fCdha_31Xw-69Ce4";
//            url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + preference.getLatitude() + "," + preference.getLongitude() + "&radius=500&type=hotel&name=hotel&key=AIzaSyAdw3Th3y1dO5GbO-6fCdha_31Xw-69Ce4";
            setNearBy();
            ArrayList<Double> locationMap= StoreData.locationData;
            if(locationMap.size()==0){
                Toast.makeText(StartActivity.this,"Service Not Available,Sorry Please try again!", Toast.LENGTH_SHORT).show();
            }else {
             //   Toast.makeText(StartActivity.this, "Size1: " + locationNameMap.toString(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(StartActivity.this, "Location Size: " + locationMap.toString(), Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(this,LocationMapsActivity.class);
                intent.putExtra("INFO",2);
                startActivity(intent);
            }
        } else if (id == R.id.nav_atm) {
            url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude + "&radius=500&type=atm&name=atm&key=AIzaSyAdw3Th3y1dO5GbO-6fCdha_31Xw-69Ce4";
            setNearBy();
            ArrayList<Double> locationMap= StoreData.locationData;
            if(locationMap.size()==0){
                Toast.makeText(StartActivity.this,"Service Not Available,Sorry Please try again!", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(this, LocationMapsActivity.class);
                intent.putExtra("INFO", 3);
                startActivity(intent);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void reStart() {
        finish();
        startActivity(getIntent());
    }

    private String getTodayDateInStringFormat(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
        return df.format(c.getTime());
    }

    private String convertTimeToDay(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSSS", Locale.getDefault());
        String days = "";
        try {
            Date date = format.parse(time);
            System.out.println("Our time " + date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            days = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
            System.out.println("Our time " + days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    private void fiveDaysApiJsonObjectCall(String city){
        String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q="+city+ "&APPID=caa1dfd44d096f6b4d9fc03c9d0fd285&units=metric";
        final List<WeatherObject> daysOfTheWeek = new ArrayList<WeatherObject>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Forecast forecast = gson.fromJson(response, Forecast.class);
                if (null == forecast) {
                    Toast.makeText(getApplicationContext(), "Nothing was returned", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "Response Good 2", Toast.LENGTH_LONG).show();
                    int[] everyday = new int[]{0,0,0,0,0,0,0};
                    List<FiveWeathers> weatherInfo = forecast.getList();
                    for(int i = 0; i < weatherInfo.size(); i++){
                        String time = weatherInfo.get(i).getDt_txt();
                        String shortDay = convertTimeToDay(time);
                        String temp = weatherInfo.get(i).getMain().getTemp();
                        String tempMin = weatherInfo.get(i).getMain().getTemp_min();
                        if(convertTimeToDay(time).equals("Mon") && everyday[0] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[0] = 1;
                        }
                        if(convertTimeToDay(time).equals("Tue") && everyday[1] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[1] = 1;
                        }
                        if(convertTimeToDay(time).equals("Wed") && everyday[2] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[2] = 1;
                        }
                        if(convertTimeToDay(time).equals("Thu") && everyday[3] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[3] = 1;
                        }
                        if(convertTimeToDay(time).equals("Fri") && everyday[4] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[4] = 1;
                        }
                        if(convertTimeToDay(time).equals("Sat") && everyday[5] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[5] = 1;
                        }
                        if(convertTimeToDay(time).equals("Sun") && everyday[6] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[6] = 1;
                        }
                        recyclerViewAdapter = new RecyclerViewAdapter(StartActivity.this, daysOfTheWeek);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void makeJsonObject(final String apiUrl){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                locationMapObject = gson.fromJson(response, LocationMapObject.class);
                if (null == locationMapObject) {
                    Toast.makeText(getApplicationContext(), "Nothing was returned", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "Response Good 1", Toast.LENGTH_LONG).show();
                    String city = locationMapObject.getName() + ", " + locationMapObject.getSys().getCountry();
                    String todayDate = getTodayDateInStringFormat();
                    Long tempVal = Math.round(Math.floor(Double.parseDouble(locationMapObject.getMain().getTemp())));
                    String weatherTemp = String.valueOf(tempVal) + "°";
                    String weatherDescription = Helper.capitalizeFirstLetter(locationMapObject.getWeather().get(0).getDescription());
                    String windSpeed = locationMapObject.getWind().getSpeed();
                    String humidityValue = locationMapObject.getMain().getHumudity();

                    // populate View data
                    cityCountry.setText(city);
                    currentDate.setText(todayDate);
                    circleTitle.setTitleText(weatherTemp.toString());
                    circleTitle.setSubtitleText(weatherDescription.toString());
                    windResult.setText(windSpeed + " km/h");
                    humidityResult.setText(humidityValue + " %");
                    Log.e("Name",locationMapObject.getName());
                   fiveDaysApiJsonObjectCall(locationMapObject.getName());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void goTo(View view) {
        if(preference.getUserName().equals("")){
            AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            dialogBuilder.setMessage("You are not signed in to create event, Will you sign in?");
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent intentLogin= new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intentLogin);
                    finish();
                }
            });
            AlertDialog b = dialogBuilder.create();
            b.show();
        }
        else{
            Intent intentEvent= new Intent(getApplicationContext(), EventListActivity.class);
            startActivity(intentEvent);
            finish();
        }
    }

    public void setNearBy(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String vicinity = jsonObject.getString("vicinity");
                        JSONObject geometryJsonObject  = jsonObject.getJSONObject("geometry");
                        JSONObject locationJsonObject  = geometryJsonObject.getJSONObject("location");
                        Double latitude= locationJsonObject.getDouble("lat");
                        Double longitude= locationJsonObject.getDouble("lng");
                        StoreData.loctionName.add(name);
                        StoreData.loctionName.add(vicinity);
                        StoreData.locationData.add(latitude);
                        StoreData.locationData.add(longitude);
                        Log.d("TNaME: ",name);
                        Log.d("TVicinity: ",vicinity);
                        Log.d("TLatitude: ",latitude+"");
                        Log.d("TLongitude: ",longitude+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                }
            }
        });
        AppController.getInstance().addToRequestQueue(request);
        //   Log.d("TNaME: ", "==> Exicuted ...");
    }
}
