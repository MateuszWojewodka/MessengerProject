package Client.ServiceHandlersWithKSoap;

class Configuration {

    static final String NAMESPACE = "http://Contract/";
    static final String MAIN_REQUEST_URL = "http://localhost:8888/";
    static final String SOAP_ACTION = null;

    static final String AUTHENTICATION_PATH = "authentication";
    static final String COMMUNICATION_PATH = "communication";
    static final String PROFILE_PATH = "profile";

    static final String REGISTER_USER_METHOD_NAME = "registerUser";
    static final String LOGIN_AND_GET_TOKEN_METHOD_NAME = "logInAndGetToken";
    static final String LOGOUT_METHOD_NAME = "logOut";
}