package Server.ServicesImplementation;

import Contract.DTO.Message;
import Server.Database.DatabaseHandler;
import Server.Database.UsersPair;

import java.util.concurrent.TimeUnit;

public class NotificationTrackerThread extends Thread {

    public NotificationTrackerThread() {
        start();
    }

    public void run() {

        while(true) {

            UsersPair[] usersPair = DatabaseHandler.getConversationUsersPairList();
            for(UsersPair pair : usersPair) {

                Message message = DatabaseHandler.getLastMessageFromConversation(pair);

                if(!message.isReadByReceiver())
                {
                    addNewMessageNotificationToUserProfile(message.getReceiver(), message.getSender());
                }
                else
                {
                    removeNewMessageNotificationFromUserProfile(message.getReceiver(), message.getSender());
                }
            }
            sleep(500);

        }
    }

    private void addNewMessageNotificationToUserProfile(String receiver, String sender){
        try {
            DatabaseHandler.addNewMessageNotificationToUserProfile(
                    receiver,
                    sender);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeNewMessageNotificationFromUserProfile(String receiver, String sender) {
        try {
            DatabaseHandler.removeNewMessageNotificationFromUserProfile(
                    receiver,
                    sender);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void sleep(int miliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {}
    }
}
