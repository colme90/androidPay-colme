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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;

/**
 * Activity that displays the user's Google Wallet checkout confirmation page.
 *
 * @see FullWalletConfirmationButtonFragment
 */
public class ConfirmationActivity extends BikestoreFragmentActivity {

    private static final int REQUEST_CODE_CHANGE_MASKED_WALLET = 1002;
    private SupportWalletFragment mWalletFragment;
    private MaskedWallet mMaskedWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMaskedWallet = getIntent().getParcelableExtra(Constants.EXTRA_MASKED_WALLET);
        setContentView(R.layout.activity_confirmation);
        createAndAddWalletFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // no need to show login menu on confirmation screen
        return false;
    }

    private void createAndAddWalletFragment() {
        WalletFragmentStyle walletFragmentStyle = new WalletFragmentStyle()
                .setMaskedWalletDetailsTextAppearance(
                        R.style.BikestoreWalletFragmentDetailsTextAppearance)
                .setMaskedWalletDetailsHeaderTextAppearance(
                        R.style.BikestoreWalletFragmentDetailsHeaderTextAppearance)
                .setMaskedWalletDetailsBackgroundColor(
                        getResources().getColor(R.color.bikestore_white))
                .setMaskedWalletDetailsButtonBackgroundResource(
                        R.drawable.bikestore_btn_default_holo_light);

        // [START wallet_fragment_options]
        WalletFragmentOptions walletFragmentOptions = WalletFragmentOptions.newBuilder()
                .setEnvironment(Constants.WALLET_ENVIRONMENT)
                .setFragmentStyle(walletFragmentStyle)
                .setTheme(WalletConstants.THEME_LIGHT)
                .setMode(WalletFragmentMode.SELECTION_DETAILS)
                .build();
        mWalletFragment = SupportWalletFragment.newInstance(walletFragmentOptions);
        // [END wallet_fragment_options]

        // Now initialize the Wallet Fragment
        String accountName = ((BikestoreApplication) getApplication()).getAccountName();
        WalletFragmentInitParams.Builder startParamsBuilder = WalletFragmentInitParams.newBuilder()
                .setMaskedWallet(mMaskedWallet)
                .setMaskedWalletRequestCode(REQUEST_CODE_CHANGE_MASKED_WALLET)
                .setAccountName(accountName);
        mWalletFragment.initialize(startParamsBuilder.build());

        // add Wallet fragment to the UI
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.dynamic_wallet_masked_wallet_fragment, mWalletFragment)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CHANGE_MASKED_WALLET:
                if (resultCode == Activity.RESULT_OK &&
                        data.hasExtra(WalletConstants.EXTRA_MASKED_WALLET)) {
                    mMaskedWallet = data.getParcelableExtra(WalletConstants.EXTRA_MASKED_WALLET);
                    ((FullWalletConfirmationButtonFragment) getResultTargetFragment())
                            .updateMaskedWallet(mMaskedWallet);
                }
                  // you may also want to use the new masked wallet data here, say to recalculate
                  // shipping or taxes if shipping address changed
                break;
            case WalletConstants.RESULT_ERROR:
                int errorCode = data.getIntExtra(WalletConstants.EXTRA_ERROR_CODE, 0);
                handleError(errorCode);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public Fragment getResultTargetFragment() {
        return getSupportFragmentManager().findFragmentById(
                R.id.full_wallet_confirmation_button_fragment);
    }
}
