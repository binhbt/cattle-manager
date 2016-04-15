package com.leo.cattle.data.entity.mapper;

import com.leo.cattle.data.entity.CostEntity;
import com.leo.cattle.data.entity.UserEntity;
import com.leo.cattle.domain.Cost;
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
public class CostEntityDataMapper {

    @Inject
    public CostEntityDataMapper() {}

    /**
     * Transform a {@link UserEntity} into an {@link User}.
     *
     * @param userEntity Object to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    public Cost transform(CostEntity userEntity) {
        Cost user = null;
        if (userEntity != null) {
            user = new Cost();
            user.setId(userEntity.getId());
            user.setName(userEntity.getName());
            user.setDesCription(userEntity.getDesCription());
            user.setCost(userEntity.getCost());
            user.setDate(userEntity.getDate());
            user.setAuthorId(userEntity.getAuthorId());
        }

        return user;
    }

    /**
     * Transform a List of {@link UserEntity} into a Collection of {@link User}.
     *
     * @param userEntityCollection Object Collection to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    public List<Cost> transform(Collection<CostEntity> userEntityCollection) {
        List<Cost> userList = new ArrayList<>();
        Cost user;
        for (CostEntity userEntity : userEntityCollection) {
            user = transform(userEntity);
            if (user != null) {
                userList.add(user);
            }
        }

        return userList;
    }
}
