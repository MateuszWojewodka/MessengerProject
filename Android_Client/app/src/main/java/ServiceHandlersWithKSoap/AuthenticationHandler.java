package ServiceHandlersWithKSoap;

import DTO.Credentials;

import org.ksoap2.serialization.SoapObject;
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

        callMethodWithParametersAndGetSoapResponse(Configuration.LOGOUT_METHOD_NAME, null);
        return true;
    }

    public String[] getAllRegisteredUsers() throws Exception {

        SoapObject registeredUsersResult = (SoapObject) callMethodWithParametersAndGetSoapResponse(
                Configuration.GET_ALL_REGISTERED_USERS_METHOD_NAME, null);

        return retrieveStringArrayFromSoapObject(registeredUsersResult);
    }

    private String[] retrieveStringArrayFromSoapObject(SoapObject soapObject) {

        String[] result = new String[soapObject.getPropertyCount()];

        for (int i = 0; i < soapObject.getPropertyCount(); i++) {

            result[i] = soapObject.getProperty(i).toString();
        }

        return result;
    }
}
