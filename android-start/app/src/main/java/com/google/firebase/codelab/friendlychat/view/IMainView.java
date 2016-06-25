package com.google.firebase.codelab.friendlychat.view;

/**
 * Created by KhangNVM on 6/23/2016.
 */
public interface IMainView {

    void gotoSignIn();

    void getUser();

    void applyRetrievedLengthLimit();

    MainActivity getContext();
}
