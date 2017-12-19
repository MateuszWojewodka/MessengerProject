package Server.Datebase;

import Contract.DTO.Message;

import java.util.*;

public class xUserProfileLogic {

    //Synchronized collection
    Map<String, Profile> usersProfiles;

    xUserProfileLogic() {
        usersProfiles = Collections.synchronizedMap(new HashMap<>());
    }

    public void createUserProfile(String username) {
        usersProfiles.put(username, new Profile());
    }

    public void removeUserProfile(String username) {
        usersProfiles.remove(username);
    }

    public void addFriendToUserProfileAndInitializeTheirConversation(String ownerUsername, String friendUsername) {
        usersProfiles.get(ownerUsername).friends.add(friendUsername);
        createConversation(ownerUsername, friendUsername);
    }

    public void removeFriendFromUserProfile(String ownerUsername, String friendUsername) {
        usersProfiles.get(ownerUsername).friends.remove(friendUsername);
    }

    public void addMessageToConversationWithFriend(String sender, String receiver, String message) {

        List<Message> conversation = usersProfiles.get(sender).conversationsWithFriends.get(receiver);
        int messageId = conversation.size();

        conversation.add(new Message(
                messageId,
                message,
                sender,
                receiver
        ));
    }

    public List<Message> getLatestMessagesFromConversationWithFriend(String ownerUsername, String friendUsername,int count) {

        List<Message> conversation = usersProfiles.get(ownerUsername).conversationsWithFriends.get(friendUsername);
        int lastMessageIndex = conversation.size() - 1;

        return getMessegesFromConversationInRightOrder(conversation, lastMessageIndex, count);
    }

    public List<Message> getMessagesFromConversationWithFriend(String ownerUsername, String friendUsername,int lastMessageIndex, int count) {

        List<Message> conversation = usersProfiles.get(ownerUsername).conversationsWithFriends.get(friendUsername);

        return getMessegesFromConversationInRightOrder(conversation, lastMessageIndex, count);
    }

    public List<Message> getMessegesFromConversationInRightOrder(List<Message> conversation, int lastMessageIndex, int count) {

        List<Message> messagesToReturn = new ArrayList<>();

        int index = lastMessageIndex;
        for(int i = 0; i<count; i++) {

            if (index < 0) break;

            index = index - i;
            Message msg = conversation.get(index);
            messagesToReturn.add(0,msg);
        }

        return messagesToReturn;
    }

    private void createConversation(String ownerUsername, String friendUsername) {
        usersProfiles.get(ownerUsername).conversationsWithFriends.put(friendUsername, new ArrayList<>());
    }

    private void deleteConversation(String ownerUsername, String friendUsername) {
        usersProfiles.get(ownerUsername).conversationsWithFriends.put(friendUsername, new ArrayList<>());
    }

    private class Profile {

        Set<String> friends;
        Map<String, List<Message>> conversationsWithFriends;

        Profile() {
            friends = new HashSet<>();
            conversationsWithFriends = new HashMap<>();
        }
    }
}
