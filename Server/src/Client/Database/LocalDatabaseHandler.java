package Client.Database;

import Contract.DTO.Message;

import java.util.List;

public class LocalDatabaseHandler {

    public static void addMessageToConversation(String userName, String friendName, Message message) {

        Database.INSTANCE.conversation.add(message);
    }

    public static void addMultipleMessagesToConversation(
            String userName,
            String friendName,
            List<Message> messages) {

        Database.INSTANCE.conversation.addAll(messages);
    }

    public static boolean isConversationEmpty(String userName, String friendName) {

        if (Database.INSTANCE.conversation == null || Database.INSTANCE.conversation.size() == 0)
            return true;
        else return false;
    }

    public static int getLastConversationMessageId(String userName, String friendName) {

        int lastMessageIndex = Database.INSTANCE.conversation.size() - 1;

        return Database.INSTANCE.conversation.get(lastMessageIndex).getMessageId();
    }
}
