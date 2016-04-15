package com.leo.cattle.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.leo.cattle.presentation.R;
import com.leo.cattle.presentation.internal.di.components.UserComponent;
import com.leo.cattle.presentation.model.CattleModel;
import com.leo.cattle.presentation.model.UserModel;
import com.leo.cattle.presentation.presenter.ListCattlePresenter;
import com.leo.cattle.presentation.presenter.UserDetailsPresenter;
import com.leo.cattle.presentation.view.ListCattleMvpView;
import com.leo.cattle.presentation.view.UserDetailsView;
import com.leo.cattle.presentation.view.activity.CattleDetailActivity;
import com.leo.cattle.presentation.view.adapter.CattleAdapter;
import com.leo.cattle.presentation.view.component.AutoLoadImageView;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by leo on 4/1/2016.
 */
public class ListCattleFragment extends BaseFragment implements ListCattleMvpView,  SwipeRefreshLayout.OnRefreshListener {
    public interface UserListListener {
        void onUserClicked(final CattleModel userModel);
    }

    @Inject
    ListCattlePresenter userListPresenter;
    @Inject
    CattleAdapter usersAdapter;


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.contentView)
    SwipeRefreshLayout contentView;
    private UserListListener userListListener;

    public ListCattleFragment() {
        setRetainInstance(true);
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof UserListListener) {
            this.userListListener = (UserListListener) activity;
        }
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(UserComponent.class).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_list_cattle, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadUserList();
        }
    }

    @Override public void onResume() {
        super.onResume();
        this.userListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.userListPresenter.pause();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.userListPresenter.destroy();
    }

    @Override public void onDetach() {
        super.onDetach();
        this.userListListener = null;
    }

    @Override public void showLoading() {
        //this.rl_progress.setVisibility(View.VISIBLE);
        //this.getActivity().setProgressBarIndeterminateVisibility(true);
        contentView.setRefreshing(true);
    }

    @Override public void hideLoading() {
        //this.rl_progress.setVisibility(View.GONE);
        //this.getActivity().setProgressBarIndeterminateVisibility(false);
        contentView.setRefreshing(false);
    }

    @Override public void showRetry() {
        //this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override public void hideRetry() {
        //this.rl_retry.setVisibility(View.GONE);
    }

    @Override public void renderUserList(Collection<CattleModel> userModelCollection) {
        if (userModelCollection != null) {
            this.usersAdapter.setUsersCollection(userModelCollection);
        }
    }

    @Override public void viewUser(CattleModel userModel) {
        if (this.userListListener != null) {
            this.userListListener.onUserClicked(userModel);
        }
    }

    @Override public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.usersAdapter.setOnItemClickListener(onItemClickListener);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(usersAdapter);
        // see http://stackoverflow.com/questions/26858692/swiperefreshlayout-setrefreshing-not-showing-indicator-initially
        int offset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
        contentView.setProgressViewOffset(false, 0, offset);
        contentView.setOnRefreshListener(this);
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.userListPresenter.initialize();
    }



    private CattleAdapter.OnItemClickListener onItemClickListener =
            new CattleAdapter.OnItemClickListener() {

                @Override public void onUserItemClicked(CattleModel userModel) {
                    Gson gson = new Gson();
                    String json = gson.toJson(userModel);
                    Log.e("cattle json", json);
                    Intent intentToLaunch = new Intent(getActivity(), CattleDetailActivity.class);
                    intentToLaunch.putExtra(CattleDetailFragment.ARG_ITEM_ID, json);
                    startActivity(intentToLaunch);
                }
            };

    @Override
    public void onRefresh() {
        this.loadUserList();
    }
}