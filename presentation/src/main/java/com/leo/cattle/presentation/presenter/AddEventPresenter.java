package com.leo.cattle.presentation.presenter;

import android.support.annotation.NonNull;

import com.leo.cattle.domain.Cost;
import com.leo.cattle.domain.Event;
import com.leo.cattle.domain.exception.DefaultErrorBundle;
import com.leo.cattle.domain.exception.ErrorBundle;
import com.leo.cattle.domain.interactor.AddCostUseCase;
import com.leo.cattle.domain.interactor.AddEventUseCase;
import com.leo.cattle.domain.interactor.DefaultSubscriber;
import com.leo.cattle.domain.interactor.UseCase;
import com.leo.cattle.presentation.exception.ErrorMessageFactory;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.mapper.CostModelDataMapper;
import com.leo.cattle.presentation.mapper.EventModelDataMapper;
import com.leo.cattle.presentation.model.CostModel;
import com.leo.cattle.presentation.model.EventModel;
import com.leo.cattle.presentation.view.AddCostMvpView;
import com.leo.cattle.presentation.view.AddEventMvpView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by leo on 4/1/2016.
 */

@PerActivity
public class AddEventPresenter  implements Presenter{

    private AddEventMvpView signUpview;

    private final UseCase addEventUseCase;
    private final EventModelDataMapper eventModelDataMapper;

    @Inject
    public AddEventPresenter(@Named("addEvent") UseCase addEventUseCase,
                             EventModelDataMapper eventModelDataMapper) {
        this.addEventUseCase = addEventUseCase;
        this.eventModelDataMapper = eventModelDataMapper;
    }

    public void setView(@NonNull AddEventMvpView view) {
        this.signUpview = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.addEventUseCase.unsubscribe();
        this.signUpview = null;
    }

    public void initialize(String token, Event cattle) {
        ((AddEventUseCase)addEventUseCase).passParamForNewEvent(token, cattle);
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

    private void showUserSuccess(Event user) {
        final EventModel userModel =
                this.eventModelDataMapper.transform(user);
        this.signUpview.newEventSuccess(userModel);
    }

    private void newCattle() {
        this.addEventUseCase.execute(new SignUpSubscriber());
    }

    private final class SignUpSubscriber extends DefaultSubscriber<Event> {

        @Override public void onCompleted() {
            AddEventPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            AddEventPresenter.this.hideViewLoading();
            AddEventPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            AddEventPresenter.this.showViewRetry();
        }

        @Override public void onNext(Event user) {
            AddEventPresenter.this.showUserSuccess(user);
        }
    }
}
