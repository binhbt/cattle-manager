package com.leo.cattle.presentation.mapper;

import com.leo.cattle.domain.Cost;
import com.leo.cattle.domain.Event;
import com.leo.cattle.domain.User;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.model.CostModel;
import com.leo.cattle.presentation.model.EventModel;
import com.leo.cattle.presentation.model.UserModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by leo on 4/1/2016.
 */

@PerActivity
public class EventModelDataMapper {

    @Inject
    public EventModelDataMapper() {}


    public EventModel transform(Event user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        EventModel userModel = new EventModel();
        userModel.setId(user.getId());
        userModel.setCattleId(user.getCattleId());
        userModel.setName(user.getName());
        userModel.setDesCription(user.getDesCription());
        userModel.setCost(user.getCost());
        userModel.setDate(user.getDate());
        userModel.setAuthorId(user.getAuthorId());
        return userModel;
    }


    public List<EventModel> transform(Collection<Event> usersCollection) {
        List<EventModel> userModelsCollection;

        if (usersCollection != null && !usersCollection.isEmpty()) {
            userModelsCollection = new ArrayList<>();
            for (Event user : usersCollection) {
                userModelsCollection.add(transform(user));
            }
        } else {
            userModelsCollection = Collections.emptyList();
        }

        return userModelsCollection;
    }
}