package com.africa.annauiare.modal;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by nikk on 9/3/17.
 */

public class RxWhereModal {

    private DataSnapshot citySnapshot;
    private DataSnapshot munciplaitySnapshot;
    private DataSnapshot neighbourHoodSnapshot;

    public DataSnapshot getCitySnapshot() {
        return citySnapshot;
    }

    public void setCitySnapshot(DataSnapshot citySnapshot) {
        this.citySnapshot = citySnapshot;
    }

    public DataSnapshot getMunciplaitySnapshot() {
        return munciplaitySnapshot;
    }

    public void setMunciplaitySnapshot(DataSnapshot munciplaitySnapshot) {
        this.munciplaitySnapshot = munciplaitySnapshot;
    }

    public DataSnapshot getNeighbourHoodSnapshot() {
        return neighbourHoodSnapshot;
    }

    public void setNeighbourHoodSnapshot(DataSnapshot neighbourHoodSnapshot) {
        this.neighbourHoodSnapshot = neighbourHoodSnapshot;
    }
}
