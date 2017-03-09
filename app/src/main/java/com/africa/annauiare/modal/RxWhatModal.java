package com.africa.annauiare.modal;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by nikk on 9/3/17.
 */

public class RxWhatModal {

    private DataSnapshot businessName;
    private DataSnapshot businessCategory;

    public RxWhatModal(){

    }


    public DataSnapshot getBusinessName() {
        return businessName;
    }

    public void setBusinessName(DataSnapshot businessName) {
        this.businessName = businessName;
    }

    public DataSnapshot getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(DataSnapshot businessCategory) {
        this.businessCategory = businessCategory;
    }


}
