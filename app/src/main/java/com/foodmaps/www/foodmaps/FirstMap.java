package com.foodmaps.www.foodmaps;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstMap extends FragmentActivity implements OnMapReadyCallback, SensorEventListener {

    private GoogleMap mMap;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float last_x, last_y, last_z;
    long lastUpdate = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    //setup accelerometer

        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        //sensormanager gets registered to listen for the accelerometer and watching if it moves how far and where


    }

    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if (Math.abs(curTime - lastUpdate) > 2000) {
                SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
                String currentDateTime = date.format(new Date());

                lastUpdate = curTime;


                if (Math.abs(last_x - x) > 10)
                {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(37.23062, -80.42178))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                            .title("The x axis has moved on" + currentDateTime));

                }
                if (Math.abs(last_y - y) > 10)
                {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(37.26062, -80.42188))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .title("The y axis has moved on" + currentDateTime));

                }

                if (Math.abs(last_z - z) > 10)
                {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(37.24062, -80.42378))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .title("The z axis has moved on" + currentDateTime));

                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy){



    }
//private void setUpMapIfNeeded() {
  //  if (mMap == null) {
    //    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
      //          .getMap();
        //if (mMap != null) {
          //  setUpMap();
        //}

    //}
//}


 private void setUpMap(){
    mMap.addMarker(new MarkerOptions().position(new LatLng(37.229, -80.424)).title("Virginia Tech"));
//later change position to your location
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.229, -80.424), 14.9f));

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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
