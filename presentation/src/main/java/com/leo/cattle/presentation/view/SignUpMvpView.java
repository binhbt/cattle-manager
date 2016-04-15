package com.leo.cattle.presentation.view;


import com.leo.cattle.presentation.model.UserModel;

/**
 * Created by leobui on 3/3/2016.
 */
public interface SignUpMvpView extends LoadDataView{
    public void signupSuccess(UserModel userModel);
}
