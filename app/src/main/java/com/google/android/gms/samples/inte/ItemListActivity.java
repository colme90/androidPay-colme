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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * The launcher activity for Bikestore application. This activity hosts two fragments,
 * one fragment shows a promotion that uses Address lookup API while the other fragment has the
 * list of bikes for sale. If the app is launched on a tablet
 * (x-large screen) in landscape mode the UI will display an item list and
 * details for the currently selected item. If the application is launched on
 * any device in any other orientation, only the item list is shown.
 *
 * Because of the extra logic around what to do with an item list click, the
 * activity implements {@link OnItemClickListener} instead of {@link ItemListFragment}.
 *
 */
public class ItemListActivity extends BikestoreFragmentActivity
        implements OnItemClickListener, AdapterView.OnItemSelectedListener {

    private boolean mIsDualFrame = false;
    private ListView mItemList;
    private ItemDetailsFragment mDetailsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        mItemList = (ListView) findViewById(android.R.id.list);
        mDetailsFragment = (ItemDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.item_details);
        mIsDualFrame = mDetailsFragment != null;
        if (mIsDualFrame) {
            mItemList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            mDetailsFragment.setItemId(0);
        }

        //get the spinner from the xml.
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
//create a list of items for the spinner.
        String[] items = new String[]{"Desa", "Inte", "Custom"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        SharedPreferences pref1 = getSharedPreferences("clavesPublicas", 0);
        if(!"".equals(pref1.getString("claveCustom", ""))) {
            ((EditText) findViewById(R.id.textoClave)).setText(pref1.getString("claveCustom", ""));
        }


        Button button = (Button)findViewById(R.id.botonGuardar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences mShared;
                SharedPreferences.Editor mEdit;
                mShared = getSharedPreferences("clavesPublicas", 0);
                Log.i("BOTON","Boton guardar");
                mEdit = mShared.edit();
                mEdit.putString("claveCustom", ((EditText) findViewById(R.id.textoClave)).getText().toString());
                mEdit.commit();
            }
        });


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Log.i("TEST","OPCION SELECCIONADA: " + pos);
        if (pos==2) {
            Constants.setClavePublicaCustom(((EditText) findViewById(R.id.textoClave)).getText().toString());
        }
        Constants.setEntornoClave(pos);






    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }





    @Override
    protected void onResume() {
        super.onResume();
        ActivityCompat.invalidateOptionsMenu(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mIsDualFrame) {
            mDetailsFragment.setItemId(position);
        } else {
            Intent intent = new Intent(this, ItemDetailsActivity.class);
            intent.putExtra(Constants.EXTRA_ITEM_ID, position);
            startActivity(intent);
        }
    }

    @Override
    public Fragment getResultTargetFragment() {
        return null;
    }
}
