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

import com.google.android.gms.wallet.WalletConstants;

/**
 * Constants used by Google Wallet SDK Sample.
 */
public class Constants {

    // Environment to use when creating an instance of Wallet.WalletOptions
    public static final int WALLET_ENVIRONMENT = WalletConstants.ENVIRONMENT_SANDBOX;

    public static final String MERCHANT_NAME = "Awesome Bike Store";

    // Intent extra keys
    public static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";
    public static final String EXTRA_MASKED_WALLET = "EXTRA_MASKED_WALLET";
    public static final String EXTRA_FULL_WALLET = "EXTRA_FULL_WALLET";

    public static final String CURRENCY_CODE_USD = "USD";
    public static final String CURRENCY_CODE_EUR = "EUR";

    public static final String clavePublicaInte = "BDC9I6LMemT9hmc3YyglBsFF1nq8/iN8Vs5VMremjXla9FYh5TIOLsol2WY2nmRi5PrZ+hJ4Kf5SkxoZLUylyCY=";

    public static final String clavePublicaDesa="";

    public static int getEntornoClave() {
        return entornoClave;
    }
    public static void setEntornoClave(int entornoClave){
        entornoClave=entornoClave;
    }
    public static final int entornoClave = 0;

    public static String getClavePublicaCustom() {
        return clavePublicaCustom;
    }

    public static void setClavePublicaCustom(String clavePublicaCustom) {
        Constants.clavePublicaCustom = clavePublicaCustom;
    }

    public static String clavePublicaCustom="";

    // values to use with KEY_DESCRIPTION
    public static final String DESCRIPTION_LINE_ITEM_SHIPPING = "Shipping";
    public static final String DESCRIPTION_LINE_ITEM_TAX = "Tax";

    /**
     * Sample list of items for sale. The list would normally be fetched from
     * the merchant's servers.
     */
    public static final ItemInfo[] ITEMS_FOR_SALE = {
            new ItemInfo("Simple Bike", "Features", 420000, 0, CURRENCY_CODE_EUR,
                    "seller data 0", R.drawable.bike000)
    };

    // To change promotion item, change the item here and also corresponding text/image
    // in fragment_promo_address_lookup.xml layout.
    public static final int PROMOTION_ITEM = 2;

}
