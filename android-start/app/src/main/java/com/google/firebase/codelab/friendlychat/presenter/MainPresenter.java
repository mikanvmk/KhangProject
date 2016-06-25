package com.google.firebase.codelab.friendlychat.presenter;

import android.content.SharedPreferences;
import android.net.Uri;

import com.google.firebase.codelab.friendlychat.view.MainActivity;
import com.google.firebase.codelab.friendlychat.model.IMainModel;
import com.google.firebase.codelab.friendlychat.model.MainModel;
import com.google.firebase.codelab.friendlychat.view.IMainView;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by KhangNVM on 6/23/2016.
 */
public class MainPresenter implements IMainPresenter {

    public MainPresenter(IMainView iMainView) {
        this.mainView = iMainView;
        model = new MainModel(this);
    }

    private IMainModel model;

    public MainActivity getMainView() {
        return mainView.getContext();
    }

    @Override
    public void newGoogleApiClient() {
        model.newGoogleApiClient();
    }

    @Override
    public void sendInvitation(int REQUEST_INVITE) {
        model.sendInvitation(REQUEST_INVITE);
    }

    private IMainView mainView;

    @Override
    public SharedPreferences getSharePreference() {
        return model.getSharePreference();
    }

    @Override
    public void FirebaseAnalyticsLogEvent(String sent) {
        model.FirebaseAnalyticsLogEvent(sent);
    }

    @Override
    public void InitializeFirebaseAuth() {
        model.InitializeFirebaseAuth();
    }

    @Override
    public void gotoSignIn() {
        mainView.gotoSignIn();
    }

    @Override
    public void getUser() {
        mainView.getUser();
    }

    @Override
    public String getDisplayNameFirebaseUser() {
        return model.getDisplayNameFirebaseUser();
    }

    @Override
    public Uri getPhotoUrlFirebaseUser() {
        return model.getPhotoUriFirebaseUser();
    }

    @Override
    public void signOut() {
        model.signOut();
    }

    @Override
    public void InitializeFirebaseRemoteConfig() {
        model.InitializeFirebaseRemoteConfig();
    }

    @Override
    public void applyRetrievedLengthLimit() {
        mainView.applyRetrievedLengthLimit();
    }

    @Override
    public Long getLongFirebaseRemoteConfig() {
        return model.getLongFirebaseRemoteConfig();
    }

    @Override
    public void fetchConfig() {
        model.fetchConfig();
    }

    @Override
    public DatabaseReference getChildFirebaseDatabaseReference(String messagesChild) {
        return model.getChildFirebaseDatabaseRefernce(messagesChild);
    }
}
