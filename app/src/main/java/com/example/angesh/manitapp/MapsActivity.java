package com.example.angesh.manitapp;

import android.content.Context;
import android.location.Criteria;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.LocationListener;
import android.location.Location;
import android.location.LocationManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        provider=locationManager.getBestProvider(new Criteria(),false);
        Location location=locationManager.getLastKnownLocation(provider);

        if(location!=null){
            onLocationChanged(location);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("Your Location"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 15));
    }


    @Override
    protected void onResume(){
        super.onResume();
        setUpMapIfNeeded();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }
    @Override
    protected void onPause(){
        super.onPause();
        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(Location location){
        Double lat=location.getLatitude();
        Double lng=location.getLongitude();

    }
    @Override
    public void onStatusChanged(String provider,int status,Bundle extras){


    }
    @Override
    public void onProviderEnabled(String provider){


    }
    @Override
    public void onProviderDisabled(String provider){


    }
}
