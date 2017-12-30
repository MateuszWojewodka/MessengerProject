package Client;

import Client.ServiceHandlers.AuthenticationHandler;
import Client.ServiceHandlers.CommunicationHandler;
import Client.ServiceHandlers.ProfileHandler;

import java.net.MalformedURLException;
import java.util.Scanner;

public class ClientMain2 {

    private static String USERNAME = "Mariusz";
    private static String FRIENDNAME = "Matis";
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

        profile.acceptFriendRequest(FRIENDNAME);

        Scanner scanner = new Scanner(System.in);
        while (true) {

            communication.sendMessageToFriend(USERNAME, FRIENDNAME, scanner.nextLine());
        }
    }
}
