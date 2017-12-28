package Client.ServiceHandlers;

import Contract.Authentication;
import Contract.DTO.Credentials;

public class AuthenticationHandler extends ServiceBaseHandler <Authentication>{

    //volatile means that cache memory is updating with main memory with every call
    private static volatile AuthenticationHandler instance = null;

    //thread safe operation
    public static AuthenticationHandler getInstance() throws Exception {
        if (instance == null) {
            synchronized (AuthenticationHandler.class) {
                if (instance == null) {
                    instance = new AuthenticationHandler();
                }
            }
        }
        return instance;
    }

    private AuthenticationHandler() throws Exception {
        super(Authentication.class);
    }

    public void registerUser(String myUserName, String myPassword) throws Exception {

        try {
            serviceObject.registerUser(new Credentials(myUserName, myPassword));
            System.out.println("-> You has been registered.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean logUserIn(String myUserName, String myPassword) throws Exception {

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
