package Client.ServiceHandlersWithKSoap;

import Contract.Authentication;
import Contract.DTO.Credentials;
import org.ksoap2.serialization.SoapPrimitive;

public class AuthenticationHandler extends ServiceBaseHandler {

    public AuthenticationHandler() {
        super(Configuration.AUTHENTICATION_PATH);
    }

    public void registerUser(Credentials credentials) {

        try {
            callMethodAndGetSoapResponse(
                    Configuration.REGISTER_USER_METHOD_NAME,
                    credentials);
            System.out.println("--> " + credentials.username + " has been registered.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean logInAndGetToken(Credentials credentials) {

        try {
            SoapPrimitive tokenResult = (SoapPrimitive) callMethodAndGetSoapResponse(
                    Configuration.LOGIN_AND_GET_TOKEN_METHOD_NAME,
                    credentials);

            String token = tokenResult.getValue().toString();
            System.out.println("--> Token is: " + token);

            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
