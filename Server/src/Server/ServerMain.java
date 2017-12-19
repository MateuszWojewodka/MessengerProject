package Server;

import javax.xml.ws.Endpoint;

public class ServerMain {

    public static void main(String[] args) {

        String uriToPublishAuthentication = "http://localhost:8888/authentication";
        String uriToPublishCommunication = "http://localhost:8888/communication";

        Endpoint.publish(uriToPublishAuthentication, new AuthenticationImpl());
        System.out.println("Authentication service published.");

        Endpoint.publish(uriToPublishCommunication, new CommunicationImpl());
        System.out.println("Communication service published.");

        System.out.println("Server is running...");
    }
}
