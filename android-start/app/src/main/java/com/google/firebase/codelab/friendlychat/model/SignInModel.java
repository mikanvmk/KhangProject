package com.google.firebase.codelab.friendlychat.model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.codelab.friendlychat.ChatApplication;
import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.presenter.ISignInPresenter;
import com.google.firebase.codelab.friendlychat.view.MainActivity;

/**
 * Created by KhangNVM on 6/23/2016.
 */
public class SignInModel implements ISignInModel {

    private static final String TAG = "SignInModel";
    private ISignInPresenter presenter;
    private GoogleApiClient mGoogleApiClient;

    public SignInModel(ISignInPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void checkSignInResult(Intent data) {
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

    @Override
    public void signIn(int rcSignIn) {
        GoogleSignInApi auth = Auth.GoogleSignInApi;
        auth.signOut(mGoogleApiClient);
        Intent signInIntent = auth.getSignInIntent(mGoogleApiClient);
        presenter.getSignInView().startActivityForResult(signInIntent, rcSignIn);
    }

    @Override
    public void ConfigGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(ChatApplication.getContext().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(ChatApplication.getContext())
                .enableAutoManage(presenter.getSignInView() /* FragmentActivity */, presenter.getSignInView() /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(presenter.getSignInView(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(presenter.getSignInView(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            presenter.getSignInView().startActivity(new Intent(presenter.getSignInView(), MainActivity.class));
                            presenter.getSignInView().finish();
                        }
                    }
                });
    }
}
