package com.leo.cattle.presentation.presenter;

import android.support.annotation.NonNull;

import com.leo.cattle.domain.User;
import com.leo.cattle.domain.exception.DefaultErrorBundle;
import com.leo.cattle.domain.exception.ErrorBundle;
import com.leo.cattle.domain.interactor.DefaultSubscriber;
import com.leo.cattle.domain.interactor.SignInUseCase;
import com.leo.cattle.domain.interactor.UseCase;
import com.leo.cattle.presentation.AndroidApplication;
import com.leo.cattle.presentation.exception.ErrorMessageFactory;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.mapper.UserModelDataMapper;
import com.leo.cattle.presentation.model.UserModel;
import com.leo.cattle.presentation.view.SignInMvpView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by leobui on 3/3/2016.
 */
@PerActivity
public class SignInPresenter implements Presenter{

    private SignInMvpView signUpview;

    private final UseCase signInUseCase;
    private final UseCase getSessionUseCase;
    private final UserModelDataMapper userModelDataMapper;

    @Inject
    public SignInPresenter(@Named("askForSignIn") UseCase signUpUseCase,@Named("userSession") UseCase getSessionUseCase,
                           UserModelDataMapper userModelDataMapper) {
        this.signInUseCase = signUpUseCase;
        this.getSessionUseCase = getSessionUseCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void setView(@NonNull SignInMvpView view) {
        this.signUpview = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.signInUseCase.unsubscribe();
        this.signUpview = null;
    }


    public void initialize(String userEmail, String userPassword) {
        ((SignInUseCase) signInUseCase).passParamForSignIn(userEmail, userPassword);
        this.askForSignIn();
    }


    private void askForSignIn() {
        this.hideViewRetry();
        this.showViewLoading();
        this.signIn();
    }

    private void showViewLoading() {
        this.signUpview.showLoading();
    }

    private void hideViewLoading() {
        this.signUpview.hideLoading();
    }

    private void showViewRetry() {
        this.signUpview.showRetry();
    }

    private void hideViewRetry() {
        this.signUpview.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.signUpview.context(),
                errorBundle.getException());
        this.signUpview.showError(errorMessage);
    }

    private void showUserSuccess(User user) {
        final UserModel userModel =
                this.userModelDataMapper.transform(user);
        this.signUpview.signInSuccess(userModel);
    }

    private void signIn() {
        this.signInUseCase.execute(new SignInSubscriber());
    }


    private final class SignInSubscriber extends DefaultSubscriber<User> {

        @Override public void onCompleted() {
            SignInPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {

            SignInPresenter.this.hideViewLoading();
            SignInPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            SignInPresenter.this.showViewRetry();
        }

        @Override public void onNext(User user) {
            //SignInPresenter.this.hideViewLoading();
            SignInPresenter.this.showUserSuccess(user);
        }
    }



    public void getSession() {
        this.getSessionUseCase.execute(new SessionSubscriber());
    }

    private final class SessionSubscriber extends DefaultSubscriber<User> {

        @Override public void onCompleted() {

        }

        @Override public void onError(Throwable e) {

        }

        @Override public void onNext(User user) {
            AndroidApplication.sessionUser = userModelDataMapper.transform(user);
            signUpview.moveToHome();
        }
    }
}
