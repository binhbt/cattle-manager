package com.leo.cattle.data.entity.mapper;

import com.leo.cattle.data.entity.MessageEntity;
import com.leo.cattle.data.entity.UserEntity;
import com.leo.cattle.domain.Message;
import com.leo.cattle.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by leo on 3/31/2016.
 */
@Singleton
public class MessageEntityDataMapper {

    @Inject
    public MessageEntityDataMapper() {}

    /**
     * Transform a {@link UserEntity} into an {@link User}.
     *
     * @param userEntity Object to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    public Message transform(MessageEntity userEntity) {
        Message user = null;
        if (userEntity != null) {
            user = new Message();
            user.setId(userEntity.getId());
            user.setMessage(userEntity.getMessage());
            user.setStatus(userEntity.getStatus());
        }

        return user;
    }

    /**
     * Transform a List of {@link UserEntity} into a Collection of {@link User}.
     *
     * @param userEntityCollection Object Collection to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    public List<Message> transform(Collection<MessageEntity> userEntityCollection) {
        List<Message> userList = new ArrayList<>();
        Message user;
        for (MessageEntity userEntity : userEntityCollection) {
            user = transform(userEntity);
            if (user != null) {
                userList.add(user);
            }
        }

        return userList;
    }
}
