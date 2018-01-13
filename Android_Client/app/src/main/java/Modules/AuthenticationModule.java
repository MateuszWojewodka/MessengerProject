package Modules;

import ServiceHandlersWithKSoap.AuthenticationHandler;
import DTO.Credentials;

public class AuthenticationModule {

    //volatile means that cache memory is updating with main memory with every call
    private static volatile AuthenticationModule instance = null;

    //thread safe operation
    public static AuthenticationModule getInstance() {
        if (instance == null) {
            synchronized (AuthenticationModule.class) {
                if (instance == null) {
                    instance = new AuthenticationModule();
                }
            }
        }
        return instance;
    }

    private AuthenticationHandler authenticationHandler = new AuthenticationHandler();

    private AuthenticationModule() {}

    public void registerUser(String myUserName, String myPassword) {

        try {
            authenticationHandler.registerUser(new Credentials(myUserName, myPassword));
            System.out.println("-> You has been registered.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean logUserIn(String myUserName, String myPassword) {

        try {
            authenticationHandler.
                    logInAndGetToken(new Credentials(myUserName, myPassword));
            System.out.println("-> You has been logged on.");
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
