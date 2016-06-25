package com.google.firebase.codelab.friendlychat.presenter;

import android.content.SharedPreferences;
import android.net.Uri;

import com.google.firebase.codelab.friendlychat.view.MainActivity;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by KhangNVM on 6/23/2016.
 */
public interface IMainPresenter {
    SharedPreferences getSharePreference();

    void FirebaseAnalyticsLogEvent(String sent);

    void InitializeFirebaseAuth();

    void gotoSignIn();

    void getUser();

    String getDisplayNameFirebaseUser();

    Uri getPhotoUrlFirebaseUser();

    void signOut();

    void InitializeFirebaseRemoteConfig();

    void applyRetrievedLengthLimit();

    Long getLongFirebaseRemoteConfig();

    void fetchConfig();

    DatabaseReference getChildFirebaseDatabaseReference(String messagesChild);

    MainActivity getMainView();

    void newGoogleApiClient();

    void sendInvitation(int REQUEST_INVITE);
}
