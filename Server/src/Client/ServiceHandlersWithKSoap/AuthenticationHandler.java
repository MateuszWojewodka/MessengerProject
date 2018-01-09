package Client.ServiceHandlersWithKSoap;

import Contract.DTO.Credentials;
import org.ksoap2.serialization.SoapPrimitive;

public class AuthenticationHandler extends ServiceBaseHandler {

    public AuthenticationHandler() {
        super(Configuration.AUTHENTICATION_MODULE_NAME);
    }

    public void registerUser(Credentials credentials) {

        try {
            callMethodWithParametersAndGetSoapResponse(
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
            SoapPrimitive tokenResult = (SoapPrimitive) callMethodWithParametersAndGetSoapResponse(
                    Configuration.LOGIN_AND_GET_TOKEN_METHOD_NAME,
                    credentials);

            String token = tokenResult.getValue().toString();
            sharedToken.set(token);

            System.out.println("--> Token is: " + token);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean logOut(Credentials credentials) {

        try {
            callMethodWithParametersAndGetSoapResponse("logOut", new Object());
            System.out.println("--> " + credentials.username + " has been logged out.");
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
