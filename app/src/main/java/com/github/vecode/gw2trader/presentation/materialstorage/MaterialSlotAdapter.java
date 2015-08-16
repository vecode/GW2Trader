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
package com.github.vecode.gw2trader.presentation.materialstorage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.vecode.gw2trader.R;
import com.github.vecode.gw2trader.domain.model.MaterialBankSlotItem;

import java.util.ArrayList;

public class MaterialSlotAdapter extends BaseAdapter {

    private ArrayList<MaterialBankSlotItem> mItems;
    private Context mContext;

    public MaterialSlotAdapter(Context context, ArrayList<MaterialBankSlotItem> items){
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.listviewitem_materialslot, null);

            MaterialBankSlotItem item = (MaterialBankSlotItem) getItem(position);
            ImageView icon = (ImageView) v.findViewById(R.id.Icon);
            TextView count = (TextView) v.findViewById(R.id.Count);
            // TODO set actual icon
            if (item.getCount() == 0){
                count.setVisibility(View.INVISIBLE);
                icon.setAlpha(0.35f);
            }else{
                count.setVisibility(View.VISIBLE);
                count.setText(item.getCount()+"");
                icon.setAlpha(1.f);
            }
        }
        return v;
    }
}
