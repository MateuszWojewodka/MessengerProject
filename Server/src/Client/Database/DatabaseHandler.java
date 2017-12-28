package Client.Database;

import Client.Database.Database;
import Contract.DTO.Message;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    public static void addMessageToConversation(String friendName, Message message) {

        createConversationIfDoesNotExist(friendName);
        Database.INSTANCE.conversations.get(friendName).add(message);
    }

    public static void addMultipleMessagesToConversation(
            String friendName,
            List<Message> messages) {

        createConversationIfDoesNotExist(friendName);
        Database.INSTANCE.conversations.get(friendName).addAll(messages);
    }

    public static boolean isConversationEmpty(String friendName) {

        createConversationIfDoesNotExist(friendName);
        if(Database.INSTANCE.conversations.get(friendName) == null ||
           Database.INSTANCE.conversations.get(friendName).size() == 0)
            return true;
        else return false;
    }

    public static int getLastConversationMessageId(String friendName) {

        createConversationIfDoesNotExist(friendName);
        int lastMessageIndex = Database.INSTANCE.conversations.get(friendName).size() - 1;

        return Database.INSTANCE.conversations.get(friendName).get(lastMessageIndex).getMessageId();
    }

    private static void createConversationIfDoesNotExist(String friendName) {

        if (!Database.INSTANCE.conversations.containsKey(friendName))
            Database.INSTANCE.conversations.put(friendName, new ArrayList<>());
    }
}
