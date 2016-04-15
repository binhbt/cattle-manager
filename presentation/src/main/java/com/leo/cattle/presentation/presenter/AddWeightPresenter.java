package com.leo.cattle.presentation.presenter;

import android.support.annotation.NonNull;

import com.leo.cattle.domain.Event;
import com.leo.cattle.domain.Weight;
import com.leo.cattle.domain.exception.DefaultErrorBundle;
import com.leo.cattle.domain.exception.ErrorBundle;
import com.leo.cattle.domain.interactor.AddEventUseCase;
import com.leo.cattle.domain.interactor.AddWeightUseCase;
import com.leo.cattle.domain.interactor.DefaultSubscriber;
import com.leo.cattle.domain.interactor.UseCase;
import com.leo.cattle.presentation.exception.ErrorMessageFactory;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.mapper.EventModelDataMapper;
import com.leo.cattle.presentation.mapper.WeightModelDataMapper;
import com.leo.cattle.presentation.model.EventModel;
import com.leo.cattle.presentation.model.WeightModel;
import com.leo.cattle.presentation.view.AddEventMvpView;
import com.leo.cattle.presentation.view.AddWeightMvpView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by leo on 4/1/2016.
 */

@PerActivity
public class AddWeightPresenter  implements Presenter{

    private AddWeightMvpView signUpview;

    private final UseCase addEventUseCase;
    private final WeightModelDataMapper eventModelDataMapper;

    @Inject
    public AddWeightPresenter(@Named("addWeight") UseCase addEventUseCase,
                              WeightModelDataMapper eventModelDataMapper) {
        this.addEventUseCase = addEventUseCase;
        this.eventModelDataMapper = eventModelDataMapper;
    }

    public void setView(@NonNull AddWeightMvpView view) {
        this.signUpview = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.addEventUseCase.unsubscribe();
        this.signUpview = null;
    }

    public void initialize(String token, Weight cattle) {
        ((AddWeightUseCase)addEventUseCase).passParamForNewWeight(token, cattle);
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

    private void showUserSuccess(Weight user) {
        final WeightModel userModel =
                this.eventModelDataMapper.transform(user);
        this.signUpview.newWeightSuccess(userModel);
    }

    private void newCattle() {
        this.addEventUseCase.execute(new SignUpSubscriber());
    }

    private final class SignUpSubscriber extends DefaultSubscriber<Weight> {

        @Override public void onCompleted() {
            AddWeightPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            AddWeightPresenter.this.hideViewLoading();
            AddWeightPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            AddWeightPresenter.this.showViewRetry();
        }

        @Override public void onNext(Weight user) {
            AddWeightPresenter.this.showUserSuccess(user);
        }
    }
}
