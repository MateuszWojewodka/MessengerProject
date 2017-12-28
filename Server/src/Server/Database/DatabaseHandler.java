package Server.Database;

import Contract.DTO.Credentials;
import Contract.DTO.Message;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    public static void addUserToRegisteredUsers(Credentials credentials){
        Database.INSTANCE.registeredUsers.put(
                credentials.username,
                credentials.password);
    }

    public static boolean isUserInRegisteredUsers(String username) {
        return Database.INSTANCE.registeredUsers.containsKey(username);
    }

    public static void addUserToLoggedUsers(String token, Credentials credentials) {
        Database.INSTANCE.loggedUsers.put(token, credentials);
    }

    public static void removeUserFromLoggedUsers(String token) {
        Database.INSTANCE.loggedUsers.remove(token);
    }

    public static boolean isTokenInUseByLoggedUser(String token) {
        return Database.INSTANCE.loggedUsers.containsKey(token);
    }

    public static String getUsernameFromToken(String token) {
        return Database.INSTANCE.loggedUsers.get(token).username;
    }

    public static String checkIfUserIsLoggedOnAndGetToken(Credentials credentials) {
        for (String token : Database.INSTANCE.loggedUsers.keySet()) {
            if (Database.INSTANCE.loggedUsers.get(token).equals(credentials))
                return token;
        }
        return null;
    }

    public static void addMessageToConversation(String sender, String receiver, String message) {

        if (!isConversationBetweenUsersAlreadyInDatabase(sender, receiver))
            createConversation(sender, receiver);

        List<Message> conversation =
                Database.INSTANCE.conversationsWithFriends.get(new UsersPair(sender, receiver));

        int messageId = conversation.size();

        conversation.add(new Message(
                messageId,
                message,
                sender,
                receiver
        ));
    }

    public static List<Message> getConversationMessagesFromLatest(String firstUser, String secondUser, int count) {

        List<Message> result = new ArrayList<>();

        List<Message> conversation =
                Database.INSTANCE.conversationsWithFriends.get(new UsersPair(firstUser, secondUser));
        if (conversation != null) {
            int lastMessageIndex = conversation.size() - 1;
            result = getConversationMessagesFromSpecifiedOneInRightOrder(conversation, lastMessageIndex, count);
        }
        return result;
    }

    public static List<Message> getConversationMessagesFromLatestToSpecified(String firstUser, String secondUser, int specifiedMessageId) {

        List<Message> result = new ArrayList<>();

        List<Message> conversation =
                Database.INSTANCE.conversationsWithFriends.get(new UsersPair(firstUser, secondUser));
        if (conversation != null) {
            result = getConversationMessagesFromLatestToSpecifiedInRightOrder(conversation, specifiedMessageId);
        }
        return result;
    }

    public static List<Message> getConversationMessagesFromSpecifiedOne(String firstUser, String secondUser, int lastMessageIndex, int count) {

        List<Message> result = new ArrayList<>();

        List<Message> conversation =
                Database.INSTANCE.conversationsWithFriends.get(new UsersPair(firstUser, secondUser));
        if (conversation != null) {
            result = getConversationMessagesFromSpecifiedOneInRightOrder(conversation, lastMessageIndex, count);
        }
        return result;
    }

    private static boolean isConversationBetweenUsersAlreadyInDatabase(String firstUser, String secondUser) {

        return Database.INSTANCE.conversationsWithFriends.containsKey(
                new UsersPair(firstUser, secondUser));
    }

    private static void createConversation(String firstUser, String secondUser) {
        Database.INSTANCE.conversationsWithFriends.put(
                new UsersPair(firstUser,secondUser),
                new ArrayList<>());
    }

    private static List<Message> getConversationMessagesFromLatestToSpecifiedInRightOrder(
            List<Message> conversation,
            int messageToIndex) {

        List<Message> messagesToReturn = new ArrayList<>();
        if (conversation == null)
            return messagesToReturn;

        int lastElementIndex = conversation.size() - 1;

        while(lastElementIndex > 0 && lastElementIndex != messageToIndex) {

            Message msg = conversation.get(lastElementIndex);
            messagesToReturn.add(0, msg);
            lastElementIndex--;
        }

        return messagesToReturn;
    }

    private static List<Message> getConversationMessagesFromSpecifiedOneInRightOrder(
            List<Message> conversation,
            int specifiedMessageIndex,
            int count) {

        List<Message> messagesToReturn = new ArrayList<>();
        if (conversation == null)
            return messagesToReturn;

        int index = specifiedMessageIndex;
        for(int i = 0; i<count; i++) {

            if (index < 0) break;

            Message msg = conversation.get(index);
            messagesToReturn.add(0,msg);
            index--;
        }

        return messagesToReturn;
    }
}
