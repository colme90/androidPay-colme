/*
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.samples.inte;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class LoginFragment extends Fragment implements
        OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {

    private static final String TAG = "LoginFragment";

    public static final int REQUEST_CODE_RESOLVE_ERR = 1005;
    private static final int REQUEST_CODE_SIGN_IN = 1006;
    private static final String WALLET_SCOPE =
            "https://www.googleapis.com/auth/payments.make_payments";

    private GoogleApiClient mGoogleApiClient;
    private int mLoginAction;

    public static LoginFragment newInstance(int loginAction) {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LoginActivity.EXTRA_ACTION, loginAction);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        SignInButton signInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(this);

        view.findViewById(R.id.button_login_bikestore).setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                onSignInClicked();
                break;
            case R.id.button_login_bikestore:
                Toast.makeText(getActivity(), R.string.login_bikestore_message, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (mLoginAction == LoginActivity.Action.LOGOUT) {
            logOut();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
       Log.e(TAG, "onConnectionFailed:" + result.getErrorMessage());
    }

    private void onSignInClicked() {

    }


    private void logOut() {
        if (mGoogleApiClient.isConnected()) {

        } else {
            mLoginAction = LoginActivity.Action.LOGOUT;
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // nothing specifically required here, onConnected will be called when connection resumes
    }
}
