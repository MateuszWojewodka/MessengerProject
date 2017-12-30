package Client;

import Client.Database.Database;
import Client.ServiceHandlers.AuthenticationHandler;
import Client.ServiceHandlers.CommunicationHandler;
import Client.ServiceHandlers.ProfileHandler;
import Contract.DTO.Message;
import Contract.Profile;

import java.net.MalformedURLException;
import java.util.*;

public class ClientMain {

    private static String USERNAME = "Matis";
    private static String FRIENDNAME = "Mariusz";
    private static String PASSWORD = "Pass";

    private static AuthenticationHandler authentication;
    private static CommunicationHandler communication;
    private static ProfileHandler profile;

    public static void main(String[] args) throws Exception, MalformedURLException {

        Scanner enterWaiter = new Scanner(System.in);

        authentication = AuthenticationHandler.getInstance();
        communication = CommunicationHandler.getInstance();
        profile = ProfileHandler.getInstance();

        System.out.println(USERNAME);

        authentication.registerUser(USERNAME, PASSWORD);
        //enterWaiter.nextLine();

        authentication.logUserIn(USERNAME, PASSWORD);
        enterWaiter.nextLine();

        profile.sendFriendRequest("dupa");
        enterWaiter.nextLine();

//        communication.startMessageUpdater(FRIENDNAME);
//        enterWaiter.nextLine();
//
//
//        List<Message> conv = Database.INSTANCE.conversations.get(FRIENDNAME);
//        Message lastMessage = conv.get(conv.size() - 1);

        //communication.markMessagesAsRead(FRIENDNAME, new int[]{lastMessage.getMessageId()});

        Scanner scanner = new Scanner(System.in);
        while (true) {

            communication.sendMessageToFriend(USERNAME, FRIENDNAME, scanner.nextLine());
        }
    }
}
