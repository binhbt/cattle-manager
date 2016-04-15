package com.leo.cattle.presentation.view;

import com.leo.cattle.presentation.model.ChatSessionModel;
import com.leo.cattle.presentation.model.MessageModel;
import com.leo.cattle.presentation.model.UserModel;

import java.util.Collection;

/**
 * Created by leobui on 3/14/2016.
 */
public interface MainMvpView extends LoadDataView {

    void logOutSuccess(MessageModel messageModel);


    void viewUser(UserModel userModel);



}
