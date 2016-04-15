package com.leo.cattle.presentation.mapper;

import com.leo.cattle.domain.Message;
import com.leo.cattle.presentation.model.MessageModel;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by leobui on 3/17/2016.
 */
public class MessageModelDataMapper {

    @Inject
    public MessageModelDataMapper() {}


    public MessageModel transform(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        MessageModel messageModel = new MessageModel();
        messageModel.setId(message.getId());
        messageModel.setMessage(message.getMessage());
        messageModel.setStatus(message.getStatus());
        return messageModel;
    }


    public Collection<MessageModel> transform(Collection<Message> messages) {
        Collection<MessageModel> messageModels;

        if (messages != null && !messages.isEmpty()) {
            messageModels = new ArrayList<>();
            for (Message message : messages) {
                messageModels.add(transform(message));
            }
        } else {
            messageModels = Collections.emptyList();
        }

        return messageModels;
    }
}
