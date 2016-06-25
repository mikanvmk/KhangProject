package com.google.firebase.codelab.friendlychat.model;

import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by KhangNVM on 6/23/2016.
 */
public class SignInModel implements ISignInModel {

    private static final String TAG = "SignInModel";

    @Override
    public void checkSignInResult() {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        } else {
            // Google Sign In failed
            Log.e(TAG, "Google Sign In failed.");
        }
    }
}
