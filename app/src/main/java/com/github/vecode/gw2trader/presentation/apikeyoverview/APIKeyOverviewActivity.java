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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.github.vecode.gw2trader.R;
import com.github.vecode.gw2trader.presentation.apikeyedit.APIKeyCreationActivity;

public class APIKeyOverviewActivity extends Activity implements IAPIKeyOverviewView{

    private IAPIKeyOverviewPresenter presenter;

    private LinearLayout errorMsg;
    private Button addKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apikey_overview);

        // TODO use dependency injection
        // TODO pass actual adapter
        ListView keys = (ListView) findViewById(R.id.KeyList);
        APIKeyAdapter adapter = new APIKeyAdapter(this, this);
        keys.setAdapter(adapter);
        presenter = adapter;

        errorMsg = (LinearLayout) findViewById(R.id.NoKeyFoundErrorMsg);
        addKey = (Button) findViewById(R.id.AddKey);
        addKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCreateAPIKey();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        presenter.requestKeys();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showNoKeysFoundMsg() {
        errorMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoKeysFoundMsg() {
        errorMsg.setVisibility(View.GONE);
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
