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
package com.leo.cattle.presentation;

import android.app.Application;

import com.leo.cattle.presentation.internal.di.components.ApplicationComponent;
import com.leo.cattle.presentation.internal.di.components.DaggerApplicationComponent;
import com.leo.cattle.presentation.internal.di.modules.ApplicationModule;
import com.leo.cattle.presentation.model.UserModel;
import com.leo.cattle.presentation.util.TypefaceUtil;

import java.net.URISyntaxException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {
  public static UserModel sessionUser;
  public static final String CHAT_SERVER_URL = "http://13.76.128.53:8000/socket.io/";
  private ApplicationComponent applicationComponent;
  private Realm mRealm;
  @Override public void onCreate() {
    super.onCreate();
    this.initializeInjector();
    this.initializeLeakDetection();

    // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
    RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
    // Get a Realm instance for this thread
    mRealm = Realm.getInstance(realmConfig);

    TypefaceUtil.overrideFont(getApplicationContext(), "Sans Serif", "fonts/catalyst-font.ttf");
  }

  private void initializeInjector() {
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  private void initializeLeakDetection() {
    if (BuildConfig.DEBUG) {
      //LeakCanary.install(this);
    }
  }

  private Socket mSocket;
  {
    try {
      mSocket = IO.socket(CHAT_SERVER_URL);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public Socket getSocket() {
    return mSocket;
  }


  public Realm getRealm(){
    return mRealm;
  }
}
