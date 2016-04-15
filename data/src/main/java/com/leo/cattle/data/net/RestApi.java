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
package com.leo.cattle.data.net;

import com.leo.cattle.data.entity.ChatSessionEntity;
import com.leo.cattle.data.entity.UserEntity;

import java.util.List;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
  String API_BASE_URL = "http://13.76.128.53:8000/api/v1/";

  /** Api url for getting all users */
  String API_URL_GET_USER_LIST = API_BASE_URL + "me/users";
  /** Api url for getting a user profile: Remember to concatenate id + 'json' */
  String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";
  String API_URL_GET_SIGN_IN = API_BASE_URL + "signin";
  String API_URL_GET_SIGN_UP = API_BASE_URL + "signup";
  String API_URL_GET_CHAT_SESSION_LIST = API_BASE_URL + "me/chatSessions";
  /**
   * Retrieves an {@link rx.Observable} which will emit a List of {@link UserEntity}.
   */
  Observable<List<UserEntity>> userEntityList(String token);
  Observable<List<ChatSessionEntity>> sessionEntityList(String token);
  /**
   * Retrieves an {@link rx.Observable} which will emit a {@link UserEntity}.
   *
   * @param userId The user id used to get user data.
   */
  Observable<UserEntity> userEntityById(final int userId);

  Observable<UserEntity> askForSignup(String userName,
                                      String userEmail,
                                      String userPassword);
  Observable<UserEntity> askForSignIn(String userEmail,
                                      String userPassword);
}
