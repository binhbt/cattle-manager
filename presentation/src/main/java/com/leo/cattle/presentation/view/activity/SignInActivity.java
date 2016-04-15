package com.leo.cattle.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.leo.cattle.presentation.R;
import com.leo.cattle.presentation.internal.di.HasComponent;
import com.leo.cattle.presentation.internal.di.components.DaggerUserComponent;
import com.leo.cattle.presentation.internal.di.components.UserComponent;
import com.leo.cattle.presentation.view.fragment.SignInFragment;

/**
 * Created by leobui on 3/15/2016.
 */
public class SignInActivity extends BaseActivity implements HasComponent<UserComponent> {



    private UserComponent userComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);
        //For GCM
        Bundle b = getIntent().getExtras();
        this.initializeInjector();
        if (savedInstanceState == null) {
            SignInFragment frag = new SignInFragment();
            if (b != null){
                frag.setArguments(b);
            }
            addFragment(R.id.fragmentContainer, frag);
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
