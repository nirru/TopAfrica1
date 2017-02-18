package com.africa.annauiare.modal;

import com.firebase.client.Firebase;

/**
 * Created by ericbasendra on 30/10/16.
 */

public class MyFirebase {


    private Firebase firebase_one;
    private Firebase firebase_two;
    private Firebase firebase_three;

    public MyFirebase(Firebase firebase_one,Firebase firebase_two,Firebase firebase_three){
        this.firebase_one = firebase_one;
        this.firebase_two = firebase_two;
        this.firebase_three = firebase_three;
    }


    public Firebase getFirebase_three() {
        return firebase_three;
    }

    public void setFirebase_three(Firebase firebase_three) {
        this.firebase_three = firebase_three;
    }

    public Firebase getFirebase_two() {
        return firebase_two;
    }

    public void setFirebase_two(Firebase firebase_two) {
        this.firebase_two = firebase_two;
    }
    public Firebase getFirebase_one() {
        return firebase_one;
    }

    public void setFirebase_one(Firebase firebase_one) {
        this.firebase_one = firebase_one;
    }

}
