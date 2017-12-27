package Client;

import Contract.Authentication;
import Contract.Communication;
import Contract.DTO.Credentials;
import Contract.DTO.Message;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.net.MalformedURLException;
import java.util.*;

public class ConversationClient {

    private static String USERNAME = "Matis";
    private static String FRIENDNAME = "Mariusz";
    private static String PASSWORD = "Pass";

    private static String TOKEN = "";
    private static Authentication authentication;
    private static Communication communication;

    private static List<Message> conversation = new ArrayList<>();

    private static Timer timer = new Timer();
    private static TimerTask timerTask = new TimerTask() {
        @Override
        public void run(){
            try {
                updateMessagesContainer(FRIENDNAME);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    };

    public static void main(String[] args) throws Exception, MalformedURLException {

        authentication = ServerConnectHandler.getAuthenticationPort();
        System.out.println("Authentication service connected.");

        communication = ServerConnectHandler.getCommunicationPort();
        System.out.println("Communication service connected.");

        //registerUser(FRIENDNAME, PASSWORD);

        registerUser(USERNAME, PASSWORD);
        TOKEN = logUserInAndGetToken(USERNAME, PASSWORD);

        //getLatestMessagesFromConversation(FRIENDNAME, 10);
        timer.schedule(timerTask, 0, 500);

        for (Message message : conversation) {
            System.out.println(message.getMessageContent());
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {

            sendMessageToFriend(FRIENDNAME, scanner.nextLine());
        }
    }

    private static void registerUser(String myUserName, String myPassword) throws Exception {

        try {
            authentication.registerUser(new Credentials(myUserName, myPassword));
            System.out.println("-> You has been registered.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String logUserInAndGetToken(String myUserName, String myPassword) throws Exception {

        String token = "";
        try {
            token = authentication.
                    logInAndGetToken(new Credentials(myUserName, myPassword));
            System.out.println("-> You has been logged on.");
            System.out.println("Received token: " + token);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            return token;
        }
    }

    private static void putTokenToCommunicationRequest() {

        Map<String, Object> req_ctx = ((BindingProvider) communication).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8888/communication?wsdl");

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Token", Collections.singletonList(TOKEN));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    }

    private static void sendMessageToFriend(String friendName, String message) {

        try {
            putTokenToCommunicationRequest();
            int id = communication.sendMessageToFriendAndGetMessageId(friendName, message);
            System.out.println("-> Message has been sent.");

            conversation.add(new Message(id, message, USERNAME, FRIENDNAME));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateMessagesContainer(
            String friendName) throws Exception {

        if (conversation.size() == 0)
            getLatestMessagesFromConversation(friendName, 10);
        else {
            int lastMessageIndex = conversation.size() - 1;
            getLatestMessagesFromConversationToSpecifiedMessage(
                    friendName,
                    conversation.get(lastMessageIndex).getMessageId());
        }
    }

    private static void getLatestMessagesFromConversationToSpecifiedMessage(
            String friendName,
            int specifiedMessageId) throws Exception {

        try {
            putTokenToCommunicationRequest();
            Message[] messages =
                    communication.getConversationMessagesFromLatestToSpecified(
                            friendName,
                            specifiedMessageId);
            if (messages != null)
                conversation.addAll(Arrays.asList(messages));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getLatestMessagesFromConversation(
            String friendName,
            int count) throws Exception {

        try {
            putTokenToCommunicationRequest();
            Message[] messages =
                    communication.getConversationMessagesFromLatest(friendName, count);
            if (messages != null)
                conversation.addAll(0, Arrays.asList(messages));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
