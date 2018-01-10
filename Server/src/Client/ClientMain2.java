package Client;

import Client.Modules.AuthenticationModule;
import Client.Modules.CommunicationModule;
import Client.Modules.ProfileModule;

import java.net.MalformedURLException;
import java.util.Scanner;

public class ClientMain2 {

    private static String USERNAME = "Mariusz";
    private static String FRIENDNAME = "Matis";
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
