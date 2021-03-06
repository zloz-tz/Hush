package com.hushproject.hush;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

class GeocoderService {

    private String address = "";

    GeocoderService (){}

    String getAddressFromCoordinates (double lat, double lng, Context context){
        Geocoder geo = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geo.getFromLocation(lat, lng,1);
            address = addresses.get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }
}
