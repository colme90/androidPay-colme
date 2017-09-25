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
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyLog;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.PaymentMethodToken;

/**
 * Displays the credentials received in the {@code FullWallet}.
 */
public class OrderCompleteActivity extends Activity implements OnClickListener {
    private static final String TAG = "OrderCompleteActivity";
    FullWallet mFullWallet;
    PaymentMethodToken token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VolleyLog.DEBUG = true;

        if(Resources.getRespuestaMensys() ==null) {


            setContentView(R.layout.activity_order_complete);
            mFullWallet = getIntent().getParcelableExtra(Constants.EXTRA_FULL_WALLET);
            token = getIntent().getParcelableExtra("TOKEN");
            TextView tokenData = (TextView) findViewById(R.id.tokenData);
            tokenData.setText(token.getToken());
            tokenData.setTextIsSelectable(true);
            String url = "https://mensys-d.redsys.es:45443/PaysysWebService/clienteControllerCE/init?os=Android&init=" + Base64.encodeToString(token.getToken().getBytes(), Base64.URL_SAFE);
            Log.d(TAG, "ENVIO A PAYSYS: " + url);
            Button continueButton = (Button) findViewById(R.id.button_continue_shopping);
            continueButton.setOnClickListener(this);
            continueButton.setVisibility(View.INVISIBLE);
            WebView webview = (WebView)findViewById(R.id.webviewRespuesta);
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    Log.i("TAG","Pagina cargada");
                    Button continueButton = (Button) findViewById(R.id.button_continue_shopping);
                    continueButton.setVisibility(View.VISIBLE);
                    ProgressBar bar = (ProgressBar) findViewById(R.id.spinner);
                    bar.setVisibility(View.INVISIBLE);
                    Resources.setRespuestaMensys(null);
                }
                @Override
                public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed() ;
                }
            });
            webview.loadUrl(url);
        }
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(OrderCompleteActivity.this, ItemListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        OrderCompleteActivity.this.startActivity(intent);
    }
}
