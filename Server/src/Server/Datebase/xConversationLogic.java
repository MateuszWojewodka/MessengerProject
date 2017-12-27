package Server.Datebase;

import Contract.DTO.Message;

import java.util.*;

public class xConversationLogic {

    Map<UsersPair, List<Message>> conversationsWithFriends;

    xConversationLogic() {
        conversationsWithFriends = Collections.synchronizedMap(new HashMap<>());
    }

    public int addMessageToConversationAndGetMessageId(String sender, String receiver, String message) {

        //TODO checking if receiver is friend of mine
        //TODO refactor database. It's only duty is to putting messages to containers
        if (!isConversationBetweenUsersAlreadyInDatabase(sender, receiver))
            createConversation(sender, receiver);

        List<Message> conversation = conversationsWithFriends.get(new UsersPair(sender, receiver));
        int messageId = conversation.size();

        conversation.add(new Message(
                messageId,
                message,
                sender,
                receiver
        ));

        return messageId;
    }

    public List<Message> getLatestMessagesFromConversationWithFriend(String firstUser, String secondUser,int count) {

        List<Message> result = new ArrayList<>();

        List<Message> conversation = conversationsWithFriends.get(new UsersPair(firstUser, secondUser));
        if (conversation != null) {
            int lastMessageIndex = conversation.size() - 1;
            result = getMessagesFromConversationInRightOrder(conversation, lastMessageIndex, count);
        }
        return result;
    }

    public List<Message> getMessagesFromConversationWithFriend(String firstUser, String secondUser,int lastMessageIndex, int count) {

        List<Message> result = new ArrayList<>();

        List<Message> conversation = conversationsWithFriends.get(new UsersPair(firstUser, secondUser));
        if (conversation != null) {
            result = getMessagesFromConversationInRightOrder(conversation, lastMessageIndex, count);
        }
        return result;
    }

    private List<Message> getMessagesFromConversationInRightOrder(List<Message> conversation, int lastMessageIndex, int count) {

        List<Message> messagesToReturn = new ArrayList<>();

        int index = lastMessageIndex;
        for(int i = 0; i<count; i++) {

            if (index < 0) break;

            Message msg = conversation.get(index);
            messagesToReturn.add(0,msg);
            index--;
        }

        return messagesToReturn;
    }

    private boolean isConversationBetweenUsersAlreadyInDatabase(String firstUser, String secondUser) {
        return conversationsWithFriends.containsKey(new UsersPair(firstUser, secondUser));
    }

    private void createConversation(String firstUser, String secondUser) {
        conversationsWithFriends.put(
                new UsersPair(firstUser,secondUser),
                new ArrayList<>());
    }

    private void deleteConversation(String firstUser, String secondUser) {
        conversationsWithFriends.remove(new UsersPair(firstUser, secondUser));
    }

    private class UsersPair {

        private String firstUser;
        private String secondUser;
        private int hashCode;

        public UsersPair(String firstUser, String secondUser) {

            this.firstUser = firstUser;
            this.secondUser = secondUser;

            generateHashCode();
        }

        private void generateHashCode() {

            int fHashCode = firstUser.hashCode();
            int sHashCode = secondUser.hashCode();

            if (fHashCode > sHashCode)
                hashCode = new String(firstUser+secondUser).hashCode();
            else
                hashCode = new String(secondUser+firstUser).hashCode();
        }

        @Override
        public boolean equals(Object obj) {

            if (obj == null)
                return false;

            if (!UsersPair.class.isAssignableFrom(obj.getClass()))
                return false;

            final UsersPair other = (UsersPair) obj;
            if (isFirstEqualsToFirstAndSecondToSecond(other))
                return true;

            if (isFirstEqualsToSecondAndSecondToFirst(other))
                return true;

            return false;
        }

        private boolean isFirstEqualsToFirstAndSecondToSecond(UsersPair other) {
            if ((this.firstUser == null) ? (other.firstUser != null) : !this.firstUser.equals(other.firstUser))
                return false;

            if ((this.secondUser == null) ? (other.secondUser != null) : !this.secondUser.equals(other.secondUser))
                return false;

            return true;
        }

        private boolean isFirstEqualsToSecondAndSecondToFirst(UsersPair other) {
            if ((this.firstUser == null) ? (other.secondUser != null) : !this.firstUser.equals(other.secondUser))
                return false;

            if ((this.secondUser == null) ? (other.firstUser != null) : !this.secondUser.equals(other.firstUser))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return hashCode;
        }
    }
}
