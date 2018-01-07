package Client;

import Client.ServiceHandlersWithKSoap.AuthenticationHandler;
import Contract.DTO.Credentials;

public class ClienKSOAP {

    public static void main(String[] args) {

        Credentials myCredentials = new Credentials("matis", "pass");


        AuthenticationHandler authentication = new AuthenticationHandler();

        authentication.registerUser(myCredentials);

        authentication.logInAndGetToken(myCredentials);
    }
}
