package com.leo.cattle.presentation.view;

import com.leo.cattle.presentation.model.CostModel;
import com.leo.cattle.presentation.model.EventModel;

/**
 * Created by leo on 4/1/2016.
 */
public interface AddEventMvpView  extends LoadDataView{
    public void newEventSuccess(EventModel cattleModel);
}
