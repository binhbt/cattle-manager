package com.leo.cattle.presentation.presenter;

import android.support.annotation.NonNull;

import com.leo.cattle.domain.Cattle;
import com.leo.cattle.domain.User;
import com.leo.cattle.domain.exception.DefaultErrorBundle;
import com.leo.cattle.domain.exception.ErrorBundle;
import com.leo.cattle.domain.interactor.AddCattleUseCase;
import com.leo.cattle.domain.interactor.DefaultSubscriber;
import com.leo.cattle.domain.interactor.SignUpUseCase;
import com.leo.cattle.domain.interactor.UseCase;
import com.leo.cattle.presentation.exception.ErrorMessageFactory;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.mapper.CattleModelDataMapper;
import com.leo.cattle.presentation.mapper.UserModelDataMapper;
import com.leo.cattle.presentation.model.CattleModel;
import com.leo.cattle.presentation.model.UserModel;
import com.leo.cattle.presentation.view.AddCattleMvpView;
import com.leo.cattle.presentation.view.SignUpMvpView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by leo on 4/1/2016.
 */
@PerActivity
public class AddCattlePresenter  implements Presenter{

    private AddCattleMvpView signUpview;

    private final UseCase addCattleUseCase;
    private final CattleModelDataMapper cattleModelDataMapper;

    @Inject
    public AddCattlePresenter(@Named("addCattle") UseCase addCattleUseCase,
                           CattleModelDataMapper cattleModelDataMapper) {
        this.addCattleUseCase = addCattleUseCase;
        this.cattleModelDataMapper = cattleModelDataMapper;
    }

    public void setView(@NonNull AddCattleMvpView view) {
        this.signUpview = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.addCattleUseCase.unsubscribe();
        this.signUpview = null;
    }

    public void initialize(String token, Cattle cattle) {
        ((AddCattleUseCase)addCattleUseCase).passParamForNewCattle(token, cattle);
        this.addCattle();
    }


    private void addCattle() {
        this.hideViewRetry();
        this.showViewLoading();
        this.newCattle();
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

    private void showUserSuccess(Cattle user) {
        final CattleModel userModel =
                this.cattleModelDataMapper.transform(user);
        this.signUpview.newCattleSuccess(userModel);
    }

    private void newCattle() {
        this.addCattleUseCase.execute(new SignUpSubscriber());
    }

    private final class SignUpSubscriber extends DefaultSubscriber<Cattle> {

        @Override public void onCompleted() {
            AddCattlePresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            AddCattlePresenter.this.hideViewLoading();
            AddCattlePresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            AddCattlePresenter.this.showViewRetry();
        }

        @Override public void onNext(Cattle user) {
            AddCattlePresenter.this.showUserSuccess(user);
        }
    }
}
