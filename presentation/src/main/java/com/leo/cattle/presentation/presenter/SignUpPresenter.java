package com.leo.cattle.presentation.presenter;

import android.support.annotation.NonNull;

import com.leo.cattle.domain.User;
import com.leo.cattle.domain.exception.DefaultErrorBundle;
import com.leo.cattle.domain.exception.ErrorBundle;
import com.leo.cattle.domain.interactor.DefaultSubscriber;
import com.leo.cattle.domain.interactor.SignUpUseCase;
import com.leo.cattle.domain.interactor.UseCase;
import com.leo.cattle.presentation.exception.ErrorMessageFactory;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.mapper.UserModelDataMapper;
import com.leo.cattle.presentation.model.UserModel;
import com.leo.cattle.presentation.view.SignUpMvpView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by leobui on 3/3/2016.
 */
@PerActivity
public class SignUpPresenter implements Presenter{

    private SignUpMvpView signUpview;

    private final UseCase signUpUseCase;
    private final UserModelDataMapper userModelDataMapper;

    @Inject
    public SignUpPresenter(@Named("askForSignUp") UseCase signUpUseCase,
                           UserModelDataMapper userModelDataMapper) {
        this.signUpUseCase = signUpUseCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void setView(@NonNull SignUpMvpView view) {
        this.signUpview = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.signUpUseCase.unsubscribe();
        this.signUpview = null;
    }

    public void initialize(String username, String userEmail, String userPassword) {
        ((SignUpUseCase)signUpUseCase).passParamForSignup(username, userEmail, userPassword);
        this.askForSignup();
    }


    private void askForSignup() {
        this.hideViewRetry();
        this.showViewLoading();
        this.signUp();
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
        this.signUpview.signupSuccess(userModel);
    }

    private void signUp() {
        this.signUpUseCase.execute(new SignUpSubscriber());
    }

    private final class SignUpSubscriber extends DefaultSubscriber<User> {

        @Override public void onCompleted() {
            SignUpPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            SignUpPresenter.this.hideViewLoading();
            SignUpPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            SignUpPresenter.this.showViewRetry();
        }

        @Override public void onNext(User user) {
            SignUpPresenter.this.showUserSuccess(user);
        }
    }
}
