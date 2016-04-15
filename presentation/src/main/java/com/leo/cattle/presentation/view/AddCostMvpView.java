package com.leo.cattle.presentation.view;

import com.leo.cattle.presentation.model.CostModel;

/**
 * Created by leo on 4/1/2016.
 */
public interface AddCostMvpView  extends LoadDataView{
    public void newCostSuccess(CostModel cattleModel);
}
