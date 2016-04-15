package com.leo.cattle.presentation.view;


import com.leo.cattle.presentation.model.UserModel;

/**
 * Created by leobui on 3/3/2016.
 */
public interface SignInMvpView extends LoadDataView {
    public void signInSuccess(UserModel userModel);
    public void moveToHome();
}
