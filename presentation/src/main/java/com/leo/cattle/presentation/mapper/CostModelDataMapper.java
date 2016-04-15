package com.leo.cattle.presentation.mapper;

import com.leo.cattle.domain.Cost;
import com.leo.cattle.domain.User;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.model.CostModel;
import com.leo.cattle.presentation.model.UserModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by leo on 4/1/2016.
 */

@PerActivity
public class CostModelDataMapper {

    @Inject
    public CostModelDataMapper() {}

    /**
     * Transform a {@link User} into an {@link UserModel}.
     *
     * @param user Object to be transformed.
     * @return {@link UserModel}.
     */
    public CostModel transform(Cost user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CostModel userModel = new CostModel();
        userModel.setId(user.getId());
        userModel.setName(user.getName());
        userModel.setDesCription(user.getDesCription());
        userModel.setCost(user.getCost());
        userModel.setDate(user.getDate());
        userModel.setAuthorId(user.getAuthorId());
        return userModel;
    }

    /**
     * Transform a Collection of {@link User} into a Collection of {@link UserModel}.
     *
     * @param usersCollection Objects to be transformed.
     * @return List of {@link UserModel}.
     */
    public Collection<CostModel> transform(Collection<Cost> usersCollection) {
        Collection<CostModel> userModelsCollection;

        if (usersCollection != null && !usersCollection.isEmpty()) {
            userModelsCollection = new ArrayList<>();
            for (Cost user : usersCollection) {
                userModelsCollection.add(transform(user));
            }
        } else {
            userModelsCollection = Collections.emptyList();
        }

        return userModelsCollection;
    }
}