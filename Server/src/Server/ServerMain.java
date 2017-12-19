package Server;

import javax.xml.ws.Endpoint;

public class ServerMain {

    public static void main(String[] args) {

        String uriToPublishConversation = "http://localhost:8888/authentication";
        Endpoint.publish(uriToPublishConversation, new AuthenticationImpl());
        System.out.println("Server is running...");
    }
}
