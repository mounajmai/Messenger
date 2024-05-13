package com.example.messenger.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatroomModel {
    String chatroomModel;
    List<String> userIds;
    Timestamp LastMessageTimestamp;
    String LastMessageSenderId;
    public ChatroomModel(){

    }

    public ChatroomModel(String chatroomModel, List<String> userIds, Timestamp lastMessageTimestamp, String lastMessageSenderId) {
        this.chatroomModel = chatroomModel;
        this.userIds = userIds;
        LastMessageTimestamp = lastMessageTimestamp;
        LastMessageSenderId = lastMessageSenderId;
    }

    public String getChatroomModel() {
        return chatroomModel;
    }

    public void setChatroomModel(String chatroomModel) {
        this.chatroomModel = chatroomModel;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Timestamp getLastMessageTimestamp() {
        return LastMessageTimestamp;
    }

    public void setLastMessageTimestamp(Timestamp lastMessageTimestamp) {
        LastMessageTimestamp = lastMessageTimestamp;
    }

    public String getLastMessageSenderId() {
        return LastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        LastMessageSenderId = lastMessageSenderId;
    }
}
