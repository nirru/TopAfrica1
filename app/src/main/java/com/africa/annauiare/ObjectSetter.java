package com.africa.annauiare;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by nikk on 4/3/17.
 */
public class ObjectSetter {
    private static ObjectSetter ourInstance = new ObjectSetter();
    private ArrayList<com.google.firebase.database.DataSnapshot> dataSnapshots;
    private DataSnapshot dataSnapshot;
    private String search;

    public static ObjectSetter getInstance() {
        return ourInstance;
    }

    private ObjectSetter() {
    }

    public ArrayList<DataSnapshot> getDataSnapshots() {
        return dataSnapshots;
    }

    public void setDataSnapshots(ArrayList<DataSnapshot> dataSnapshots) {
        this.dataSnapshots = dataSnapshots;
    }


    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public DataSnapshot getDataSnapshot() {
        return dataSnapshot;
    }

    public void setDataSnapshot(DataSnapshot dataSnapshot) {
        this.dataSnapshot = dataSnapshot;
    }
}
