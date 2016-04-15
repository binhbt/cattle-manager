package com.leo.cattle.data.entity.mapper;

import com.leo.cattle.data.entity.ChatSessionEntity;
import com.leo.cattle.domain.ChatSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by leobui on 3/16/2016.
 */

@Singleton
public class ChatSessionEntityDataMapper {

    @Inject
    public ChatSessionEntityDataMapper() {}


    public ChatSession transform(ChatSessionEntity chatSessionEntity) {
        ChatSession chatSession = null;
        if (chatSession== null){
            chatSession = new ChatSession();
            chatSession.setId(chatSessionEntity.getId());
            chatSession.setName(chatSessionEntity.getName());
            chatSession.setStatus(chatSessionEntity.getStatus());
            chatSession.setTopic(chatSessionEntity.getTopic());
            chatSession.setType(chatSessionEntity.getType());
        }

        return chatSession;
    }


    public List<ChatSession> transform(Collection<ChatSessionEntity> userEntityCollection) {
        List<ChatSession> userList = new ArrayList<>();
        ChatSession user;
        for (ChatSessionEntity userEntity : userEntityCollection) {
            user = transform(userEntity);
            if (user != null) {
                userList.add(user);
            }
        }

        return userList;
    }
}
