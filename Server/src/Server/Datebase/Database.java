package Server.Datebase;

//Singleton class
public enum Database {

    INSTANCE;

    public xAuthenticationLogic authentication;
    public xUserProfileLogic profiles;
    public xConversationLogic conversation;

    private Database() {
        authentication = new xAuthenticationLogic();
        profiles = new xUserProfileLogic();
        conversation = new xConversationLogic();
    }
}
