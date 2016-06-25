package com.google.firebase.codelab.friendlychat.presenter;

import android.content.Intent;

import com.google.firebase.codelab.friendlychat.model.ISignInModel;
import com.google.firebase.codelab.friendlychat.model.SignInModel;
import com.google.firebase.codelab.friendlychat.view.ISignInView;
import com.google.firebase.codelab.friendlychat.view.SignInActivity;

/**
 * Created by KhangNVM on 6/23/2016.
 */
public class SignInPresenter implements ISignInPresenter {

    public SignInPresenter(ISignInView signInView) {
        this.signInView = signInView;
        model = new SignInModel(this);
    }

    private ISignInView signInView;
    private ISignInModel model;

    @Override
    public void checkSignInResult(Intent data) {
        model.checkSignInResult(data);
    }

    @Override
    public void signIn(int rcSignIn) {
        model.signIn(rcSignIn);
    }

    @Override
    public void ConfigGoogleSignIn() {
        model.ConfigGoogleSignIn();
    }

    @Override
    public SignInActivity getSignInView(){
        return signInView.getSignInView();
    }
}
