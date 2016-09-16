package com.example.welcome.travelmatev11.Service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.welcome.travelmatev11.SharedPreference.MyPreference;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.location.LocationListener{
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;

    MyPreference preference;
    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preference=new MyPreference(this);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        googleApiClient.connect();
       /* try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
//        Toast.makeText(LocationService.this, " Started ,Keep moving", Toast.LENGTH_LONG).show();

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        googleApiClient.disconnect();
     //   Toast.makeText(LocationService.this, "Service now disconnected", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest= LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(600000);

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,(com.google.android.gms.location.LocationListener)this);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Double longitude=location.getLongitude();
        Double latitude=location.getLatitude();
        preference.saveLocationData(latitude,longitude);
  //     Toast.makeText(this,"Longitude:: "+ longitude,Toast.LENGTH_LONG ).show();
    //  Toast.makeText(this,"Latitude:: "+ latitude,Toast.LENGTH_LONG ).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
