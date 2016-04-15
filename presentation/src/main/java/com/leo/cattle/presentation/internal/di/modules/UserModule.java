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
package com.leo.cattle.presentation.internal.di.modules;

import com.leo.cattle.domain.executor.PostExecutionThread;
import com.leo.cattle.domain.executor.ThreadExecutor;
import com.leo.cattle.domain.interactor.AddCattleUseCase;
import com.leo.cattle.domain.interactor.AddCostUseCase;
import com.leo.cattle.domain.interactor.AddEventUseCase;
import com.leo.cattle.domain.interactor.AddWeightUseCase;
import com.leo.cattle.domain.interactor.GetListCattle;
import com.leo.cattle.domain.interactor.GetSessionListUseCase;
import com.leo.cattle.domain.interactor.GetSessionUseCase;
import com.leo.cattle.domain.interactor.GetUserDetails;
import com.leo.cattle.domain.interactor.GetUserList;
import com.leo.cattle.domain.interactor.LogOutUseCase;
import com.leo.cattle.domain.interactor.SignInUseCase;
import com.leo.cattle.domain.interactor.SignUpUseCase;
import com.leo.cattle.domain.interactor.UseCase;
import com.leo.cattle.domain.repository.UserRepository;
import com.leo.cattle.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class UserModule {

  private int userId = -1;

  public UserModule() {}

  public UserModule(int userId) {
    this.userId = userId;
  }

  @Provides @PerActivity
  @Named("userList") UseCase provideGetUserListUseCase(
      GetUserList getUserList) {
    return getUserList;
  }
    @Provides @PerActivity @Named("chatSessionList") UseCase provideGetChatSessionListUseCase(
            GetSessionListUseCase getUserList) {
        return getUserList;
    }

  @Provides @PerActivity @Named("userDetails") UseCase provideGetUserDetailsUseCase(
      UserRepository userRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new GetUserDetails(userId, userRepository, threadExecutor, postExecutionThread);
  }
    @Provides
    @PerActivity
    @Named("userSession")
    UseCase provideGetUserSessionUseCase(
            UserRepository userRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new GetSessionUseCase(userRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("askForSignUp")
    UseCase provideSignUpUseCase(
            UserRepository userRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new SignUpUseCase(userRepository, threadExecutor, postExecutionThread);
    }
    @Provides
    @PerActivity
    @Named("askForSignIn")
    UseCase provideSignInUseCase(
            UserRepository userRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new SignInUseCase(userRepository, threadExecutor, postExecutionThread);
    }
    @Provides
    @PerActivity
    @Named("askForLogout")
    UseCase provideLogOutUseCase(
            UserRepository userRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new LogOutUseCase(userRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("addCattle")
    UseCase provideAddCattleUseCase(
            UserRepository userRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new AddCattleUseCase(userRepository, threadExecutor, postExecutionThread);
    }
    @Provides
    @PerActivity
    @Named("addCost")
    UseCase provideAddCostUseCase(
            UserRepository userRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new AddCostUseCase(userRepository, threadExecutor, postExecutionThread);
    }
    @Provides
    @PerActivity
    @Named("addEvent")
    UseCase provideAddEventUseCase(
            UserRepository userRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new AddEventUseCase(userRepository, threadExecutor, postExecutionThread);
    }
    @Provides
    @PerActivity
    @Named("addWeight")
    UseCase provideAddWeightUseCase(
            UserRepository userRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new AddWeightUseCase(userRepository, threadExecutor, postExecutionThread);
    }
    @Provides @PerActivity
    @Named("cattleList") UseCase provideGetCattleListUseCase(
            GetListCattle getListCattle) {
        return getListCattle;
    }
}