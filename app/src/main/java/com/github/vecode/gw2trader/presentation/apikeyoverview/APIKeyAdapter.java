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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.vecode.gw2trader.R;
import com.github.vecode.gw2trader.domain.model.APIKey;

import java.util.ArrayList;
import java.util.List;

public class APIKeyAdapter extends BaseAdapter implements IAPIKeyOverviewPresenter,
        IOnKeysRetrievedListener, IOnAPIKeyDeletedListener{

    private ArrayList<APIKey> mKeys;
    private Context mContext;
    private IAPIKeyOverviewView mView;
    private IAPIKeyOverviewInteractor mInteractor;

    public APIKeyAdapter(Context context, IAPIKeyOverviewView view){
        mContext = context;
        mView = view;
        mKeys = new ArrayList<>();


        // TODO use dependency injection
        mInteractor = new APIKeyOverviewInteractor();
    }

    @Override
    public int getCount() {
        return mKeys == null ? 0 : mKeys.size();
    }

    @Override
    public Object getItem(int position) {
        return mKeys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mKeys.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.listviewitem_apikey, null);

            APIKey key = (APIKey) getItem(position);
            ((TextView) v.findViewById(R.id.Name)).setText(key.getName());
            ((TextView) v.findViewById(R.id.Key)).setText(key.getKey());

            ((CheckBox) v.findViewById(R.id.AccountPermission)).setChecked(key.getAccountAccess());
            ((CheckBox) v.findViewById(R.id.InventoryPermission)).setChecked(key.getInventoryAccess());
            ((CheckBox) v.findViewById(R.id.TradingPostPermission)).setChecked(key.getTradingPostAccess());
            ((CheckBox) v.findViewById(R.id.CharacterPermission)).setChecked(key.getCharacterAccess());

            final RelativeLayout details = (RelativeLayout) v.findViewById(R.id.Details);

            (v.findViewById(R.id.Toggle)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(details.getVisibility()==View.GONE)

                    {
                        details.setVisibility(View.VISIBLE);
                    }

                    else

                    {
                        details.setVisibility(View.GONE);
                    }
                }
            });
        }
        return v;
    }

    @Override
    public void onAPIKeyEdit(int id) {
        mView.navigateToAPIKeyEdit(id);
    }

    @Override
    public void onAPIKeyDelete(int id) {
        // TODO show confirmation dialog
    }

    @Override
    public void onAPIKeyDeleteConfirmed(int id) {
        mInteractor.deleteKey(id, this);
    }

    @Override
    public void onAPIKeyDeleteCancelled() {
        // TODO hide confirmation dialog
    }

    @Override
    public void onCreateAPIKey() {
        mView.navigateToAPIKeyCreation();
    }

    @Override
    public void onShowKeyInformation(int id) {

    }

    @Override
    public void requestKeys() {
        mInteractor.requestKeys(this);
    }


    @Override
    public void onKeysRetrieved(List<APIKey> keys) {
        mKeys.clear();
        mKeys.addAll(keys);
        mView.hideNoKeysFoundMsg();

        notifyDataSetChanged();
    }

    @Override
    public void onNoKeysFound() {
        mView.showNoKeysFoundMsg();
    }

    @Override
    public void onRetrievalFailed() {

    }

    @Override
    public void onSuccess() {

    }

}
