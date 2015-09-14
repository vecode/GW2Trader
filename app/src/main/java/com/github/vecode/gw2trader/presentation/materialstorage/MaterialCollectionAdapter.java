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
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.github.vecode.gw2trader.R;
import com.github.vecode.gw2trader.domain.model.MaterialBankSlotItem;
import com.github.vecode.gw2trader.domain.model.MaterialCollection;
import com.github.vecode.gw2trader.presentation.widget.ExpandableHeightGridView;

import java.util.ArrayList;

public class MaterialCollectionAdapter extends BaseExpandableListAdapter {

    private Context mContext;

    private ArrayList<MaterialCollection> mMaterialCollections;

    public MaterialCollectionAdapter(Context context){
        mContext = context;
        mMaterialCollections = getSampleCollections();
    }

    @Override
    public int getGroupCount() {
        return mMaterialCollections == null ? 0 : mMaterialCollections.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // children are rendered in a single container view (grid view)
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mMaterialCollections.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mMaterialCollections.get(groupPosition).getItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mMaterialCollections.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mMaterialCollections.get(groupPosition).getItems().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View v = convertView;
        if(convertView == null){

            MaterialCollection collection = (MaterialCollection) getGroup(groupPosition);

            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            v = inflater.inflate(R.layout.material_collection_header, null);
            ((TextView) v.findViewById(R.id.CollectionName)).setText(collection.getName());
        }
        return  v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;
        if (convertView == null){

            MaterialCollection collection = (MaterialCollection) getGroup(groupPosition);
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.material_collection_grid, null);
            ExpandableHeightGridView grid = (ExpandableHeightGridView) v.findViewById(R.id.ItemGrid);
            grid.setAdapter(new MaterialSlotAdapter(mContext, collection.getItems()));
        }
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private ArrayList<MaterialCollection> getSampleCollections(){

        ArrayList<MaterialCollection> collections = new ArrayList<>();

        MaterialCollection collection = new MaterialCollection();
        collection.setId(1);
        collection.setName("Common Crafting Materials");
        ArrayList<MaterialBankSlotItem> items = new ArrayList<>();

        MaterialBankSlotItem item = new MaterialBankSlotItem();
        item.setId(1);
        item.setCategory(1);
        item.setCount(100);
        items.add(item);
        item = new MaterialBankSlotItem();
        item.setId(2);
        item.setCount(0);
        item.setCategory(2);
        items.add(item);
        item = new MaterialBankSlotItem();
        item.setId(3);
        item.setCount(33);
        item.setCategory(1);
        items.add(item);
        items.addAll(items);
        items.addAll(items);

        collection.setItems(items);

        collections.add(collection);
        collections.add(collection);
        return collections;
    }
}
