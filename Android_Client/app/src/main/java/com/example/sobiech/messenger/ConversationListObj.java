package com.example.sobiech.messenger;

/**
 * Created by WOJTEK on 2017-12-28.
 */

public class ConversationListObj {
    private String username;
    private String lastMessageContent;
    private String lastMessageSender;
    private String lastMessageTime;
    private boolean read;

    public String getUsername() {
        return username;
    }

    public String getLastMessageContent() {
        return lastMessageContent;
    }

    public String getLastMessageSender() {
        return lastMessageSender;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public boolean isRead() {
        return read;
    }
}
