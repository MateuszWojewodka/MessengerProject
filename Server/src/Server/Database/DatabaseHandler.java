package Server.Database;

import Contract.DTO.Credentials;
import Contract.DTO.Message;
import Contract.DTO.Notifications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {

    public static void addNewMessageNotificationToUserProfile(String userName, String messageSender) throws Exception {
        if(!Database.INSTANCE.usersProfiles.containsKey(userName))
            throw new Exception("User doesn't have profile created. Register user at first.");
        Database.INSTANCE.usersProfiles.get(userName).addNewMessageNotification(messageSender);
    }

    public static void addFriendRequestToUserProfile(String userName, String requestingUser) throws Exception{
        if(!Database.INSTANCE.usersProfiles.containsKey(userName))
            throw new Exception("User doesn't have profile created. Register user at first.");

        Database.INSTANCE.usersProfiles.get(userName).addFriendRequest(requestingUser);
    }

    public static void addFriendToUser(String userName, String friendName) throws Exception {
        if(!Database.INSTANCE.usersProfiles.containsKey(userName))
            throw new Exception("User doesn't have profile created. Register user at first.");

        Database.INSTANCE.usersProfiles.get(userName).addFriend(friendName);
    }

    public static void addUserToRegisteredUsersAndCreateHisProfile(Credentials credentials){
        Database.INSTANCE.registeredUsers.put(
                credentials.username,
                credentials.password);
        createUserProfile(credentials.username);
    }

    public static void addUserToLoggedUsers(String token, Credentials credentials) {
        Database.INSTANCE.loggedUsers.put(token, credentials);
    }

    public static void addMessageToConversation(String sender, String receiver, String message) {

        if (!isConversationBetweenUsersAlreadyInDatabase(sender, receiver))
            createConversation(sender, receiver);

        Map<Integer, Message> conversation =
                Database.INSTANCE.conversationsWithFriends.get(new UsersPair(sender, receiver));

        int messageId = conversation.size();

        conversation.put(messageId, new Message(
                messageId,
                message,
                sender,
                receiver
        ));
    }

    public static void removeNewMessageNotificationFromUserProfile(String userName, String messageSender) throws Exception {
        if(!Database.INSTANCE.usersProfiles.containsKey(userName))
            throw new Exception("User doesn't have profile created. Register user at first.");
        Database.INSTANCE.usersProfiles.get(userName).removeNewMessageNotification(messageSender);
    }

    public static void removeFriendRequestFromUserProfile(String userName, String requestingUser) throws Exception {
        if(!Database.INSTANCE.usersProfiles.containsKey(userName))
            throw new Exception("User doesn't have profile created. Register user at first.");

        Database.INSTANCE.usersProfiles.get(userName).removeFriendRequest(requestingUser);
    }

    public static void removeFriendFromUser(String userName, String friendName) throws Exception {
        if(!Database.INSTANCE.usersProfiles.containsKey(userName))
            throw new Exception("User doesn't have profile created. Register user at first.");

        Database.INSTANCE.usersProfiles.get(userName).removeFriend(friendName);
    }
    public static void removeUserFromLoggedUsers(String token) {
        Database.INSTANCE.loggedUsers.remove(token);
    }

    public static boolean isUserOnFriendListOf(String userName, String userWhichFriendListWillBeChecked) throws Exception {
        if(!Database.INSTANCE.usersProfiles.containsKey(userWhichFriendListWillBeChecked))
            throw new Exception("User doesn't have profile created. Register user at first.");

        return Database.INSTANCE.usersProfiles.get(userName).friends.contains(userWhichFriendListWillBeChecked);
    }

    public static boolean isFriendRequestInUsersNotifications(String userName, String requestingUser) throws Exception {
        if(!Database.INSTANCE.usersProfiles.containsKey(userName))
            throw new Exception("User doesn't have profile created. Register user at first.");

        return Database.INSTANCE.usersProfiles.get(userName).isFriendRequestInNotifications(requestingUser);
    }

    public static boolean isUserInRegisteredUsers(String username) {
        return Database.INSTANCE.registeredUsers.containsKey(username);
    }

    public static boolean isTokenInUseByLoggedUser(String token) {
        return Database.INSTANCE.loggedUsers.containsKey(token);
    }

    public static String checkIfUserIsLoggedOnAndGetToken(Credentials credentials) {
        for (String token : Database.INSTANCE.loggedUsers.keySet()) {
            if (Database.INSTANCE.loggedUsers.get(token).equals(credentials))
                return token;
        }
        return null;
    }

    public static Notifications getNotifications(String userName) throws Exception {

        if(!Database.INSTANCE.usersProfiles.containsKey(userName))
            throw new Exception("User doesn't have profile created. Register user at first.");

        return Database.INSTANCE.usersProfiles.get(userName).getNotification();
    }

    public static String[] getFriendList(String userName) throws Exception {
        if(!Database.INSTANCE.usersProfiles.containsKey(userName))
            throw new Exception("User doesn't have profile created. Register user at first.");

        return Database.INSTANCE.usersProfiles.get(userName).friends.toArray(new String[0]);
    }

    public static String getUsernameFromToken(String token) throws Exception{

        if(!Database.INSTANCE.loggedUsers.containsKey(token))
            throw new Exception("User is not logged on");

        return Database.INSTANCE.loggedUsers.get(token).username;
    }

    public static UsersPair[] getConversationUsersPairList() {

        return Database.INSTANCE.conversationsWithFriends.keySet().toArray(new UsersPair[0]);
    }

    public static Message getLastMessageFromConversation(String firstUser, String secondUser) {

        Message result = new Message();

        Map<Integer, Message> conversation =
                Database.INSTANCE.conversationsWithFriends.get(new UsersPair(firstUser, secondUser));

        if(conversation != null) {
            int lastMessageId = conversation.size() - 1;
            result  = conversation.get(lastMessageId);
        }
        return result;
    }

    public static Message getLastMessageFromConversation(UsersPair usersPair) {

        Message result = new Message();

        Map<Integer, Message> conversation =
                Database.INSTANCE.conversationsWithFriends.get(usersPair);

        if(conversation != null) {
            int lastMessageId = conversation.size() - 1;
            result  = conversation.get(lastMessageId);
        }
        return result;
    }

    public static Message getMessageFromConversationById(String firstUser, String secondUser, int messageId) {

        Message result = new Message();

        Map<Integer, Message> conversation =
                Database.INSTANCE.conversationsWithFriends.get(new UsersPair(firstUser, secondUser));

        if (conversation != null) result = conversation.get(messageId);

        return result;
    }

    public static List<Message> getConversationMessagesFromLatest(String firstUser, String secondUser, int count) {

        List<Message> result = new ArrayList<>();

        Map<Integer, Message> conversation =
                Database.INSTANCE.conversationsWithFriends.get(new UsersPair(firstUser, secondUser));
        if (conversation != null) {
            int lastMessageId = conversation.size() - 1;
            result = getConversationMessagesFromSpecifiedOneInRightOrder(conversation, lastMessageId, count);
        }
        return result;
    }

    public static List<Message> getConversationMessagesFromLatestToSpecified(String firstUser, String secondUser, int specifiedMessageId) {

        List<Message> result = new ArrayList<>();

        Map<Integer, Message> conversation =
                Database.INSTANCE.conversationsWithFriends.get(new UsersPair(firstUser, secondUser));
        if (conversation != null) {
            result = getConversationMessagesFromLatestToSpecifiedInRightOrder(conversation, specifiedMessageId);
        }
        return result;
    }

    public static List<Message> getConversationMessagesFromSpecifiedOne(String firstUser, String secondUser, int lastMessageId, int count) {

        List<Message> result = new ArrayList<>();

        Map<Integer, Message> conversation =
                Database.INSTANCE.conversationsWithFriends.get(new UsersPair(firstUser, secondUser));
        if (conversation != null) {
            result = getConversationMessagesFromSpecifiedOneInRightOrder(conversation, lastMessageId, count);
        }
        return result;
    }

    //*****************************************************************************
    //PRIVATE METHODS

    private static void createUserProfile(String username) {
        Database.INSTANCE.usersProfiles.put(username, new Profile());
    }

    private static boolean isConversationBetweenUsersAlreadyInDatabase(String firstUser, String secondUser) {

        return Database.INSTANCE.conversationsWithFriends.containsKey(
                new UsersPair(firstUser, secondUser));
    }

    private static void createConversation(String firstUser, String secondUser) {
        Database.INSTANCE.conversationsWithFriends.put(
                new UsersPair(firstUser,secondUser),
                new HashMap<>());
    }

    private static List<Message> getConversationMessagesFromLatestToSpecifiedInRightOrder(
            Map<Integer, Message> conversation,
            int messageToId) {

        List<Message> messagesToReturn = new ArrayList<>();
        if (conversation == null)
            return messagesToReturn;

        int lastElementId = conversation.size();

        while(lastElementId > 0 && lastElementId != messageToId) {

            Message message = conversation.get(lastElementId);
            if (message != null) messagesToReturn.add(0, message);
            lastElementId--;
        }

        return messagesToReturn;
    }

    private static List<Message> getConversationMessagesFromSpecifiedOneInRightOrder(
            Map<Integer, Message> conversation,
            int specifiedMessageId,
            int count) {

        List<Message> messagesToReturn = new ArrayList<>();

        if (conversation == null)
            return messagesToReturn;

        int id = specifiedMessageId;
        for(int i = 0; i<count; i++) {

            if (id < 0) break;

            Message message = conversation.get(id);
            if (message != null) messagesToReturn.add(0,message);
            id--;
        }

        return messagesToReturn;
    }
}
