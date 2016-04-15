/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
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
package com.leo.cattle.data.repository.datasource;

import com.leo.cattle.data.cache.UserCache;
import com.leo.cattle.data.entity.CattleEntity;
import com.leo.cattle.data.entity.ChatSessionEntity;
import com.leo.cattle.data.entity.CostEntity;
import com.leo.cattle.data.entity.EventEntity;
import com.leo.cattle.data.entity.MessageEntity;
import com.leo.cattle.data.entity.UserEntity;
import com.leo.cattle.data.entity.WeightEntity;
import com.leo.cattle.domain.Cattle;
import com.leo.cattle.domain.Cost;
import com.leo.cattle.domain.Event;
import com.leo.cattle.domain.Weight;

import java.util.List;

import rx.Observable;

/**
 * {@link UserDataStore} implementation based on file system data store.
 */
public class DiskUserDataStore implements UserDataStore {

  private final UserCache userCache;

  /**
   * Construct a {@link UserDataStore} based file system data store.
   *
   * @param userCache A {@link UserCache} to cache data retrieved from the api.
   */
  public DiskUserDataStore(UserCache userCache) {
    this.userCache = userCache;
  }

  @Override public Observable<List<UserEntity>> userEntityList(String token) {
    //TODO: implement simple cache for storing/retrieving collections of users.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
  @Override public Observable<List<ChatSessionEntity>> sessionEntityList(String token) {
    //TODO: implement simple cache for storing/retrieving collections of users.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
  @Override public Observable<UserEntity> userEntityDetails(final int userId) {
     return this.userCache.get(userId);
  }

  @Override public Observable<UserEntity> userEntityOnSession(){
    return this.userCache.getLoggedUser();
  }
  @Override
  public Observable<UserEntity> askForSignup(String userName,
                                             String userEmail,
                                             String userPassword){
    //Unimplement
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
  @Override
  public Observable<UserEntity> askForSignIn(String userEmail,
                                             String userPassword){
    //Unimplement
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
  @Override
  public Observable<MessageEntity> askForLogout(String tokenKey){
    //Unimplement
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
  @Override
  public Observable<CattleEntity> addCattle(String tokenKey, Cattle cattle){
    //Unimplement
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
  @Override
  public Observable<CostEntity> addCost(String tokenKey, Cost cost){
    //Unimplement
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
  @Override
  public Observable<EventEntity> addEvent(String tokenKey, Event cost){
    //Unimplement
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
  @Override
  public Observable<WeightEntity> addWeight(String tokenKey, Weight cost){
    //Unimplement
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
  @Override public Observable<List<CattleEntity>> cattleEntityList(String token) {
    //TODO: implement simple cache for storing/retrieving collections of users.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
}
