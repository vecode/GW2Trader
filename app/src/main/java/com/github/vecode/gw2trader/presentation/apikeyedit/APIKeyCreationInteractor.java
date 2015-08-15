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

import com.github.vecode.gw2trader.domain.model.APIKey;
import com.github.vecode.gw2trader.presentation.apikeyedit.IAPIKeyCreationInteractor;

public class APIKeyCreationInteractor implements IAPIKeyCreationInteractor {

    // TODO implement
    @Override
    public boolean isKeyValid(String key) {
        return true;
    }

    @Override
    public void saveKey(String name, String key, IOnAPIKeyAddedListener listener) {

        boolean inputInvalid = false;
        if (name == null || name.isEmpty()){
            listener.onEmptyNameError();
            inputInvalid = true;
        }

        // TODO check if name exists

        if (key == null || key.isEmpty()){
            listener.onEmptyKeyError();
            inputInvalid = true;
        }

        // TODO check if key exists

        if(inputInvalid){
            return;
        }

        if (!isKeyValid(key)){
            listener.onInvalidKeyError();
            return;
        }

        // TODO add key

        listener.onSuccess();
    }

    @Override
    public void loadKey(int id, IOnAPIKeyLoadedListener listener) {
        // TODO retrieve actual key
        APIKey key = new APIKey("my key", "1111-2222-3333");
        key.setId(1);
        listener.onKeyLoaded(key);
    }
}
