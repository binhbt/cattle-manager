package com.leo.cattle.data.entity.mapper;

import com.leo.cattle.data.entity.EventEntity;
import com.leo.cattle.data.entity.UserEntity;
import com.leo.cattle.domain.Event;
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
public class EventEntityDataMapper {

    @Inject
    public EventEntityDataMapper() {}

    /**
     * Transform a {@link UserEntity} into an {@link User}.
     *
     * @param userEntity Object to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    public Event transform(EventEntity userEntity) {
        Event user = null;
        if (userEntity != null) {
            user = new Event();
            user.setId(userEntity.getId());
            user.setDesCription(userEntity.getDesCription());
            user.setCattleId(userEntity.getCattleId());
            user.setAuthorId(userEntity.getAuthorId());
            user.setCost(userEntity.getCost());
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
    public List<Event> transform(Collection<EventEntity> userEntityCollection) {
        List<Event> userList = new ArrayList<>();
        Event user;
        for (EventEntity userEntity : userEntityCollection) {
            user = transform(userEntity);
            if (user != null) {
                userList.add(user);
            }
        }

        return userList;
    }
}
