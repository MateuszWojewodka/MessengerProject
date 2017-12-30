package Server.ServicesImplementation;

import Contract.Communication;
import Contract.DTO.Message;
import Server.Database.DatabaseHandler;

import javax.jws.WebService;

@WebService(endpointInterface = "Contract.Communication")
public class CommunicationImpl extends ServiceBaseImpl implements Communication {

    @Override
    public void sendMessageToFriend(String friendUserName, String message) throws Exception {

        throwExceptionIfCurrentUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);
        throwExceptionIfUserIsNotFriendOf(friendUserName);

        String senderName = getNameOfUser();

        DatabaseHandler.addMessageToConversation(senderName, friendUserName, message);
    }

    @Override
    public Message[] getConversationMessagesFromLatest(
            String friendUserName,
            int messagesCount) throws Exception {

        throwExceptionIfCurrentUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        return DatabaseHandler
                .getConversationMessagesFromLatest(
                        getNameOfUser(),
                        friendUserName,
                        messagesCount).toArray(new Message[0]);
    }

    @Override
    public Message[] getConversationMessagesFromSpecifiedOne(
            String friendUserName,
            int lastMessageId,
            int messageCount) throws Exception {

        throwExceptionIfCurrentUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        return DatabaseHandler
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

        throwExceptionIfCurrentUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        return DatabaseHandler
                .getConversationMessagesFromLatestToSpecified(
                        getNameOfUser(),
                        friendUserName,
                        specifiedMessageId).toArray(new Message[0]);
    }

    @Override
    public void markConversationMessagesAsRead(String friendName, int[] messagesId) throws Exception {

        throwExceptionIfCurrentUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendName);

        for(int id : messagesId) {
            DatabaseHandler.getMessageFromConversationById(getNameOfUser(), friendName, id).markAsRead();
        }
    }
}
