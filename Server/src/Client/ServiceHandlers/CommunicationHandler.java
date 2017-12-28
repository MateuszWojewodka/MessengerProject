package Client.ServiceHandlers;

import Client.Annotations.TokenAuthenticated;
import Client.Database.LocalDatabaseHandler;
import Contract.Communication;
import Contract.DTO.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private List<MessageToSend> messagesToSend = new ArrayList<>();
    Thread messageSender = new MessageSender();

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

    public void sendMessageToFriend(String userName, String friendName, String message) {

        synchronized (messagesToSend) {

            messagesToSend.add(new MessageToSend(message, userName, friendName));
            messagesToSend.notifyAll();
        }
    }

    @TokenAuthenticated
    public void sendMessageToServer(String userName, String friendName, String message) {

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

    private class MessageSender extends Thread {

        private MessageToSend message;

        public MessageSender() {
            start();
        }

        public void run() {

            while(true) {
                synchronized (messagesToSend) {

                    while (messagesToSend.size() == 0) {
                        try {
                            messagesToSend.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    message = messagesToSend.get(0);
                    messagesToSend.remove(0);
                }
                sendMessageToServer(
                        message.sender,
                        message.receiver,
                        message.messageContent);
            }
        }
    }

    private class MessageToSend {

        public String messageContent;
        public String sender;
        public String receiver;

        public MessageToSend(String messageContent,
                       String sender,
                       String receiver) {

            this.messageContent = messageContent;
            this.sender = sender;
            this.receiver = receiver;
        }
    }
}
