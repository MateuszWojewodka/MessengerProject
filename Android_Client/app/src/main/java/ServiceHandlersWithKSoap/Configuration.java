package ServiceHandlersWithKSoap;

class Configuration {

    static final String NAMESPACE = "http://Contract/";
    static final String MAIN_REQUEST_URL = "http://10.0.2.2:8888/";
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

    static final String GET_FRIEND_LIST_METHOD_NAME = "getFriendsList";
    static final String SEND_FRIEND_REQUEST_METHOD_NAME = "sendFriendRequest";
    static final String REMOVE_FRIEND_METHOD_NAME = "removeFriend";
    static final String ACCEPT_FRIEND_REQUEST_METHOD_NAME = "acceptFriendRequest";
    static final String REJECT_FRIEND_REQUEST_METHOD_NAME = "rejectFriendRequest";
    static final String GET_NOTIFICATIONS_METHOD_NAME = "getNotifications";
}