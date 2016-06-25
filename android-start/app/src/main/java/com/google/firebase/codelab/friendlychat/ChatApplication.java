package com.google.firebase.codelab.friendlychat;

import android.app.Application;
import android.content.Context;

/**
 * Created by Nguyen on 6/25/2016.
 */
public class ChatApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
