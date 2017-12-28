package Client.ServiceHandlers;

import Client.Annotations.TokenAuthenticated;
import Client.Database.LocalDatabaseHandler;
import Contract.Communication;
import Contract.DTO.Message;

import java.util.Arrays;

public class CommunicationHandler extends ServiceBaseHandler<Communication> {

    //volatile means that cache memory is updating with main memory with every call
    private static volatile CommunicationHandler instance = null;

    //thread safe operation
    public static CommunicationHandler getInstance() throws Exception {
        if (instance == null) {
            synchronized (CommunicationHandler.class) {
                if (instance == null) {
                    instance = new CommunicationHandler();
                }
            }
        }
        return instance;
    }

    private CommunicationHandler() throws Exception {
        super(Communication.class);
    }

    public void updateMessagesContainerDatabase(
            String userName,
            String friendName) throws Exception {

        if (LocalDatabaseHandler.isConversationEmpty(userName, friendName))
            putToLocalDatabaseLatestMessagesFromConversation(userName, friendName, 10);
        else {
            putToLocalDatabaseLatestMessagesFromConversationToSpecifiedMessage(
                    userName,
                    friendName,
                    LocalDatabaseHandler.getLastConversationMessageId(userName, friendName));
        }
    }

    public void addMessageToSend(String userName, String friendName, String message) {


    }

    @TokenAuthenticated
    public void sendMessageToFriend(String userName, String friendName, String message) {

        try {
            serviceObject.sendMessageToFriend(friendName, message);
            System.out.println("-> Message has been sent.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    private void putToLocalDatabaseLatestMessagesFromConversationToSpecifiedMessage(
            String userName,
            String friendName,
            int specifiedMessageId) throws Exception {

        try {
            Message[] messages =
                    serviceObject.getConversationMessagesFromLatestToSpecified(
                            friendName,
                            specifiedMessageId);
            if (messages != null)
                LocalDatabaseHandler.addMultipleMessagesToConversation(userName, friendName,Arrays.asList(messages));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    private void putToLocalDatabaseLatestMessagesFromConversation(
            String userName,
            String friendName,
            int count) throws Exception {

        try {
            Message[] messages =
                    serviceObject.getConversationMessagesFromLatest(friendName, count);
            if (messages != null)
                LocalDatabaseHandler.addMultipleMessagesToConversation(userName, friendName,Arrays.asList(messages));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
