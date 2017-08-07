package com.pixelall.rxstudy;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

/**
 * Created by lxl on 2017/8/4.
 */

public  class LocationUtil {
    private WeakReference<Context> contextWeakReference;
    private List<String> providerList;
    private String providerString;
    private LocationManager locationManager;
    private int providerPosition=0;
    private OnLocationCallBack onLocationCallBack;
    public LocationUtil(Context context,OnLocationCallBack onLocationCallBack){
        this.onLocationCallBack=onLocationCallBack;
        contextWeakReference=new WeakReference<>(context);
        initLocationData();

    }

    private void initLocationData() {
        locationManager= (LocationManager) contextWeakReference
                .get().getSystemService(Context.LOCATION_SERVICE);
        providerList=locationManager.getAllProviders();

        String bestProvider=locationManager.getBestProvider(new Criteria(),true);
        if (providerList.contains(bestProvider)) {
            providerList.remove(bestProvider);
            providerList.add(0,bestProvider);
        }
        if (providerList!=null&&providerList.size()>0) providerString= LocationManager.NETWORK_PROVIDER;
        initLocation();
    }

    private void initLocation() {
        final Context context=contextWeakReference.get();
        if (context==null) return;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(i);
        }

        int minTime=100;//刷新时间
        float minDistance=1;//刷新距离
        locationManager.requestLocationUpdates(providerString, minTime, minDistance, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (onLocationCallBack!=null) onLocationCallBack.LocationData(location);
//                Bundle b=location.getExtras();
//                //得到纬度
//                double latitude = location.getLatitude();
//                //得到经度
//                double longitude = location.getLongitude();
//
//                Geocoder gc = new Geocoder(context, Locale.getDefault());
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
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(context, "更改定位方式:"+provider, Toast.LENGTH_SHORT).show();
                Log.i("2", "onProviderEnabled: "+provider+"--->"+status);
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i("3", "onProviderEnabled: "+provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i("4", "onProviderDisabled: "+provider);
            }
        });

    }

    public interface OnLocationCallBack{
        void LocationData(Location location);
    }

}
