package com.leo.cattle.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.leo.cattle.presentation.R;
import com.leo.cattle.presentation.internal.di.HasComponent;
import com.leo.cattle.presentation.internal.di.components.DaggerUserComponent;
import com.leo.cattle.presentation.internal.di.components.UserComponent;
import com.leo.cattle.presentation.view.fragment.AddCattleFragment;
import com.leo.cattle.presentation.view.fragment.SignInFragment;

/**
 * Created by leo on 3/24/2016.
 */
public class AddCattleActivity extends BaseActivity implements HasComponent<UserComponent> {


    private UserComponent userComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        this.initializeInjector();
        if (savedInstanceState == null) {
            AddCattleFragment frag = new AddCattleFragment();
            addFragment(R.id.fragmentContainer, frag);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public UserComponent getComponent() {
        return userComponent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


