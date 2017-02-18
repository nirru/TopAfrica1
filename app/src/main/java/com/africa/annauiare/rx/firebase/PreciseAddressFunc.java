package com.africa.annauiare.rx.firebase;

import android.location.Address;

import java.util.ArrayList;

import rx.functions.Func1;

public class PreciseAddressFunc implements Func1<Address, ArrayList<String>> {
    private double lat ,lng;
    public PreciseAddressFunc(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }
    @Override
    public ArrayList call(Address address) {
        if (address == null) return new ArrayList();
        String detailAddress = "";
        ArrayList<String> addressLines = new ArrayList<>();
        addressLines.add(address.getCountryName());
        addressLines.add(address.getLocality() != null ? address.getLocality() : address.getAdminArea());
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            detailAddress += address.getAddressLine(i) + ' ';
         }
        addressLines.add(detailAddress);
        addressLines.add("" + lat);
        addressLines.add("" + lng);
        addressLines.add(address.getSubLocality()!=null ? address.getSubLocality() : address.getSubAdminArea());
        addressLines.add("" + "Political");
        addressLines.add("" + address.getAdminArea());
        return addressLines;
    }
}
