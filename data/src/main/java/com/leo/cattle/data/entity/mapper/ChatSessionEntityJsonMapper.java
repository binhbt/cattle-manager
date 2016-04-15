package com.leo.cattle.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.leo.cattle.data.entity.ChatSessionEntity;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by leobui on 3/16/2016.
 */

public class ChatSessionEntityJsonMapper {

    private final Gson gson;

    @Inject
    public ChatSessionEntityJsonMapper() {
        this.gson = new Gson();
    }


    public ChatSessionEntity transformChatSessionEntity(String userJsonResponse) throws JsonSyntaxException {
        try {
            Type userEntityType = new TypeToken<ChatSessionEntity>() {}.getType();
            ChatSessionEntity userEntity = this.gson.fromJson(userJsonResponse, userEntityType);

            return userEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    public List<ChatSessionEntity> transformChatSessionEntityCollection(String userListJsonResponse)
            throws JsonSyntaxException {

        List<ChatSessionEntity> userEntityCollection;
        try {
            Type listOfUserEntityType = new TypeToken<List<ChatSessionEntity>>() {}.getType();
            userEntityCollection = this.gson.fromJson(userListJsonResponse, listOfUserEntityType);

            return userEntityCollection;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
