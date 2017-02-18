package com.africa.annauiare.rx.firebase.exception;

import com.firebase.client.FirebaseError;

/**
 * Created by ericbasendra on 07/10/16.
 */

public class RxFirebaseException extends RuntimeException{

    public final FirebaseError error;

    private RxFirebaseException(FirebaseError error) {
        super(error.getMessage(), error.toException());
        this.error = error;
    }

    public static RxFirebaseException from(FirebaseError error) {
        return new RxFirebaseException(error);
    }
}
