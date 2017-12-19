package Client;

import Contract.Authentication;
import Contract.Communication;
import Contract.DTO.Credentials;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class ConversationClient {

    public static void main(String[] args) throws MalformedURLException {

        URL authenticationWsdlURL = new URL("http://localhost:8888/authentication?wsdl");
        QName authenticationQName = new QName("http://Server/", "AuthenticationImplService");

        URL communicationWsdlURL = new URL("http://localhost:8888/communication?wsdl");
        QName communicationQName = new QName("http://Server/", "CommunicationImplService");

        Service authenticationService = Service.create(authenticationWsdlURL, authenticationQName);
        Authentication authentication = authenticationService.getPort(Authentication.class);
        System.out.println("Authentication service connected.");

        Service communicationService = Service.create(communicationWsdlURL, communicationQName);
        Communication communication = communicationService.getPort(Communication.class);
        System.out.println("Communication service connected.");

        System.out.println("Echo says:");

        try {
            authentication.registerUser(new Credentials("User", "Pass"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(authentication.
                    logInAndGetToken(new Credentials("User", "Pass")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
