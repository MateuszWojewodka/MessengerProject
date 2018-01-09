package Client.ServiceHandlersWithKSoap;

class Configuration {

    static final String NAMESPACE = "http://Contract/";
    static final String MAIN_REQUEST_URL = "http://localhost:8888/";
    static final String SOAP_ACTION = null;

    static final String AUTHENTICATION_MODULE_NAME = "authentication";
    static final String COMMUNICATION_MODULE_NAME = "communication";
    static final String PROFILE_MODULE_NAME = "profile";

    static final String REGISTER_USER_METHOD_NAME = "registerUser";
    static final String LOGIN_AND_GET_TOKEN_METHOD_NAME = "logInAndGetToken";
    static final String LOGOUT_METHOD_NAME = "logOut";

    static final String SEND_MESSAGE_TO_FRIEND_METHOD_NAME = "sendMessageToFriend";
    static final String GET_CONVERSATION_MESSAGES_FROM_LATEST_METHOD_NAME = "getConversationMessagesFromLatest";
    static final String GET_CONVERSATION_MESSAGES_FROM_LATEST_TO_SPECIFIED_METHOD_NAME = "getConversationMessagesFromLatestToSpecified";
    static final String GET_CONVERSATION_MESSAGES_FROM_SPECIFIED_ONE_METHOD_NAME = "getConversationMessagesFromSpecifiedOne";
    static final String MARK_CONVERSATION_MESSAGES_AS_READ_METHOD_NAME = "markConversationMessagesAsRead";
}