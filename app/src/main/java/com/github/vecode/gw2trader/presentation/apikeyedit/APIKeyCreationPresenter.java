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

public class APIKeyCreationPresenter implements IAPIKeyCreationPresenter, IOnAPIKeyAddedListener,
        IOnAPIKeyLoadedListener {

    private IAPIKeyCreationView view;
    private IAPIKeyCreationInteractor interactor;

    public APIKeyCreationPresenter(IAPIKeyCreationView view){
        this.view = view;

        // TODO use dependency injection
        interactor = new APIKeyCreationInteractor();
    }

    @Override
    public void addKey(String keyName, String key) {
        view.hideKeyError();
        view.hideNameError();
        interactor.saveKey(keyName, key, this);
    }

    @Override
    public void requestKey(int id) {
        interactor.loadKey(id, this);
    }

    @Override
    public void onNameExistsError() {
        view.showNameAlreadyExistsError();
    }

    @Override
    public void onKeyExistsError() {
        view.showAPIKeyAlreadyExistsError();
    }

    @Override
    public void onEmptyNameError() {
        view.showEmptyNameError();
    }

    @Override
    public void onEmptyKeyError() {
        view.showEmptyAPIKeyError();
    }

    @Override
    public void onInvalidKeyError() {
        view.showInvalidAPIKeyError();
    }

    @Override
    public void onSuccess() {
        view.navigateToAPIKeyOverview();
    }

    @Override
    public void onKeyLoaded(APIKey key) {
        view.displayKey(key);
    }

    @Override
    public void onKeyNotFoundError() {

    }
}
