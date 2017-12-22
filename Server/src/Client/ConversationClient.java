package Client;

import Contract.Authentication;
import Contract.Communication;
import Contract.DTO.Credentials;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationClient {

    public static void main(String[] args) throws MalformedURLException {

        Authentication authentication = ServerConectHandler.getAuthenticationPort();
        System.out.println("Authentication service connected.");

        Communication communication = ServerConectHandler.getCommunicationPort();
        System.out.println("Communication service connected.");

        System.out.println("Echo says:");

        try {
            authentication.registerUser(new Credentials("Mariusz", "Pass"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String token = "";

        try {
            token = authentication.
                    logInAndGetToken(new Credentials("User", "Pass"));

            System.out.println(token);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Map<String, Object> req_ctx = ((BindingProvider) communication).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8888/communication?wsdl");

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Token", Collections.singletonList(token));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        try {
            //communication.addFriend("Mariusz");
            communication.sendMessageToFriend("Mariusz", "Druga wiadomosc!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
