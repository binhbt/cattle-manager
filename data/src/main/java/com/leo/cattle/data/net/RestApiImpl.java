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
package com.leo.cattle.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.leo.cattle.data.entity.ChatSessionEntity;
import com.leo.cattle.data.entity.UserEntity;
import com.leo.cattle.data.entity.mapper.ChatSessionEntityJsonMapper;
import com.leo.cattle.data.entity.mapper.UserEntityJsonMapper;
import com.leo.cattle.data.exception.NetworkConnectionException;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

  private final Context context;
  private final UserEntityJsonMapper userEntityJsonMapper;
  private final ChatSessionEntityJsonMapper sessionEntityJsonMapper;
  /**
   * Constructor of the class
   *
   * @param context {@link android.content.Context}.
   * @param userEntityJsonMapper {@link UserEntityJsonMapper}.
   */
  public RestApiImpl(Context context,
                     UserEntityJsonMapper userEntityJsonMapper,
                     ChatSessionEntityJsonMapper sessionEntityJsonMapper) {
    if (context == null || userEntityJsonMapper == null || sessionEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.userEntityJsonMapper = userEntityJsonMapper;
    this.sessionEntityJsonMapper = sessionEntityJsonMapper;
  }

  @RxLogObservable
  @Override public Observable<List<UserEntity>> userEntityList(String token) {
    return Observable.create(subscriber -> {
      if (isThereInternetConnection()) {
        try {
          String responseUserEntities = getUserEntitiesFromApi(token);
          if (responseUserEntities != null) {
            subscriber.onNext(userEntityJsonMapper.transformUserEntityCollection(
                responseUserEntities));
            subscriber.onCompleted();
          } else {
            subscriber.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          subscriber.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        subscriber.onError(new NetworkConnectionException());
      }
    });
  }
  private String getUserEntitiesFromApi(String token) throws MalformedURLException {
    return ApiConnection.createGET(API_URL_GET_USER_LIST).requestSyncCall(token);
  }
  @RxLogObservable
  @Override public Observable<List<ChatSessionEntity>> sessionEntityList(String token) {
    return Observable.create(subscriber -> {
      if (isThereInternetConnection()) {
        try {
          String responseUserEntities = getChatSessionEntitiesFromApi(token);
          if (responseUserEntities != null) {
            subscriber.onNext(sessionEntityJsonMapper.transformChatSessionEntityCollection(
                    responseUserEntities));
            subscriber.onCompleted();
          } else {
            subscriber.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          subscriber.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        subscriber.onError(new NetworkConnectionException());
      }
    });
  }

  private String getChatSessionEntitiesFromApi(String token) throws MalformedURLException {
    return ApiConnection.createGET(API_URL_GET_CHAT_SESSION_LIST).requestSyncCall(token);
  }
  @RxLogObservable
  @Override public Observable<UserEntity> userEntityById(final int userId) {
    return Observable.create(subscriber -> {
      if (isThereInternetConnection()) {
        try {
          String responseUserDetails = getUserDetailsFromApi(userId);
          if (responseUserDetails != null) {
            subscriber.onNext(userEntityJsonMapper.transformUserEntity(responseUserDetails));
            subscriber.onCompleted();
          } else {
            subscriber.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          subscriber.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        subscriber.onError(new NetworkConnectionException());
      }
    });
  }



  private String getUserDetailsFromApi(int userId) throws MalformedURLException {
    String apiUrl = API_URL_GET_USER_DETAILS + userId + ".json";
    return ApiConnection.createGET(apiUrl).requestSyncCall();
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }

  @Override
  public Observable<UserEntity> askForSignup(String userName,
                                             String userEmail,
                                             String userPassword){
    return Observable.create(subscriber -> {
      if (isThereInternetConnection()) {
        try {
          String responseUserDetails = sigUp(userName, userEmail, userPassword);
          if (responseUserDetails != null) {
            subscriber.onNext(userEntityJsonMapper.transformUserEntity(responseUserDetails));
            subscriber.onCompleted();
          } else {
            subscriber.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          subscriber.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        subscriber.onError(new NetworkConnectionException());
      }
    });
  }
  private String sigUp(String userName,
                       String userEmail,
                       String userPassword) throws MalformedURLException {
    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
    paramList.add(new BasicNameValuePair("username", userName));
    //paramList.add(new BasicNameValuePair("email", userEmail));
    paramList.add(new BasicNameValuePair("password", userPassword));

    return ApiConnection.createGET(API_URL_GET_SIGN_UP + getStringParam(paramList)).requestSyncCall();
  }
  @Override
  public Observable<UserEntity> askForSignIn(String userEmail,
                                             String userPassword){
    return Observable.create(subscriber -> {
      if (isThereInternetConnection()) {
        try {
          String responseUserDetails = sigIn(userEmail, userPassword);
          if (responseUserDetails != null) {
            subscriber.onNext(userEntityJsonMapper.transformUserEntity(responseUserDetails));
            subscriber.onCompleted();
          } else {
            subscriber.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          subscriber.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        subscriber.onError(new NetworkConnectionException());
      }
    });
  }
  private String sigIn(String userEmail,
                       String userPassword) throws MalformedURLException {
    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
    paramList.add(new BasicNameValuePair("username", userEmail));
    paramList.add(new BasicNameValuePair("password", userPassword));

    return ApiConnection.createGET(API_URL_GET_SIGN_IN + getStringParam(paramList)).requestSyncCall();
  }
  private String getStringParam(List<NameValuePair> params) {
    String ret = "?";
    return ret + URLEncodedUtils.format(params, "utf8");
  }
}
