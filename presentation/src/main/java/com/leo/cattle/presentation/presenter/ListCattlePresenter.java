package com.leo.cattle.presentation.presenter;

import android.support.annotation.NonNull;

import com.leo.cattle.domain.Cattle;
import com.leo.cattle.domain.exception.DefaultErrorBundle;
import com.leo.cattle.domain.exception.ErrorBundle;
import com.leo.cattle.domain.interactor.DefaultSubscriber;
import com.leo.cattle.domain.interactor.GetListCattle;
import com.leo.cattle.domain.interactor.UseCase;
import com.leo.cattle.presentation.AndroidApplication;
import com.leo.cattle.presentation.exception.ErrorMessageFactory;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.mapper.CattleModelDataMapper;
import com.leo.cattle.presentation.model.CattleModel;
import com.leo.cattle.presentation.view.ListCattleMvpView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by leo on 4/1/2016.
 */

@PerActivity
public class ListCattlePresenter implements Presenter {

    private ListCattleMvpView viewListView;

    private final UseCase getUserListUseCase;
    private final CattleModelDataMapper userModelDataMapper;

    @Inject
    public ListCattlePresenter(@Named("cattleList") UseCase getUserListUserCase,
                               CattleModelDataMapper userModelDataMapper) {
        this.getUserListUseCase = getUserListUserCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void setView(@NonNull ListCattleMvpView view) {
        this.viewListView = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.getUserListUseCase.unsubscribe();
        this.viewListView = null;
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        ((GetListCattle)this.getUserListUseCase).setAccessToken(AndroidApplication.sessionUser.getAccessToken());
        this.loadUserList();
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserList();
    }

    public void onUserClicked(CattleModel userModel) {
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

    private void showUsersCollectionInView(Collection<Cattle> usersCollection) {
        final Collection<CattleModel> userModelsCollection =
                this.userModelDataMapper.transform(usersCollection);
        this.viewListView.renderUserList(userModelsCollection);
    }

    private void getUserList() {
        this.getUserListUseCase.execute(new UserListSubscriber());
    }

    private final class UserListSubscriber extends DefaultSubscriber<List<Cattle>> {

        @Override public void onCompleted() {
            ListCattlePresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            ListCattlePresenter.this.hideViewLoading();
            ListCattlePresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            ListCattlePresenter.this.showViewRetry();
        }

        @Override public void onNext(List<Cattle> users) {
            ListCattlePresenter.this.showUsersCollectionInView(users);
        }
    }
}
