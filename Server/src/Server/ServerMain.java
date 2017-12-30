package Server;

import Server.ServicesImplementation.AuthenticationImpl;
import Server.ServicesImplementation.CommunicationImpl;
import Server.ServicesImplementation.ProfileImpl;

import javax.xml.ws.Endpoint;

public class ServerMain {

    public static void main(String[] args) {

        String uriToPublishAuthentication = "http://localhost:8888/authentication";
        String uriToPublishCommunication = "http://localhost:8888/communication";
        String uriToPublishProfile = "http://localhost:8888/profile";

        Endpoint.publish(uriToPublishAuthentication, new AuthenticationImpl());
        System.out.println("Authentication service published.");

        Endpoint.publish(uriToPublishCommunication, new CommunicationImpl());
        System.out.println("Communication service published.");

        Endpoint.publish(uriToPublishProfile, new ProfileImpl());
        System.out.println("Profile service published.");

        System.out.println("Server is running...");
    }
}
