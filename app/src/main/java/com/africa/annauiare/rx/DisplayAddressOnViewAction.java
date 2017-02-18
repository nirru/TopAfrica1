package com.africa.annauiare.rx;

import android.widget.EditText;

import java.util.ArrayList;

import rx.functions.Action1;

public class DisplayAddressOnViewAction implements Action1<ArrayList<String>> {
    private final EditText target_country,text_state,target_city, target_district,target_neighbour,target_address,target_cordinate;

    public DisplayAddressOnViewAction(EditText target_country, EditText text_state,EditText target_city, EditText district, EditText neighbour, EditText target_address, EditText target_cordinate) {
        this.target_country = target_country;
        this.text_state = text_state;
        this.target_city = target_city;
        this.target_address = target_address;
        this.target_cordinate  = target_cordinate;
        this.target_district = district;
        this.target_neighbour = neighbour;
    }

    @Override
    public void call(ArrayList<String> s) {
        target_country.setText(s.get(0));
        target_city.setText(s.get(1));
        target_address.setText(s.get(2));
        target_cordinate.setText(s.get(3) + "," + s.get(4));
        target_district.setText(s.get(5) !=null ? s.get(5) : "dakar");
        target_neighbour.setText(s.get(6) !=null ? s.get(6) : "political");
        text_state.setText(s.get(7) !=null ? s.get(7) : "Dakar");
    }
}