package com.pixelall.rxstudy;

import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GnssMeasurement;
import android.location.GnssMeasurementsEvent;
import android.location.GnssNavigationMessage;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by lxl on 2017/8/4.
 */

public class LocationActivity extends Activity {
    static String provider;
    private TextView locationString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        locationString= (TextView) findViewById(R.id.location);
        new LocationUtil(this, new LocationUtil.OnLocationCallBack() {
            @Override
            public void LocationData(Location location) {
                Bundle b=location.getExtras();
//得到纬度
                double latitude = location.getLatitude();
//得到经度
                double longitude = location.getLongitude();
                Geocoder gc = new Geocoder(LocationActivity.this, Locale.getDefault());
                List<Address> locationList = null;
                try {
                    locationList = gc.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (locationList==null||locationList.size()==0) return;
                Address address = locationList.get(0);//得到Address实例
//Log.i(TAG, "address =" + address);
                String countryName = address.getCountryName();//得到国家名称，比如：中国
                Log.i("1--1", "countryName = " + countryName);
                String locality = address.getLocality();//得到城市名称，比如：北京市
                Log.i("1--1", "locality = " + locality);
                locationString.setText(address.getCountryName()+"->"+address.getLocality()+"->"+address.getAddressLine(0));
                for (int i = 0; address.getAddressLine(i) != null; i++) {
                    String addressLine = address.getAddressLine(i);//得到周边信息，包括街道等，i=0，得到街道名称
                    Log.i("1--1", "addressLine = " + addressLine);
                }
            }
        });
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        provider=locationManager.getBestProvider(new Criteria(),true);
////        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
////            provider = LocationManager.NETWORK_PROVIDER;
////        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
////            provider = LocationManager.GPS_PROVIDER;
////        }else {
////            provider=LocationManager.PASSIVE_PROVIDER;
////        }
//
//
//        GnssMeasurementsEvent gnssMeasurementsEvent;
//        GnssMeasurement gnssMeasurement;
//        GnssNavigationMessage gs;
//        GnssStatus status;
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
//            Intent i = new Intent();
//            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(i);
//            return;
//        }
//
//        Log.i("地址状态",provider);
//        locationManager.requestLocationUpdates(provider, 100, 1, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                Bundle b=location.getExtras();
//                //得到纬度
//                double latitude = location.getLatitude();
//                //得到经度
//                double longitude = location.getLongitude();
//
//                Geocoder gc = new Geocoder(LocationActivity.this, Locale.getDefault());
//                List<Address> locationList = null;
//                try {
//                    locationList = gc.getFromLocation(latitude, longitude, 1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (locationList==null||locationList.size()==0) return;
//                Address address = locationList.get(0);//得到Address实例
//                //Log.i(TAG, "address =" + address);
//                String countryName = address.getCountryName();//得到国家名称，比如：中国
//                Log.i("1--1", "countryName = " + countryName);
//                String locality = address.getLocality();//得到城市名称，比如：北京市
//                Log.i("1--1", "locality = " + locality);
//                locationString.setText(address.getCountryName()+"->"+address.getLocality()+"->"+address.getAddressLine(0));
//                for (int i = 0; address.getAddressLine(i) != null; i++) {
//                    String addressLine = address.getAddressLine(i);//得到周边信息，包括街道等，i=0，得到街道名称
//                    Log.i("1--1", "addressLine = " + addressLine);
//                }
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//                Log.i("2", "onProviderEnabled: "+provider+"--->"+status);
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//                Log.i("3", "onProviderEnabled: "+provider);
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//                Log.i("4", "onProviderDisabled: "+provider);
//            }
//        });



    }
}
