/*
 * Copyright (C) 2015 Marvin Danker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.vecode.gw2trader.presentation.apikeyedit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.vecode.gw2trader.R;
import com.github.vecode.gw2trader.domain.model.APIKey;
import com.github.vecode.gw2trader.presentation.apikeyoverview.APIKeyOverviewActivity;

public class APIKeyCreationActivity extends AppCompatActivity implements IAPIKeyCreationView {

    private EditText nameInput;
    private EditText keyInput;
    private TextView keyId;
    private TextView nameErrorMsg;
    private TextView keyErrorMsg;
    private Button addKey;

    private IAPIKeyCreationPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apikey_creation);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
//        if (toolbar != null){
////            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            ((TextView) toolbar.findViewById(R.id.Title)).setText("API Keys");
//        }

        if(presenter == null){
            // TODO use dependency injection
            presenter = new APIKeyCreationPresenter(this);
        }

        nameInput = (EditText) findViewById(R.id.Name);
        keyInput = (EditText) findViewById(R.id.Key);
        keyId = (TextView) findViewById(R.id.KeyId);
        nameErrorMsg = (TextView) findViewById(R.id.NameErrorMsg);
        keyErrorMsg = (TextView) findViewById(R.id.KeyErrorMsg);

        addKey = (Button) findViewById(R.id.SaveKey);
        addKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addKey(nameInput.getText().toString(),
                        keyInput.getText().toString());
            }
        });

        Bundle b = getIntent().getExtras();
        if (b != null){
            int id = b.getInt("id");
            presenter.requestKey(id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apikey_creation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hideNameError() {
            nameErrorMsg.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideKeyError() {
            keyErrorMsg.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNetworkError() {
        keyErrorMsg.setVisibility(View.VISIBLE);
        keyErrorMsg.setText(R.string.error_network_error);
    }

    @Override
    public void showInvalidAPIKeyError() {
        keyErrorMsg.setVisibility(View.VISIBLE);
        keyErrorMsg.setText(R.string.error_key_invalid);
    }

    @Override
    public void showEmptyNameError() {
        nameErrorMsg.setVisibility(View.VISIBLE);
        nameErrorMsg.setText(R.string.error_keyname_empty);
    }

    @Override
    public void showNameAlreadyExistsError() {
        nameErrorMsg.setVisibility(View.VISIBLE);
        nameErrorMsg.setText(R.string.error_keyname_exists);
    }

    @Override
    public void showAPIKeyAlreadyExistsError() {
        keyErrorMsg.setVisibility(View.VISIBLE);
        keyErrorMsg.setText(R.string.error_key_exists);
    }

    @Override
    public void showEmptyAPIKeyError() {
        keyErrorMsg.setVisibility(View.VISIBLE);
        keyErrorMsg.setText(R.string.error_key_empty);
    }

    @Override
    public void showLoadingSpinner() {
        // TODO implement
    }

    @Override
    public void hideLoadingSpinner() {
        // TODO implement
    }

    @Override
    public void navigateToAPIKeyOverview() {
        startActivity(new Intent(this, APIKeyOverviewActivity.class));
        finish();
    }

    @Override
    public void displayKey(APIKey key) {
        nameInput.setText(key.getName());
        keyInput.setText(key.getKey());
        keyId.setText(key.getId());
    }
}
