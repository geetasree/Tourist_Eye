package com.example.welcome.travelmatev11.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.welcome.travelmatev11.R;
import com.example.welcome.travelmatev11.SharedPreference.MyPreference;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.welcome.travelmatev11.Activity.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LocationMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<String> arrayDataInfo;
    String url;
    LatLng marker22;
    int value;
    Intent intent;
    MyPreference preference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        preference=new MyPreference(this);
        intent=getIntent();
        value= intent.getIntExtra("INFO",-1);
        Log.d("VaLUE32:",value+"");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(value==4){
            double latitude = intent.getDoubleExtra("latitude",-1);
            double longitude = intent.getDoubleExtra("longitude",-1);
            Log.d("LOCATION",latitude +""+ longitude +"");
            String city = intent.getStringExtra("city");
            value= intent.getIntExtra("INFO",-1);
            Log.d("VaLUE32:",value+"");

            if(latitude>0 && longitude>0) {
                showMyMap(latitude, longitude, "Current Location");
            }
            else
            {
                if (city.equals("Dubai"))
                    showMyMap(25.2048, 55.2708, city);
                else if (city.equals("Venice"))
                    showMyMap(45.4408, 12.3155, city);
                else if (city.equals("Tokyo"))
                    showMyMap(35.6895, 139.6917, city);
                else if (city.equals("Seoul"))
                    showMyMap(37.5665, 126.9780, city);
                else if (city.equals("Singapore"))
                    showMyMap(1.3521, 103.8198, city);
                else if (city.equals("Beijing"))
                    showMyMap(39.9042, 116.4074, city);
                else if (city.equals("Kathmandu"))
                    showMyMap(27.7172, 85.3240, city);
            }
        }
        else {
            BitmapDescriptor bitmapDescriptor
                    = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_AZURE);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(preference.getLatitude(), preference.getLongitude())).icon(bitmapDescriptor)
                    .title("You are here "));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(preference.getLatitude(), preference.getLongitude())));
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );

            ArrayList<String> locationNameMap= StoreData.loctionName;
            ArrayList<Double> locationMap= StoreData.locationData;
            for(int i=0;i<locationMap.size();i=i+2){
                double lat = locationMap.get(i);
                double lng = locationMap.get(i+1);
                marker22 = new LatLng(lat,lng);
                mMap.addMarker(new MarkerOptions().position(marker22).title(locationNameMap.get(i)).snippet(locationNameMap.get(i+1)));
            }
        }
        StoreData.loctionName.clear();
        StoreData.locationData.clear();
    }


    public void showMyMap(double latitude, double longitude, String cityname)
    {
        LatLng location = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in "+cityname));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        if(cityname.equals("Current Location"))
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );
        else
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ) );
    }
}
