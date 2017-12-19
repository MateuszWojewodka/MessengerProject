package Client;

import Contract.Authentication;
import Contract.Credentials;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class ConversationClient {

    public static void main(String[] args) throws MalformedURLException {
        URL wsdlURL = new URL("http://localhost:8888/authentication?wsdl");
        QName qName = new QName("http://Server/", "AuthenticationImplService");

        Service service = Service.create(wsdlURL, qName);
        Authentication conversation = service.getPort(Authentication.class);

        System.out.println("Echo says:");

        try {
            conversation.registerUser(new Credentials("User", "Pass"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(conversation.
                    logInAndGetToken(new Credentials("User", "Pass")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
