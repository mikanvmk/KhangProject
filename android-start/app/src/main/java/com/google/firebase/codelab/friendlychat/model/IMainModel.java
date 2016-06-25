package com.google.firebase.codelab.friendlychat.model;

import android.content.SharedPreferences;
import android.net.Uri;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by KhangNVM on 6/23/2016.
 */
public interface IMainModel {
    SharedPreferences getSharePreference();

    void FirebaseAnalyticsLogEvent(String sent);

    void InitializeFirebaseAuth();

    String getDisplayNameFirebaseUser();

    Uri getPhotoUriFirebaseUser();

    void signOut();

    void InitializeFirebaseRemoteConfig();

    Long getLongFirebaseRemoteConfig();

    void fetchConfig();

    DatabaseReference getChildFirebaseDatabaseRefernce(String messagesChild);

    void newGoogleApiClient();

    void sendInvitation(int REQUEST_INVITE);
}
