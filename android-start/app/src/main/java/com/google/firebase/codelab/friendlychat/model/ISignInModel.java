package com.google.firebase.codelab.friendlychat.model;

import android.content.Intent;

/**
 * Created by KhangNVM on 6/23/2016.
 */
public interface ISignInModel {

    void checkSignInResult(Intent data);

    void signIn(int rcSignIn);

    void ConfigGoogleSignIn();
}
