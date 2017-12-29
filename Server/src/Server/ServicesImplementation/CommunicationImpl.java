package Server.ServicesImplementation;

import Contract.Communication;
import Contract.DTO.Message;
import Server.Database.Database;
import Server.Database.DatabaseHandler;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "Contract.Communication")
public class CommunicationImpl extends ServiceBaseImpl implements Communication {

    @Override
    public void sendMessageToFriend(String friendUserName, String message) throws Exception {

        throwExceptionIfUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        String senderName = getNameOfUser();

        DatabaseHandler.addMessageToConversation(senderName, friendUserName, message);
    }

    @Override
    public Message[] getConversationMessagesFromLatest(
            String friendUserName,
            int messagesCount) throws Exception {

        throwExceptionIfUserIsNotLoggedOn();
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

        throwExceptionIfUserIsNotLoggedOn();
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

        throwExceptionIfUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendUserName);

        return DatabaseHandler
                .getConversationMessagesFromLatestToSpecified(
                        getNameOfUser(),
                        friendUserName,
                        specifiedMessageId).toArray(new Message[0]);
    }

    @Override
    public void markConversationMessagesAsRead(String friendName, int[] messagesId) throws Exception {
        throwExceptionIfUserIsNotLoggedOn();
        throwExceptionIfUserIsNotRegistered(friendName);

        for(int id : messagesId) {
            DatabaseHandler.getMessageById(getNameOfUser(), friendName, id).markAsRead();
        }

    }
}
