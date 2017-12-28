package Client;

import Client.ServiceHandlers.AuthenticationHandler;
import Client.ServiceHandlers.CommunicationHandler;

import java.net.MalformedURLException;
import java.util.*;

public class ClientMain {

    private static String USERNAME = "Matis";
    private static String FRIENDNAME = "Mariusz";
    private static String PASSWORD = "Pass";

    private static AuthenticationHandler authentication;
    private static CommunicationHandler communication;

    public static void main(String[] args) throws Exception, MalformedURLException {

        authentication = AuthenticationHandler.getInstance();
        communication = CommunicationHandler.getInstance();

        authentication.registerUser(USERNAME, PASSWORD);
        authentication.registerUser(FRIENDNAME, PASSWORD);
        authentication.logUserIn(USERNAME, PASSWORD);

        Scanner scanner = new Scanner(System.in);
        while (true) {

            communication.sendMessageToFriend(USERNAME, FRIENDNAME, scanner.nextLine());
        }
    }
}
