package com.leo.cattle.data.entity.mapper;

import com.leo.cattle.data.entity.CattleEntity;
import com.leo.cattle.data.entity.UserEntity;
import com.leo.cattle.domain.Cattle;
import com.leo.cattle.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by leo on 4/1/2016.
 */

@Singleton
public class CattleEntityDataMapper {

    @Inject
    public CattleEntityDataMapper() {}

    /**
     * Transform a {@link UserEntity} into an {@link User}.
     *
     * @param userEntity Object to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    public Cattle transform(CattleEntity userEntity) {
        EventEntityDataMapper eventEntityDataMapper = new EventEntityDataMapper();
        WeightEntityDataMapper weightEntityDataMapper = new WeightEntityDataMapper();
        Cattle user = null;
        if (userEntity != null) {
            user = new Cattle();
            user.setId(userEntity.getId());
            user.setName(userEntity.getName());
            user.setDesCription(userEntity.getDesCription());
            user.setBlood(userEntity.getBlood());
            user.setWeight(userEntity.getWeight());
            user.setBuyDate(userEntity.getBuyDate());
            user.setMonthOld(userEntity.getMonthOld());
            user.setCost(userEntity.getCost());
            user.setStatus(userEntity.getStatus());
            user.setSaleDate(userEntity.getSaleDate());
            user.setSalePrice(userEntity.getSalePrice());
            user.setSaleStatus(userEntity.getSaleStatus());
            //user.setName(userEntity.getName());
            user.setEvents(eventEntityDataMapper.transform(userEntity.getEvents()));
            user.setWeights(weightEntityDataMapper.transform(userEntity.getWeights()));
        }

        return user;
    }

    /**
     * Transform a List of {@link UserEntity} into a Collection of {@link User}.
     *
     * @param userEntityCollection Object Collection to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    public List<Cattle> transform(Collection<CattleEntity> userEntityCollection) {
        List<Cattle> userList = new ArrayList<>();
        Cattle user;
        for (CattleEntity userEntity : userEntityCollection) {
            user = transform(userEntity);
            if (user != null) {
                userList.add(user);
            }
        }

        return userList;
    }
}
