package Client;

import Contract.Conversation;
import Contract.Credentials;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class ConversationClient {

    public static void main(String[] args) throws MalformedURLException {
        URL wsdlURL = new URL("http://localhost:8888/conversation?wsdl");
        QName qName = new QName("http://Server/", "ConversationImplService");

        Service service = Service.create(wsdlURL, qName);
        Conversation conversation = service.getPort(Conversation.class);

        System.out.println("Echo says:");

        conversation.registerUser(new Credentials("User", "Pass"));

        try {
            System.out.println(conversation.
                    logInAndGetToken(new Credentials("User1", "Pass1")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
