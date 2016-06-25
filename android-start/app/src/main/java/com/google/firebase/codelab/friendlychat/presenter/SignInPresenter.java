package com.google.firebase.codelab.friendlychat.presenter;

import com.google.firebase.codelab.friendlychat.model.ISignInModel;
import com.google.firebase.codelab.friendlychat.model.SignInModel;
import com.google.firebase.codelab.friendlychat.view.ISignInView;

/**
 * Created by KhangNVM on 6/23/2016.
 */
public class SignInPresenter implements ISignInPresenter {

    public SignInPresenter(ISignInView signInView) {
        this.signInView = signInView;
        model = new SignInModel();
    }

    private ISignInView signInView;
    private ISignInModel model;

    @Override
    public void checkSignInResult() {
        model.checkSignInResult();
    }
}
