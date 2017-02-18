package com.africa.annauiare.rx.firebase;

import android.support.annotation.NonNull;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

import com.africa.annauiare.rx.firebase.exception.RxFirebaseException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * Created by Nikk on 02/10/2016.
 */
public class RxFirebaseAuth {

    @NonNull
    public static Observable<AuthResult> signInAnonymously(@NonNull final FirebaseAuth firebaseAuth) {
        return Observable.create(new Observable.OnSubscribe<AuthResult>() {
            @Override
            public void call(final Subscriber<? super AuthResult> subscriber) {
                RxHandler.assignOnTask(subscriber, firebaseAuth.signInAnonymously());
            }
        });
    }

    @NonNull
    public static Observable<AuthResult> signInWithEmailAndPassword(@NonNull final FirebaseAuth firebaseAuth,
                                                                    @NonNull final String email,
                                                                    @NonNull final String password) {
        return Observable.create(new Observable.OnSubscribe<AuthResult>() {
            @Override
            public void call(final Subscriber<? super AuthResult> subscriber) {
                RxHandler.assignOnTask(subscriber, firebaseAuth.signInWithEmailAndPassword(email, password));
            }
        });
    }

    @NonNull
    public static Observable<AuthResult> signInWithCredential(@NonNull final FirebaseAuth firebaseAuth,
                                                              @NonNull final AuthCredential credential) {
        return Observable.create(new Observable.OnSubscribe<AuthResult>() {
            @Override
            public void call(final Subscriber<? super AuthResult> subscriber) {
                RxHandler.assignOnTask(subscriber, firebaseAuth.signInWithCredential(credential));
            }
        });
    }

    @NonNull
    public static Observable<AuthData> signInWithAuthToken(@NonNull final Firebase firebaseAuth,
                                                            @NonNull final String oauthToken,@NonNull final String provider) {
        return Observable.create(new Observable.OnSubscribe<AuthData>() {
            @Override
            public void call(final Subscriber<? super AuthData> subscriber) {
                final Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(@NonNull AuthData authData) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(authData);
                        }
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError e) {
                        if (!subscriber.isUnsubscribed()) {
                             subscriber.onError(RxFirebaseException.from(e));
                        }
                    }
                };
                firebaseAuth.authWithOAuthToken(provider,oauthToken,authResultHandler);

                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {

                    }
                }));
            }
        });
    }

    @NonNull
    public static Observable<AuthResult> signInWithCustomToken(@NonNull final FirebaseAuth firebaseAuth,
                                                               @NonNull final String token) {
        return Observable.create(new Observable.OnSubscribe<AuthResult>() {
            @Override
            public void call(final Subscriber<? super AuthResult> subscriber) {
                RxHandler.assignOnTask(subscriber, firebaseAuth.signInWithCustomToken(token));
            }
        });
    }

    @NonNull
    public static Observable<AuthResult> createUserWithEmailAndPassword(@NonNull final FirebaseAuth firebaseAuth,
                                                                        @NonNull final String email,
                                                                        @NonNull final String password) {
        return Observable.create(new Observable.OnSubscribe<AuthResult>() {
            @Override
            public void call(final Subscriber<? super AuthResult> subscriber) {
                RxHandler.assignOnTask(subscriber, firebaseAuth.createUserWithEmailAndPassword(email, password));
            }
        });
    }

    @NonNull
    public static Observable<ProviderQueryResult> fetchProvidersForEmail(@NonNull final FirebaseAuth firebaseAuth,
                                                                         @NonNull final String email) {
        return Observable.create(new Observable.OnSubscribe<ProviderQueryResult>() {
            @Override
            public void call(final Subscriber<? super ProviderQueryResult> subscriber) {
                RxHandler.assignOnTask(subscriber, firebaseAuth.fetchProvidersForEmail(email));
            }
        });
    }

    @NonNull
    public static Observable<Void> sendPasswordResetEmail(@NonNull final FirebaseAuth firebaseAuth,
                                                          @NonNull final String email) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                RxHandler.assignOnTask(subscriber, firebaseAuth.sendPasswordResetEmail(email));
            }
        });
    }

    @NonNull
    public static Observable<FirebaseUser> observeAuthState(@NonNull final FirebaseAuth firebaseAuth) {

        return Observable.create(new Observable.OnSubscribe<FirebaseUser>() {
            @Override
            public void call(final Subscriber<? super FirebaseUser> subscriber) {
                final FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(firebaseAuth.getCurrentUser());
                        }
                    }
                };
                firebaseAuth.addAuthStateListener(authStateListener);

                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        firebaseAuth.removeAuthStateListener(authStateListener);
                    }
                }));
            }
        });
    }
}

