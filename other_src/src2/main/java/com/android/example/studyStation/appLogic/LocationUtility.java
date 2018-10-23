package com.android.example.studyStation.appLogic;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by AmmarRabie on 22/09/2017.
 */

public class LocationUtility {
    private LocationUtility() {
    }

    /**
     * this function measure the distance between tow points in a map, notice that it returns the line length
     * between these points, it is useful to use in calculating the distance between object and other in a map
     * and it is not recommended in  get the path or the way to choose to go from on point to other
     * @return the distance in millimeters (mm) between 2 points on map (specified by lat,long)
     */
    public static long distanceBetween_mm(double latitude_1, double longitude_1, double latitude_2, double longitude_2) {
        float[] results = new float[1];
        android.location.Location.distanceBetween(latitude_1, longitude_1, latitude_2, longitude_2, results);
        Log.i("LocationUtility", "distanceBetween_mm: the result is " + results[0]);
        return (long) (results[0] * 1000);
    }


    public interface onGetLastLocationListener {
        void onGetLastLocation(double latitude, double longitude);
    }

    synchronized public static boolean getUserLocation(Activity activity, final onGetLastLocationListener listener) {
        FusedLocationProviderClient mFusedLocationClient;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity.getApplicationContext());

        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;

        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            listener.onGetLastLocation(location.getLatitude(), location.getLongitude());
                        }
                    }
                });

        return true;
    }
}
