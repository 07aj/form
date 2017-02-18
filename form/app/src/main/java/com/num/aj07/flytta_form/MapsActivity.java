package com.num.aj07.flytta_form;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener,GoogleMap.OnMapLongClickListener,GoogleMap.OnMarkerDragListener{

    private GoogleMap map;
    int k=0;
    Double lat,lon;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        b=(Button)findViewById(R.id.button);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

        //map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        //create initial marker

        }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        b.setVisibility(View.INVISIBLE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Switch on Location and drag marker");

        alertDialog.show();
        map.setOnMarkerDragListener(this);
        map.setOnMapLongClickListener(this);
        map.setOnMapClickListener(this);
        CameraPosition INIT =
                new CameraPosition.Builder()
                        .target(new LatLng(19.0222, 72.8666))
                        .zoom(17.5F)
                        .bearing(300F) // orientation
                        .tilt( 50F) // viewing angle
                        .build();

        // use map to move camera into position
        map.moveCamera( CameraUpdateFactory.newCameraPosition(INIT) );
        //map.addMarker( new MarkerOptions()
          //      .position( new LatLng(19.0216, 72.8646) )
            //    .title("Location")
              //  .snippet("First Marker")).showInfoWindow();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    }

    @Override
    public void onMapClick(LatLng latLng) {
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
// TODO Auto-generated method stub
        if(k==0) {
            //create new marker when user long clicks
            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .draggable(true));
            Toast.makeText(getBaseContext(), "lat:" + latLng.latitude + "lon:" + latLng.longitude, Toast.LENGTH_SHORT).show();
            lat=latLng.latitude;
            lon=latLng.longitude;
            b.setVisibility(View.VISIBLE);
            k++;
        }
        }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        // TODO Auto-generated method stub
        LatLng dragPosition = marker.getPosition();
        double dragLat = dragPosition.latitude;
        double dragLong = dragPosition.longitude;
        Log.i("info", "on drag end :" + dragLat + " dragLong :" + dragLong);
        Toast.makeText(getApplicationContext(), "Marker Dragged..!", Toast.LENGTH_SHORT).show();
        Toast.makeText(getBaseContext(), "lat:" +dragLat + "lon:" + dragLong, Toast.LENGTH_SHORT).show();
        lat=dragLat;
        lon=dragLong;
    }
    public void abc(View view)
    {
        String s=Double.toString(lat);
        String s1=Double.toString(lon);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Lat", s);
        editor.putString("Lon", s1);

        editor.commit();
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }
}