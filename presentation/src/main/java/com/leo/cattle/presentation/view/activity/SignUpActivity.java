package com.leo.cattle.presentation.view.activity;

import android.os.Bundle;

import com.leo.cattle.presentation.R;
import com.leo.cattle.presentation.internal.di.HasComponent;
import com.leo.cattle.presentation.internal.di.components.DaggerUserComponent;
import com.leo.cattle.presentation.internal.di.components.UserComponent;
import com.leo.cattle.presentation.view.fragment.SignUpFragment;

/**
 * Created by leobui on 3/15/2016.
 */
public class SignUpActivity extends BaseActivity implements HasComponent<UserComponent> {


    private UserComponent userComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new SignUpFragment());
        }
    }

    private void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override public UserComponent getComponent() {
        return userComponent;
    }


}