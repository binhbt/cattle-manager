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
package com.leo.cattle.presentation.internal.di.components;

import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.internal.di.modules.ActivityModule;
import com.leo.cattle.presentation.internal.di.modules.UserModule;
import com.leo.cattle.presentation.view.activity.AddEventActivity;
import com.leo.cattle.presentation.view.activity.MainActivity;
import com.leo.cattle.presentation.view.fragment.AddCattleFragment;
import com.leo.cattle.presentation.view.fragment.AddCostFrament;
import com.leo.cattle.presentation.view.fragment.AddEventFragment;
import com.leo.cattle.presentation.view.fragment.AddWeightFragment;
import com.leo.cattle.presentation.view.fragment.ListCattleFragment;
import com.leo.cattle.presentation.view.fragment.SignInFragment;
import com.leo.cattle.presentation.view.fragment.SignUpFragment;
import com.leo.cattle.presentation.view.fragment.UserDetailsFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
  void inject(UserDetailsFragment userDetailsFragment);
  void inject(MainActivity mainActivity);
  void inject(SignInFragment signInFragment);
  void inject(SignUpFragment signUpFragment);
  void inject(AddCattleFragment addCattleFragment);
  void inject(AddCostFrament addCostFrament);
  void inject(AddEventFragment addEventFragment);
  void inject(AddWeightFragment addWeightFragment);
  void inject(ListCattleFragment listCattleFragment);
}
