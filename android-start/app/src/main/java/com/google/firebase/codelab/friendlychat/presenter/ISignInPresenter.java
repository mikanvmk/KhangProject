package com.google.firebase.codelab.friendlychat.presenter;

import android.content.Intent;

import com.google.firebase.codelab.friendlychat.view.SignInActivity;

/**
 * Created by KhangNVM on 6/23/2016.
 */
public interface ISignInPresenter {

    void checkSignInResult(Intent data);

    void signIn(int rcSignIn);

    void ConfigGoogleSignIn();

    SignInActivity getSignInView();
}
