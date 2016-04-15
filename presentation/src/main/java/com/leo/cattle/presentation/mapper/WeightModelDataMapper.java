package com.leo.cattle.presentation.mapper;

import com.leo.cattle.domain.User;
import com.leo.cattle.domain.Weight;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.model.UserModel;
import com.leo.cattle.presentation.model.WeightModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by leo on 4/1/2016.
 */

@PerActivity
public class WeightModelDataMapper {

    @Inject
    public WeightModelDataMapper() {}


    public WeightModel transform(Weight user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        WeightModel userModel = new WeightModel();
        userModel.setId(user.getId());
        userModel.setDesCription(user.getDesCription());
        userModel.setName(user.getName());
        userModel.setDesCription(user.getDesCription());
        userModel.setAuthorId(user.getAuthorId());
        userModel.setWeight(user.getWeight());
        userModel.setDate(user.getDate());
        return userModel;
    }


    public List<WeightModel> transform(Collection<Weight> usersCollection) {
        List<WeightModel> userModelsCollection;

        if (usersCollection != null && !usersCollection.isEmpty()) {
            userModelsCollection = new ArrayList<>();
            for (Weight user : usersCollection) {
                userModelsCollection.add(transform(user));
            }
        } else {
            userModelsCollection = Collections.emptyList();
        }

        return userModelsCollection;
    }
}
