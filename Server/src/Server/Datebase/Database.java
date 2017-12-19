package Server.Datebase;

//Singleton class
public enum Database {

    INSTANCE;

    public xAuthenticationLogic authenticationLogic;

    private Database() {
        authenticationLogic = new xAuthenticationLogic();
    }
}
