package Client;

import Client.Database.Database;
import Client.Enums.ServiceTypes;
import Client.ServiceHandlers.AuthenticationHandler;
import Client.ServiceHandlers.CommunicationHandler;
import Client.ServiceHandlers.ServiceBaseHandler;
import Contract.Authentication;
import Contract.Communication;
import Contract.DTO.Credentials;
import Contract.DTO.Message;

import java.net.MalformedURLException;
import java.util.*;

public class ClientMain {

    private static String USERNAME = "Mariusz";
    private static String FRIENDNAME = "Matis";
    private static String PASSWORD = "Pass";

    private static AuthenticationHandler authentication;
    private static CommunicationHandler communication;



    private static Timer timer = new Timer();
    private static TimerTask timerTask = new TimerTask() {
        @Override
        public void run(){
            try {
                communication.updateMessagesContainerDatabase(USERNAME,FRIENDNAME);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    };

    public static void main(String[] args) throws Exception, MalformedURLException {

        authentication = AuthenticationHandler.getInstance();
        communication = CommunicationHandler.getInstance();

        authentication.registerUser(USERNAME, PASSWORD);
        authentication.registerUser(FRIENDNAME, PASSWORD);
        authentication.logUserIn(USERNAME, PASSWORD);

        timer.schedule(timerTask, 0, 300);

        Scanner scanner = new Scanner(System.in);
        while (true) {

            communication.sendMessageToFriend(USERNAME, FRIENDNAME, scanner.nextLine());
        }
    }
}
