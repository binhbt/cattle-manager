package com.leo.cattle.data.entity.mapper;

import com.leo.cattle.data.entity.UserEntity;
import com.leo.cattle.data.entity.WeightEntity;
import com.leo.cattle.domain.User;
import com.leo.cattle.domain.Weight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by leo on 4/1/2016.
 */

@Singleton
public class WeightEntityDataMapper {

    @Inject
    public WeightEntityDataMapper() {}

    /**
     * Transform a {@link UserEntity} into an {@link User}.
     *
     * @param userEntity Object to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    public Weight transform(WeightEntity userEntity) {
        Weight user = null;
        if (userEntity != null) {
            user = new Weight();
            user.setId(userEntity.getId());
            user.setDesCription(userEntity.getDesCription());
            user.setName(userEntity.getName());
            user.setDesCription(userEntity.getDesCription());
            user.setAuthorId(userEntity.getAuthorId());
            user.setWeight(userEntity.getWeight());
            user.setDate(userEntity.getDate());
        }

        return user;
    }

    /**
     * Transform a List of {@link UserEntity} into a Collection of {@link User}.
     *
     * @param userEntityCollection Object Collection to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    public List<Weight> transform(Collection<WeightEntity> userEntityCollection) {
        List<Weight> userList = new ArrayList<>();
        Weight user;
        for (WeightEntity userEntity : userEntityCollection) {
            user = transform(userEntity);
            if (user != null) {
                userList.add(user);
            }
        }

        return userList;
    }
}
