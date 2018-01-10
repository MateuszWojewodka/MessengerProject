package Client.ServiceHandlersWithKSoap;

import Contract.DTO.Credentials;
import org.ksoap2.serialization.SoapPrimitive;

public class AuthenticationHandler extends ServiceBaseHandler {

    public AuthenticationHandler() {
        super(Configuration.AUTHENTICATION_MODULE_NAME);
    }

    public void registerUser(Credentials credentials) throws Exception {

        callMethodWithParametersAndGetSoapResponse(
                Configuration.REGISTER_USER_METHOD_NAME,
                credentials);
    }

    public boolean logInAndGetToken(Credentials credentials) throws Exception {

        SoapPrimitive tokenResult = (SoapPrimitive) callMethodWithParametersAndGetSoapResponse(
                Configuration.LOGIN_AND_GET_TOKEN_METHOD_NAME,
                credentials);

        String token = tokenResult.getValue().toString();
        sharedToken.set(token);

        System.out.println("--> Token is: " + token);
        return true;
    }

    public boolean logOut() throws Exception {

        callMethodWithParametersAndGetSoapResponse("logOut", null);
        System.out.println("--> User has been logged out.");
        return true;
    }
}
