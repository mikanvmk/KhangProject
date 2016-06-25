package com.google.firebase.codelab.friendlychat.model;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.codelab.friendlychat.ChatApplication;
import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.presenter.IMainPresenter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KhangNVM on 6/23/2016.
 */
public class MainModel implements IMainModel {

    public MainModel(IMainPresenter presenter) {
        this.presenter = presenter;
    }

    private static final String TAG = "MainModel";
    private IMainPresenter presenter;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    // Firebase instance variables
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    public SharedPreferences getSharePreference() {
        return PreferenceManager.getDefaultSharedPreferences(presenter.getMainView());
    }

    @Override
    public void FirebaseAnalyticsLogEvent(String sent) {
        Bundle payload = new Bundle();
        payload.putString(FirebaseAnalytics.Param.VALUE, sent);
        getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.SHARE,
                payload);
    }

    @Override
    public void InitializeFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            presenter.gotoSignIn();
        } else {
            presenter.getUser();
        }
    }

    @Override
    public String getDisplayNameFirebaseUser() {
        return mFirebaseUser.getDisplayName();
    }
    @Override
    public Uri getPhotoUriFirebaseUser() {
        return mFirebaseUser.getPhotoUrl();
    }

    @Override
    public void signOut() {
        mFirebaseAuth.signOut();
    }

    @Override
    public void InitializeFirebaseRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Define Firebase Remote Config Settings.
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

        // Define default config values. Defaults are used when fetched config values are not
        // available. Eg: if an error occurred fetching values from the server.
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put("friendly_msg_length", 10L);

        // Apply config settings and default values.
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);

        // Fetch remote config.
        fetchConfig();
    }

    @Override
    public Long getLongFirebaseRemoteConfig() {
        return mFirebaseRemoteConfig.getLong("friendly_msg_length");
    }

    private FirebaseAnalytics getFirebaseAnalytics(){
        return FirebaseAnalytics.getInstance(presenter.getMainView());
    }
    @Override
    // Fetch the config to determine the allowed length of messages.
    public void fetchConfig() {
        long cacheExpiration = 3600; // 1 hour in seconds
        // If developer mode is enabled reduce cacheExpiration to 0 so that
        // each fetch goes to the server. This should not be used in release
        // builds.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings()
                .isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Make the fetched config available via
                        // FirebaseRemoteConfig get<type> calls.
                        mFirebaseRemoteConfig.activateFetched();
                        presenter.applyRetrievedLengthLimit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // There has been an error fetching the config
                        Log.w(TAG, "Error fetching config: " +
                                e.getMessage());
                        presenter.applyRetrievedLengthLimit();
                    }
                });
    }

    @Override
    public DatabaseReference getChildFirebaseDatabaseRefernce(String messagesChild) {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void newGoogleApiClient() {
        new GoogleApiClient.Builder(ChatApplication.getContext())
                .enableAutoManage(presenter.getMainView() /* FragmentActivity */, presenter.getMainView() /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    @Override
    public void sendInvitation(int REQUEST_INVITE) {
        Intent intent = new AppInviteInvitation.IntentBuilder(ChatApplication.getContext().getString(R.string.invitation_title))
                .setMessage(ChatApplication.getContext().getString(R.string.invitation_message))
                .setCallToActionText(ChatApplication.getContext().getString(R.string.invitation_cta))
                .build();
        presenter.getMainView().startActivityForResult(intent, REQUEST_INVITE);
    }
}
