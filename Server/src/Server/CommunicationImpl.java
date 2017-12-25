package Server;

import Contract.Communication;
import Server.Datebase.Database;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.crypto.Data;
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

    public String[] getLatestMessagesFromConversation(
            String friendUserName,
            int messagesCount) throws Exception {

        throwExceptionIfUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        return (String[]) Database.INSTANCE.conversation
                .getLatestMessagesFromConversationWithFriend(
                        getNameOfUser(),
                        friendUserName,
                        messagesCount).toArray();
    }

    public String[] getMessagesFromConversation(
            String friendUserName,
            int latestMessageId,
            int messageCount) throws Exception {

        throwExceptionIfUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        return (String[]) Database.INSTANCE.conversation
                .getMessagesFromConversationWithFriend(
                        getNameOfUser(),
                        friendUserName,
                        latestMessageId,
                        messageCount).toArray();
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
