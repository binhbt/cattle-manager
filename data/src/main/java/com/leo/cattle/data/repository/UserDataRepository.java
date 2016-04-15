/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leo.cattle.data.repository;

import com.leo.cattle.data.entity.MessageEntity;
import com.leo.cattle.data.entity.mapper.CattleEntityDataMapper;
import com.leo.cattle.data.entity.mapper.ChatSessionEntityDataMapper;
import com.leo.cattle.data.entity.mapper.CostEntityDataMapper;
import com.leo.cattle.data.entity.mapper.EventEntityDataMapper;
import com.leo.cattle.data.entity.mapper.MessageEntityDataMapper;
import com.leo.cattle.data.entity.mapper.UserEntityDataMapper;
import com.leo.cattle.data.entity.mapper.WeightEntityDataMapper;
import com.leo.cattle.data.repository.datasource.UserDataStore;
import com.leo.cattle.data.repository.datasource.UserDataStoreFactory;
import com.leo.cattle.domain.Cattle;
import com.leo.cattle.domain.ChatSession;
import com.leo.cattle.domain.Cost;
import com.leo.cattle.domain.Event;
import com.leo.cattle.domain.Message;
import com.leo.cattle.domain.User;
import com.leo.cattle.domain.Weight;
import com.leo.cattle.domain.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link UserRepository} for retrieving user data.
 */
@Singleton
public class UserDataRepository implements UserRepository {

  private final UserDataStoreFactory userDataStoreFactory;
  private final UserEntityDataMapper userEntityDataMapper;
  private final MessageEntityDataMapper messageEntityDataMapper;
  private final ChatSessionEntityDataMapper chatSessionEntityDataMapper;
  private final CattleEntityDataMapper cattleEntityDataMapper;
  private final CostEntityDataMapper costEntityDataMapper;
  private final EventEntityDataMapper eventEntityDataMapper;
  private final WeightEntityDataMapper weightEntityDataMapper;
  /**
   * Constructs a {@link UserRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param userEntityDataMapper {@link UserEntityDataMapper}.
   */
  @Inject
  public UserDataRepository(UserDataStoreFactory dataStoreFactory,
      UserEntityDataMapper userEntityDataMapper, ChatSessionEntityDataMapper chatSessionEntityDataMapper,
                            MessageEntityDataMapper messageEntityDataMapper,
                            CattleEntityDataMapper cattleEntityDataMapper,
                            CostEntityDataMapper costEntityDataMapper,
                            EventEntityDataMapper eventEntityDataMapper,
                            WeightEntityDataMapper weightEntityDataMapper) {
    this.userDataStoreFactory = dataStoreFactory;
    this.userEntityDataMapper = userEntityDataMapper;
    this.chatSessionEntityDataMapper = chatSessionEntityDataMapper;
      this.messageEntityDataMapper = messageEntityDataMapper;
      this.cattleEntityDataMapper = cattleEntityDataMapper;
      this.costEntityDataMapper = costEntityDataMapper;
      this.eventEntityDataMapper = eventEntityDataMapper;
      this.weightEntityDataMapper = weightEntityDataMapper;
  }

  @SuppressWarnings("Convert2MethodRef")
  @Override public Observable<List<User>> users(String token) {
    //we always get all users from the cloud
    final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
    return userDataStore.userEntityList(token)
        .map(userEntities -> this.userEntityDataMapper.transform(userEntities));
  }
    @SuppressWarnings("Convert2MethodRef")
    @Override public Observable<List<ChatSession>> sessions(String token) {
        //we always get all users from the cloud
        final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
        return userDataStore.sessionEntityList(token)
                .map(sessionEntities -> this.chatSessionEntityDataMapper.transform(sessionEntities));
    }
  @SuppressWarnings("Convert2MethodRef")
  @Override public Observable<User> user(int userId) {
    final UserDataStore userDataStore = this.userDataStoreFactory.create(userId);
    return userDataStore.userEntityDetails(userId)
        .map(userEntity -> this.userEntityDataMapper.transform(userEntity));
  }

  @SuppressWarnings("Convert2MethodRef")
  @Override public Observable<User> getLoggedUser() {
    final UserDataStore userDataStore = this.userDataStoreFactory.createForSession();
    return userDataStore.userEntityOnSession()
            .map(userEntity -> this.userEntityDataMapper.transform(userEntity));
  }
  @Override
  public Observable<User> askForSignup(String userName,
                                       String userEmail,
                                       String userPassword){
    final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
    return userDataStore.askForSignup(userName, userEmail, userPassword)
            .map(userEntity -> this.userEntityDataMapper.transform(userEntity));
  }
  @Override
  public Observable<User> askForSignIn(String userEmail,
                                       String userPassword){
    final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
    return userDataStore.askForSignIn(userEmail, userPassword)
            .map(userEntity -> this.userEntityDataMapper.transform(userEntity));
  }
    @Override
    public Observable<Message> askForLogout(String tokenKey){
        final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
        return userDataStore.askForLogout(tokenKey)
                .map(messageEntity -> this.messageEntityDataMapper.transform(messageEntity));
    }

    public Observable<Cattle> addCattle(String tokenKey, Cattle cattle){
        final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
        return userDataStore.addCattle(tokenKey, cattle)
                .map(messageEntity -> this.cattleEntityDataMapper.transform(messageEntity));
    }
    public Observable<Cost> addCost(String tokenKey, Cost cost){
        final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
        return userDataStore.addCost(tokenKey, cost)
                .map(costEntity -> this.costEntityDataMapper.transform(costEntity));
    }
    public Observable<Event> addEvent(String tokenKey, Event cost){
        final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
        return userDataStore.addEvent(tokenKey, cost)
                .map(costEntity -> this.eventEntityDataMapper.transform(costEntity));
    }
    public Observable<Weight> addWeight(String tokenKey, Weight cost){
        final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
        return userDataStore.addWeight(tokenKey, cost)
                .map(costEntity -> this.weightEntityDataMapper.transform(costEntity));
    }
    @SuppressWarnings("Convert2MethodRef")
    @Override public Observable<List<Cattle>> cattles(String token) {
        //we always get all users from the cloud
        final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
        return userDataStore.cattleEntityList(token)
                .map(userEntities -> this.cattleEntityDataMapper.transform(userEntities));
    }
}
