package com.africa.annauiare.rx;

import android.widget.TextView;

import java.util.ArrayList;

import rx.functions.Action1;

public class DisplayAddressOnDetailPage implements Action1<ArrayList<String>> {
    private final TextView target_address;

    public DisplayAddressOnDetailPage(TextView target_address) {

        this.target_address = target_address;
    }

    @Override
    public void call(ArrayList<String> s) {
        target_address.setText(s.get(0));
    }
}