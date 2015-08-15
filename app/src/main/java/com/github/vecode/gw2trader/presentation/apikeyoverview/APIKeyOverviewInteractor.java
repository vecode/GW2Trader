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

import com.github.vecode.gw2trader.domain.model.APIKey;

import java.util.ArrayList;

public class APIKeyOverviewInteractor implements IAPIKeyOverviewInteractor {
    @Override
    public void requestKeys(IOnKeysRetrievedListener listener) {

        // TODO retrieve actual keys
        ArrayList<APIKey> keys = new ArrayList<>();
        APIKey key = new APIKey("name1", "1234");
        key.setId(1);
        key.setAccountAccess(true);
        key.setCharacterAccess(false);
        key.setmInventoryAccess(true);
        key.setTradingPostAccess(true);
        keys.add(key);

        key = new APIKey("name2", "3456");
        key.setId(2);
        key.setAccountAccess(true);
        key.setCharacterAccess(true);
        key.setmInventoryAccess(false);
        key.setTradingPostAccess(false);
        keys.add(key);

        key = new APIKey("name3", "7890");
        key.setId(3);
        key.setAccountAccess(true);
        key.setCharacterAccess(true);
        key.setmInventoryAccess(false);
        key.setTradingPostAccess(true);
        keys.add(key);

        if (keys.isEmpty()){
            listener.onNoKeysFound();
        }else{
            listener.onKeysRetrieved(keys);
        }
    }

    @Override
    public void deleteKey(int id, IOnAPIKeyDeletedListener listener) {

    }
}
