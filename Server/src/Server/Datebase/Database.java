package Server.Datebase;

//Singleton class
public enum Database {

    INSTANCE;

    public AuthenticationLogic authenticationLogic;

    private Database() {
        authenticationLogic = new AuthenticationLogic();
    }
}
