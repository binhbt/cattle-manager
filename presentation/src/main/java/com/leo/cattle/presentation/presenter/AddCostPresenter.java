package com.leo.cattle.presentation.presenter;

import android.support.annotation.NonNull;

import com.leo.cattle.domain.Cattle;
import com.leo.cattle.domain.Cost;
import com.leo.cattle.domain.exception.DefaultErrorBundle;
import com.leo.cattle.domain.exception.ErrorBundle;
import com.leo.cattle.domain.interactor.AddCattleUseCase;
import com.leo.cattle.domain.interactor.AddCostUseCase;
import com.leo.cattle.domain.interactor.DefaultSubscriber;
import com.leo.cattle.domain.interactor.UseCase;
import com.leo.cattle.presentation.exception.ErrorMessageFactory;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.mapper.CattleModelDataMapper;
import com.leo.cattle.presentation.mapper.CostModelDataMapper;
import com.leo.cattle.presentation.model.CattleModel;
import com.leo.cattle.presentation.model.CostModel;
import com.leo.cattle.presentation.view.AddCattleMvpView;
import com.leo.cattle.presentation.view.AddCostMvpView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by leo on 4/1/2016.
 */

@PerActivity
public class AddCostPresenter  implements Presenter{

    private AddCostMvpView signUpview;

    private final UseCase addCostUseCase;
    private final CostModelDataMapper costModelDataMapper;

    @Inject
    public AddCostPresenter(@Named("addCost") UseCase addCostUseCase,
                            CostModelDataMapper costModelDataMapper) {
        this.addCostUseCase = addCostUseCase;
        this.costModelDataMapper = costModelDataMapper;
    }

    public void setView(@NonNull AddCostMvpView view) {
        this.signUpview = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.addCostUseCase.unsubscribe();
        this.signUpview = null;
    }

    public void initialize(String token, Cost cattle) {
        ((AddCostUseCase)addCostUseCase).passParamForNewCost(token, cattle);
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

    private void showUserSuccess(Cost user) {
        final CostModel userModel =
                this.costModelDataMapper.transform(user);
        this.signUpview.newCostSuccess(userModel);
    }

    private void newCattle() {
        this.addCostUseCase.execute(new SignUpSubscriber());
    }

    private final class SignUpSubscriber extends DefaultSubscriber<Cost> {

        @Override public void onCompleted() {
            AddCostPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            AddCostPresenter.this.hideViewLoading();
            AddCostPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            AddCostPresenter.this.showViewRetry();
        }

        @Override public void onNext(Cost user) {
            AddCostPresenter.this.showUserSuccess(user);
        }
    }
}
