package Client;

import Contract.Authentication;
import Contract.Communication;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public final class ServerConectHandler {

    public static Authentication getAuthenticationPort() throws MalformedURLException {

        URL authenticationWsdlURL = new URL("http://localhost:8888/authentication?wsdl");
        QName authenticationQName = new QName("http://Server/", "AuthenticationImplService");

        Service authenticationService = Service.create(authenticationWsdlURL, authenticationQName);
        return authenticationService.getPort(Authentication.class);
    }

    public static Communication getCommunicationPort() throws MalformedURLException {

        URL communicationWsdlURL = new URL("http://localhost:8888/communication?wsdl");
        QName communicationQName = new QName("http://Server/", "CommunicationImplService");

        Service communicationService = Service.create(communicationWsdlURL, communicationQName);
        return communicationService.getPort(Communication.class);
    }
}
