package com.leo.cattle.presentation.view.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.leo.cattle.presentation.AndroidApplication;
import com.leo.cattle.presentation.R;
import com.leo.cattle.presentation.internal.di.HasComponent;
import com.leo.cattle.presentation.internal.di.components.DaggerUserComponent;
import com.leo.cattle.presentation.internal.di.components.UserComponent;
import com.leo.cattle.presentation.internal.di.modules.UserModule;
import com.leo.cattle.presentation.model.MessageModel;
import com.leo.cattle.presentation.model.UserModel;
import com.leo.cattle.presentation.presenter.MainPresenter;
import com.leo.cattle.presentation.presenter.UserDetailsPresenter;
import com.leo.cattle.presentation.view.MainMvpView;
import com.leo.cattle.presentation.view.fragment.AddEventFragment;
import com.leo.cattle.presentation.view.fragment.ListCattleFragment;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainMvpView, HasComponent<UserComponent> {
    private UserComponent userComponent;
    @Inject
    MainPresenter mainPresenter;

    @Bind(R.id.avatar)
    ImageView mAvatar;

    @Bind(R.id.name)
    TextView mName;

    @Bind(R.id.email)
    TextView mEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        ButterKnife.bind(this, headerLayout);

        final com.getbase.floatingactionbutton.FloatingActionButton actionA = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCattle();
            }
        });
        final View actionB = findViewById(R.id.action_b);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddEventActivity.class));
            }
        });
        final View actionC = findViewById(R.id.action_c);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddCostActivity.class));
            }
        });

        initializeInjector();
        if (savedInstanceState == null) {
            ListCattleFragment frag = new ListCattleFragment();
            addFragment(R.id.fragmentContainer, frag);
        }
        this.getComponent().inject(this);
        this.mainPresenter.setView(this);

        loadUserData();
    }
    private void loadUserData(){
        mName.setText(AndroidApplication.sessionUser.getName());
        mEmail.setText(AndroidApplication.sessionUser.getEmail());
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_repo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_cattle) {
            showAddCattle();
            return true;
        }else if (id == R.id.view_graph){

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cattles) {
            // Handle the camera action
        } else if (id == R.id.nav_events) {

        } else if (id == R.id.nav_costs) {

        } else if (id == R.id.nav_massage) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_logout) {
            this.mainPresenter.initialize();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showAddCattle() {
        /*
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragment newFragment = AddCattleDialogFragment.newInstance();

        newFragment.show(ft, "dialog");
        */
        startActivity(new Intent(this, AddCattleActivity.class));
    }
    @Override
    public void logOutSuccess(MessageModel messageModel){
        Toast.makeText(this, messageModel.getMessage(), Toast.LENGTH_LONG).show();
        if (messageModel.getStatus() ==1){
            AndroidApplication.sessionUser = null;
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
    }

    @Override
    public void viewUser(UserModel userModel){

    }
    @Override public void showLoading() {
    }

    @Override public void hideLoading() {
    }

    @Override public void showRetry() {
    }

    @Override public void hideRetry() {
    }

    @Override public void showError(String message) {
    }

    @Override public Context context() {
        return getApplicationContext();
    }
    private void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(new UserModule(0))
                .build();
    }

    @Override public UserComponent getComponent() {
        return userComponent;
    }
    @Override public void onResume() {
        super.onResume();
        this.mainPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.mainPresenter.pause();
    }



    @Override public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        this.mainPresenter.destroy();
    }
}
