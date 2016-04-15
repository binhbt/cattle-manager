package com.leo.cattle.presentation.presenter;

import android.support.annotation.NonNull;

import com.leo.cattle.domain.Message;
import com.leo.cattle.domain.exception.DefaultErrorBundle;
import com.leo.cattle.domain.exception.ErrorBundle;
import com.leo.cattle.domain.interactor.DefaultSubscriber;
import com.leo.cattle.domain.interactor.LogOutUseCase;
import com.leo.cattle.domain.interactor.UseCase;
import com.leo.cattle.presentation.AndroidApplication;
import com.leo.cattle.presentation.exception.ErrorMessageFactory;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.mapper.MessageModelDataMapper;
import com.leo.cattle.presentation.model.MessageModel;
import com.leo.cattle.presentation.model.UserModel;
import com.leo.cattle.presentation.view.MainMvpView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by leobui on 3/14/2016.
 */
@PerActivity
public class MainPresenter implements Presenter {

    private MainMvpView viewListView;

    private final UseCase logoutUseCase;
    private final MessageModelDataMapper messageModelDataMapper;

    @Inject
    public MainPresenter(@Named("askForLogout") UseCase logoutUseCase,
                         MessageModelDataMapper messageModelDataMapper) {
        this.logoutUseCase = logoutUseCase;
        this.messageModelDataMapper = messageModelDataMapper;
    }

    public void setView(@NonNull MainMvpView view) {
        this.viewListView = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.logoutUseCase.unsubscribe();
        this.viewListView = null;
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        ((LogOutUseCase)this.logoutUseCase).setTokenKey(AndroidApplication.sessionUser.getAccessToken());
        this.logout();
    }

    /**
     * Loads all users.
     */
    private void logout() {
        this.hideViewRetry();
        this.showViewLoading();
        this.askForLogOut();
    }

    public void onUserClicked(UserModel userModel) {
        this.viewListView.viewUser(userModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void signOutSuccess(Message message) {
        final MessageModel messageModel =
                this.messageModelDataMapper.transform(message);
        this.viewListView.logOutSuccess(messageModel);
    }

    private void askForLogOut() {
        this.logoutUseCase.execute(new UserListSubscriber());
    }

    private final class UserListSubscriber extends DefaultSubscriber<Message> {

        @Override public void onCompleted() {
            MainPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            MainPresenter.this.hideViewLoading();
            MainPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            MainPresenter.this.showViewRetry();
        }

        @Override public void onNext(Message message) {
            MainPresenter.this.signOutSuccess(message);
        }
    }
}
