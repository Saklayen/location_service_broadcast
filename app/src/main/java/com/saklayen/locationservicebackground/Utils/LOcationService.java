package com.saklayen.locationservicebackground.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

public class LOcationService extends Service {

    TimerTask timerTask;
    Timer timer;
    LocationManager locationManager;
    String latitude, longitude;

    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "MY_LOC_BROAD_CAST";
    Intent intent;
    int counter = 0;

    private static final int REQUEST_LOCATION = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
        timer = new Timer();
        timerTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                Log.e("Log", "Service Running");

                //.........location new.....//
                locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


                //GPS is already On then

                getLocation();

                //new location end.........//


            }
        };
        timer.schedule(timerTask, 1000, 1000);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {
        counter++;
        Log.e("get location", "getLocation: "+"getLocationCalled" );
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        android.location.Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        android.location.Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        android.location.Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        if (LocationGps !=null)
        {
            double lat=LocationGps.getLatitude();
            double longi=LocationGps.getLongitude();
            latitude=String.valueOf(lat);
            longitude=String.valueOf(longi);

            intent.putExtra("latitude", "(+"+counter+"+)"+latitude);
            intent.putExtra("longitude", "(+"+counter+"+)"+longitude);
            sendBroadcast(intent);

            Log.e("Location", "getLocation: +"+"Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            //Toast.makeText(getApplicationContext(),"Lat : "+latitude+" Long: "+longitude, Toast.LENGTH_LONG).show();
        }
        else if (LocationNetwork !=null)
        {
            double lat=LocationNetwork.getLatitude();
            double longi=LocationNetwork.getLongitude();

            latitude=String.valueOf(lat);
            longitude=String.valueOf(longi);

            intent.putExtra("latitude", "(+"+counter+"+)"+latitude);
            intent.putExtra("longitude", "(+"+counter+"+)"+longitude);
            sendBroadcast(intent);

            Log.e("Loc", "getLocation: "+"Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            //Toast.makeText(getApplicationContext(),"Lat : "+latitude+" Long: "+longitude, Toast.LENGTH_LONG).show();
        }
        else if (LocationPassive !=null)
        {
            double lat=LocationPassive.getLatitude();
            double longi=LocationPassive.getLongitude();

            latitude=String.valueOf(lat);
            longitude=String.valueOf(longi);

            intent.putExtra("latitude", "(+"+counter+"+)"+latitude);
            intent.putExtra("longitude", "(+"+counter+"+)"+longitude);
            sendBroadcast(intent);

            Log.e("Loc", "getLocation: "+"Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            //Toast.makeText(getApplicationContext(),"Lat : "+latitude+" Long: "+longitude, Toast.LENGTH_LONG).show();
        }
        else
        {
            //Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            Log.e("failed", "getLocation:  cant take location " );
        }

    }
}
