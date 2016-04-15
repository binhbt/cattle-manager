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
package com.leo.cattle.domain.repository;

import com.leo.cattle.domain.Cattle;
import com.leo.cattle.domain.ChatSession;
import com.leo.cattle.domain.Cost;
import com.leo.cattle.domain.Event;
import com.leo.cattle.domain.Message;
import com.leo.cattle.domain.Weight;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link com.leo.cattle.domain.User} related data.
 */
public interface UserRepository {
  /**
   * Get an {@link rx.Observable} which will emit a List of {@link com.leo.cattle.domain.User}.
   */
  Observable<List<com.leo.cattle.domain.User>> users(String token);
  Observable<List<ChatSession>> sessions( String token);

  /**
   * Get an {@link rx.Observable} which will emit a {@link com.leo.cattle.domain.User}.
   *
   * @param userId The user id used to retrieve user data.
   */
  Observable<com.leo.cattle.domain.User> user(final int userId);

  Observable<com.leo.cattle.domain.User> getLoggedUser();

  Observable<com.leo.cattle.domain.User> askForSignup(String userName, String userEmail, String userPassword);
  Observable<com.leo.cattle.domain.User> askForSignIn(String userEmail, String userPassword);
  Observable<Message> askForLogout(String tokenKey);
  Observable<Cattle> addCattle(String tokenKey, Cattle cattle);
  Observable<Cost> addCost(String tokenKey, Cost cost);
  Observable<Event> addEvent(String tokenKey, Event cost);
  Observable<Weight> addWeight(String tokenKey, Weight cost);
  Observable<List<Cattle>> cattles(String token);
}
