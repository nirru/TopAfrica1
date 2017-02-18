package com.africa.annauiare.rx.firebase.listener;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import rx.Subscriber;

public class RxCompletionListener implements Firebase.CompletionListener {
    private final Subscriber<? super Firebase> subscriber;

    public RxCompletionListener(Subscriber<? super Firebase> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        if (firebaseError != null) {
            subscriber.onError(firebaseError.toException());
            return;
        }

        subscriber.onNext(firebase);
        subscriber.onCompleted();
    }
}