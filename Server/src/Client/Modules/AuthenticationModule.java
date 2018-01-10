package Client.Modules;

import Contract.Authentication;
import Contract.DTO.Credentials;

public class AuthenticationModule extends ServiceBaseHandler <Authentication>{

    //volatile means that cache memory is updating with main memory with every call
    private static volatile AuthenticationModule instance = null;

    //thread safe operation
    public static AuthenticationModule getInstance() throws Exception {
        if (instance == null) {
            synchronized (AuthenticationModule.class) {
                if (instance == null) {
                    instance = new AuthenticationModule();
                }
            }
        }
        return instance;
    }

    private AuthenticationModule() throws Exception {
        super(Authentication.class);
    }

    public void registerUser(String myUserName, String myPassword) {

        try {
            serviceObject.registerUser(new Credentials(myUserName, myPassword));
            System.out.println("-> You has been registered.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean logUserIn(String myUserName, String myPassword) {

        try {
            String token = serviceObject.
                    logInAndGetToken(new Credentials(myUserName, myPassword));
            sharedToken.set(token);
            System.out.println("-> You has been logged on.");
            System.out.println("-> Received token: " + token);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
