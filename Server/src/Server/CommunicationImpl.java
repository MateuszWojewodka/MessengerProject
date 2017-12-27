package Server;

import Contract.Communication;
import Contract.DTO.Message;
import Server.Datebase.Database;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService(endpointInterface = "Contract.Communication")
public class CommunicationImpl implements Communication {

    @Resource
    WebServiceContext webServiceCtx;

    @Override
    public void addFriend(String friendUserName) throws Exception {

        throwExceptionIfUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        Database.INSTANCE.profiles.
                addFriendToUserProfile(
                        getNameOfUser(),
                        friendUserName);
    }

    @Override
    public void removeFriend(String friendUserName) throws Exception {

        throwExceptionIfUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        Database.INSTANCE.profiles.
                removeFriendFromUserProfile(
                        getNameOfUser(),
                        friendUserName);
    }

    @Override
    public int sendMessageToFriendAndGetMessageId(String friendUserName, String message) throws Exception {

        throwExceptionIfUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        String senderName = getNameOfUser();

        return Database.INSTANCE.conversation.addMessageToConversationAndGetMessageId(senderName, friendUserName, message);
    }

    public Message[] getConversationMessagesFromLatest(
            String friendUserName,
            int messagesCount) throws Exception {

        throwExceptionIfUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        return Database.INSTANCE.conversation
                .getConversationMessagesFromLatest(
                        getNameOfUser(),
                        friendUserName,
                        messagesCount).toArray(new Message[0]);
    }

    public Message[] getConversationMessagesFromSpecifiedOne(
            String friendUserName,
            int lastMessageId,
            int messageCount) throws Exception {

        throwExceptionIfUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        return Database.INSTANCE.conversation
                .getConversationMessagesFromSpecifiedOne(
                        getNameOfUser(),
                        friendUserName,
                        lastMessageId,
                        messageCount).toArray(new Message[0]);
    }

    @Override
    public Message[] getConversationMessagesFromLatestToSpecified(
            String friendUserName,
            int specifiedMessageId) throws Exception {

        throwExceptionIfUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        return Database.INSTANCE.conversation
                .getConversationMessagesFromLatestToSpecified(
                        getNameOfUser(),
                        friendUserName,
                        specifiedMessageId).toArray(new Message[0]);
    }

    private String getNameOfUser() {

        String token = RequestHandling.getTokenFromHttpRequest(webServiceCtx);
        return Database.INSTANCE.authentication.getUserNameFromToken(token);
    }

    private void throwExceptionIfUserIsNotLoggedOn() throws Exception {

        if (!isUserIsLoggedOn()) {
            throw new Exception("User is not logged on.");
        }
    }

    private void throwExceptionIfUserIsNotRegistered(String username) throws  Exception {

        if (!Database.INSTANCE.authentication.isUserRegistered(username))
            throw new Exception("User " + username + " is not registered.");
    }

    private boolean isUserIsLoggedOn() {

        String token = RequestHandling.getTokenFromHttpRequest(webServiceCtx);

        if (token == null || !Database.INSTANCE.authentication.isUserLoggedOn(token))
            return false;
        else
            return true;
    }
}
