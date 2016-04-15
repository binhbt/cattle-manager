package com.leo.cattle.presentation.mapper;

import com.leo.cattle.domain.Cattle;
import com.leo.cattle.domain.User;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.model.CattleModel;
import com.leo.cattle.presentation.model.UserModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by leo on 4/1/2016.
 */
@PerActivity
public class CattleModelDataMapper {

    @Inject
    public CattleModelDataMapper() {}

    /**
     * Transform a {@link User} into an {@link UserModel}.
     *
     * @param user Object to be transformed.
     * @return {@link UserModel}.
     */
    public CattleModel transform(Cattle user) {
        EventModelDataMapper eventModelDataMapper = new EventModelDataMapper();
        WeightModelDataMapper weightModelDataMapper = new WeightModelDataMapper();
        if (user == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CattleModel userModel = new CattleModel();
        userModel.setId(user.getId());
        userModel.setName(user.getName());
        userModel.setDesCription(user.getDesCription());
        userModel.setBlood(user.getBlood());
        userModel.setWeight(user.getWeight());
        userModel.setBuyDate(user.getBuyDate());
        userModel.setMonthOld(user.getMonthOld());
        userModel.setCost(user.getCost());
        userModel.setStatus(user.getStatus());
        userModel.setSaleDate(user.getSaleDate());
        userModel.setSalePrice(user.getSalePrice());
        userModel.setSaleStatus(user.getSaleStatus());
        //userModel.setName(user.getName());
        userModel.setEvents(eventModelDataMapper.transform(user.getEvents()));
        userModel.setWeights(weightModelDataMapper.transform(user.getWeights()));
        return userModel;
    }

    /**
     * Transform a Collection of {@link User} into a Collection of {@link UserModel}.
     *
     * @param usersCollection Objects to be transformed.
     * @return List of {@link UserModel}.
     */
    public Collection<CattleModel> transform(Collection<Cattle> usersCollection) {
        Collection<CattleModel> userModelsCollection;

        if (usersCollection != null && !usersCollection.isEmpty()) {
            userModelsCollection = new ArrayList<>();
            for (Cattle user : usersCollection) {
                userModelsCollection.add(transform(user));
            }
        } else {
            userModelsCollection = Collections.emptyList();
        }

        return userModelsCollection;
    }
}
