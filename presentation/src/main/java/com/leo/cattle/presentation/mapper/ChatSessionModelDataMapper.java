package com.leo.cattle.presentation.mapper;

import com.leo.cattle.domain.ChatSession;
import com.leo.cattle.presentation.internal.di.PerActivity;
import com.leo.cattle.presentation.model.ChatSessionModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by leobui on 3/16/2016.
 */

@PerActivity
public class ChatSessionModelDataMapper {

    @Inject
    public ChatSessionModelDataMapper() {}


    public ChatSessionModel transform(ChatSession chatSession) {
        if (chatSession == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ChatSessionModel chatSessionModel = new ChatSessionModel();
        chatSessionModel.setId(chatSession.getId());
        chatSessionModel.setName(chatSession.getName());
        chatSessionModel.setType(chatSession.getType());
        chatSessionModel.setTopic(chatSession.getTopic());
        chatSessionModel.setStatus(chatSession.getStatus());
        chatSessionModel.setOwnerId(chatSession.getOwnerId());

        return chatSessionModel;
    }


    public Collection<ChatSessionModel> transform(Collection<ChatSession> usersCollection) {
        Collection<ChatSessionModel> userModelsCollection;

        if (usersCollection != null && !usersCollection.isEmpty()) {
            userModelsCollection = new ArrayList<>();
            for (ChatSession user : usersCollection) {
                userModelsCollection.add(transform(user));
            }
        } else {
            userModelsCollection = Collections.emptyList();
        }

        return userModelsCollection;
    }
}
