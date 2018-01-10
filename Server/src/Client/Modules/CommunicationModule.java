package Client.Modules;

import Client.Annotations.TokenAuthenticated;
import Client.Database.DatabaseHandler;
import Contract.Communication;
import Contract.DTO.Message;

import java.util.*;

public class CommunicationModule extends ServiceBaseHandler<Communication> {

    //volatile means that cache memory is updating with main memory with every call
    private static volatile CommunicationModule instance = null;

    //thread safe operation
    public static CommunicationModule getInstance() throws Exception {
        if (instance == null) {
            synchronized (CommunicationModule.class) {
                if (instance == null) {
                    instance = new CommunicationModule();
                }
            }
        }
        return instance;
    }

    private List<MessageToSend> messagesToSend;
    private Thread messageSender;
    private Timer timerToUpdateMessages;

    private CommunicationModule() throws Exception {
        super(Communication.class);

        messagesToSend = new ArrayList<>();
        messageSender = new MessageSenderThread();
        timerToUpdateMessages = new Timer();
    }

    private void updateMessagesContainerDatabase(
            String friendName) throws Exception {

        if (DatabaseHandler.isConversationEmpty(friendName))
            putToLocalDatabaseLatestMessagesFromConversation(friendName, 10);
        else {
            putToLocalDatabaseLatestMessagesFromConversationToSpecifiedMessage(
                    friendName,
                    DatabaseHandler.getLastConversationMessageId(friendName));
        }
    }

    @TokenAuthenticated
    public void markMessagesAsRead(String friendName, int[] messagesId) throws Exception {

        serviceObject.markConversationMessagesAsRead(friendName, messagesId);
    }

    public void sendMessageToFriend(String userName, String friendName, String message) {

        synchronized (messagesToSend) {

            messagesToSend.add(new MessageToSend(message, userName, friendName));
            messagesToSend.notifyAll();
        }
    }

    @TokenAuthenticated
    private void sendMessageToServer(String userName, String friendName, String message) {

        try {
            serviceObject.sendMessageToFriend(friendName, message);
            System.out.println("-> Message has been sent.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    private void putToLocalDatabaseLatestMessagesFromConversationToSpecifiedMessage(
            String friendName,
            int specifiedMessageId) throws Exception {

        try {
            Message[] messages =
                    serviceObject.getConversationMessagesFromLatestToSpecified(
                            friendName,
                            specifiedMessageId);
            if (messages != null)
                DatabaseHandler.addMultipleMessagesToConversation(friendName,Arrays.asList(messages));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    private void putToLocalDatabaseLatestMessagesFromConversation(
            String friendName,
            int count) throws Exception {

        try {
            Message[] messages =
                    serviceObject.getConversationMessagesFromLatest(friendName, count);
            if (messages != null)
                DatabaseHandler.addMultipleMessagesToConversation(friendName,Arrays.asList(messages));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void startMessageUpdater(String friendName) {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run(){
                try {
                    updateMessagesContainerDatabase(friendName);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        timerToUpdateMessages.schedule(timerTask, 0, 300);
    }

    public void stopAllMessageUpdaterTasks(String friendName) {

        timerToUpdateMessages.cancel();
    }

    private class MessageSenderThread extends Thread {

        private MessageToSend message;

        public MessageSenderThread() {
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
