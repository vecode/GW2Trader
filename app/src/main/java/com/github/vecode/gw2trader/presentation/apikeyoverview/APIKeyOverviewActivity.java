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

package com.github.vecode.gw2trader.presentation.apikeyoverview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.vecode.gw2trader.R;
import com.github.vecode.gw2trader.presentation.apikeyedit.APIKeyCreationActivity;

public class APIKeyOverviewActivity extends AppCompatActivity implements IAPIKeyOverviewView{

    private IAPIKeyOverviewPresenter mPresenter;

    private LinearLayout mErrorMsg;
    private Button mAddKey;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apikey_overview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            ((TextView) toolbar.findViewById(R.id.Title)).setText("API Keys");
        }

        // TODO use dependency injection
        // TODO pass actual adapter
        ListView keys = (ListView) findViewById(R.id.KeyList);
        APIKeyAdapter adapter = new APIKeyAdapter(this, this);
        keys.setAdapter(adapter);
        mPresenter = adapter;

        mErrorMsg = (LinearLayout) findViewById(R.id.NoKeyFoundErrorMsg);
        mAddKey = (Button) findViewById(R.id.AddKey);
        mAddKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onCreateAPIKey();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mPresenter.requestKeys();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_apikey_overview, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addKey) {
            mPresenter.onCreateAPIKey();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showNoKeysFoundMsg() {
        mErrorMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoKeysFoundMsg() {
        mErrorMsg.setVisibility(View.GONE);
    }

    @Override
    public void navigateToAPIKeyCreation() {
        startActivity(new Intent(this, APIKeyCreationActivity.class));
        finish();
    }

    @Override
    public void navigateToAPIKeyEdit(int id){
        Intent intent = new Intent(this, APIKeyCreationActivity.class);
        Bundle b = new Bundle();
        b.putInt("id", id);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    @Override
    public void showAPIKeyDeleteConfirmation() {

    }

    @Override
    public void hideAPIKeyDeleteConfirmation() {

    }


}
