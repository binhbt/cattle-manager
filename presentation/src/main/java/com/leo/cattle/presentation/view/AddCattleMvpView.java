package com.leo.cattle.presentation.view;

import com.leo.cattle.presentation.model.CattleModel;
import com.leo.cattle.presentation.model.UserModel;

/**
 * Created by leo on 4/1/2016.
 */
public interface AddCattleMvpView extends LoadDataView{
    public void newCattleSuccess(CattleModel cattleModel);
}
