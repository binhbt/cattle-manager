package com.leo.cattle.presentation.view;

import com.leo.cattle.presentation.model.CattleModel;

import java.util.Collection;

/**
 * Created by leo on 4/1/2016.
 */
public interface ListCattleMvpView extends LoadDataView{

    void renderUserList(Collection<CattleModel> userModelCollection);

    void viewUser(CattleModel userModel);

}
