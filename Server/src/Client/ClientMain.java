package Client;

import Client.Modules.AuthenticationModule;
import Client.Modules.CommunicationModule;
import Client.Modules.ProfileModule;

import java.net.MalformedURLException;
import java.util.*;

public class ClientMain {

    private static String USERNAME = "Matis";
    private static String FRIENDNAME = "Mariusz";
    private static String PASSWORD = "Pass";

    private static AuthenticationModule authentication;
    private static CommunicationModule communication;
    private static ProfileModule profile;

    public static void main(String[] args) throws Exception, MalformedURLException {

        Scanner enterWaiter = new Scanner(System.in);

        authentication = AuthenticationModule.getInstance();
        communication = CommunicationModule.getInstance();
        profile = ProfileModule.getInstance();

        System.out.println(USERNAME);

        authentication.registerUser(USERNAME, PASSWORD);

        authentication.registerUser("Mariusz", PASSWORD);
        authentication.registerUser("Roman", PASSWORD);
        authentication.registerUser("Jozef", PASSWORD);

        //enterWaiter.nextLine();

        authentication.logUserIn(USERNAME, PASSWORD);

        profile.sendFriendRequest("Mariusz");
        enterWaiter.nextLine();
//
//        profile.sendFriendRequest(FRIENDNAME);
//        enterWaiter.nextLine();

//        communication.startMessageUpdater(FRIENDNAME);
//        enterWaiter.nextLine();
//
//
//        List<Message> conv = Database.INSTANCE.conversations.get(FRIENDNAME);
//        Message lastMessage = conv.get(conv.size() - 1);

        //communication.markMessagesAsRead(FRIENDNAME, new int[]{lastMessage.getMessageId()});

        Scanner scanner = new Scanner(System.in);
        while (true) {

            communication.sendMessageToFriend(USERNAME, "Mariusz", scanner.nextLine());
        }
    }
}
