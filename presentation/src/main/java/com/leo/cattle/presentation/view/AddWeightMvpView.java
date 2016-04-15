package com.leo.cattle.presentation.view;

import com.leo.cattle.presentation.model.EventModel;
import com.leo.cattle.presentation.model.WeightModel;

/**
 * Created by leo on 4/1/2016.
 */

public interface AddWeightMvpView  extends LoadDataView{
    public void newWeightSuccess(WeightModel cattleModel);
}