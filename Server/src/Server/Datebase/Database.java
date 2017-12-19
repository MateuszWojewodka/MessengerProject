package Server.Datebase;

//Singleton class
public enum Database {

    INSTANCE;

    public xAuthenticationLogic authentication;
    public xUserProfileLogic profiles;

    private Database() {
        authentication = new xAuthenticationLogic();
        profiles = new xUserProfileLogic();

    }
}
