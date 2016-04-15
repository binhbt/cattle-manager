/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leo.cattle.data.repository.datasource;

import com.leo.cattle.data.cache.UserCache;
import com.leo.cattle.data.entity.CattleEntity;
import com.leo.cattle.data.entity.ChatSessionEntity;
import com.leo.cattle.data.entity.CostEntity;
import com.leo.cattle.data.entity.EventEntity;
import com.leo.cattle.data.entity.MessageEntity;
import com.leo.cattle.data.entity.UserEntity;
import com.leo.cattle.data.entity.WeightEntity;
import com.leo.cattle.data.net.ApiService;
import com.leo.cattle.data.net.RestApi;
import com.leo.cattle.domain.Cattle;
import com.leo.cattle.domain.Cost;
import com.leo.cattle.domain.Event;
import com.leo.cattle.domain.Weight;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * {@link UserDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudUserDataStore implements UserDataStore {

    //private final RestApi restApi;
    private final UserCache userCache;
    private ApiService restApi;
    private final Action1<UserEntity> saveToCacheAction = userEntity -> {
        if (userEntity != null) {
            CloudUserDataStore.this.userCache.put(userEntity);
        }
    };
    private final Action1<UserEntity> saveToSession = userEntity -> {
        if (userEntity != null && userEntity.getId() > 0) {
            CloudUserDataStore.this.userCache.putSession(userEntity);
        }
    };
    private final Action1<MessageEntity> deleteSession = statusEntity -> {
        if (statusEntity != null) {
            CloudUserDataStore.this.userCache.deleteSession();
        }
    };

    /**
     * Construct a {@link UserDataStore} based on connections to the api (Cloud).
     *
     * @param restApi The {@link RestApi} implementation to use.
     * @param userCache A {@link UserCache} to cache data retrieved from the api.
     */
    public CloudUserDataStore(ApiService restApi, UserCache userCache) {
        this.restApi = restApi;
        this.userCache = userCache;
    }

    @Override
    public Observable<List<UserEntity>> userEntityList(String token) {
        return this.restApi.getApi().userEntityList(token);
    }

    @Override
    public Observable<List<ChatSessionEntity>> sessionEntityList(String token) {
        return this.restApi.getApi().sessionEntityList(token);
    }

    @Override
    public Observable<UserEntity> userEntityDetails(final int userId) {
        return this.restApi.getApi().userEntityById(userId).doOnNext(saveToCacheAction);
    }

    @Override
    public Observable<UserEntity> userEntityOnSession() {
        //Do not implement this
        return null;
    }

    @Override
    public Observable<UserEntity> askForSignup(String userName,
                                               String userEmail,
                                               String userPassword) {
        return this.restApi.getApi().askForSignup(userName, userEmail, userPassword).doOnNext(saveToSession);
    }

    @Override
    public Observable<UserEntity> askForSignIn(String userEmail,
                                               String userPassword) {
        return this.restApi.getApi().askForSignIn(userEmail, userPassword).doOnNext(saveToSession);
    }

    @Override
    public Observable<MessageEntity> askForLogout(String tokenKey) {
        return this.restApi.getApi().askForLogout(tokenKey).doOnNext(deleteSession);
    }

    @Override
    public Observable<CattleEntity> addCattle(String tokenKey, Cattle cattle) {
        return this.restApi.getApi().newCattle(tokenKey,
                cattle.getName(),
                cattle.getDesCription(),
                cattle.getBlood(),
                cattle.getWeight(),
                cattle.getMonthOld(),
                cattle.getCost(),
                cattle.getBuyDate(),
                cattle.getUserId());
    }

    @Override
    public Observable<CostEntity> addCost(String tokenKey, Cost cost) {
        return this.restApi.getApi().newCost(
                tokenKey,
                cost.getName(),
                cost.getDesCription(),
                cost.getCost(),
                cost.getDate(),
                cost.getAuthorId());
    }

    @Override
    public Observable<EventEntity> addEvent(String tokenKey, Event event) {
        return this.restApi.getApi().newEvent(
                tokenKey,
                event.getName(),
                event.getDesCription(),
                event.getCost(),
                event.getDate(),
                event.getAuthorId(),
                event.getCattleIds());
    }
    @Override
    public Observable<WeightEntity> addWeight(String tokenKey, Weight event) {
        return this.restApi.getApi().newWeight(
                tokenKey,
                event.getName(),
                event.getDesCription(),
                event.getWeight(),
                event.getDate(),
                event.getAuthorId(),
                event.getCattleId());
    }
    @Override
    public Observable<List<CattleEntity>> cattleEntityList(String token) {
        return this.restApi.getApi().cattleEntityList(token);
    }
}
