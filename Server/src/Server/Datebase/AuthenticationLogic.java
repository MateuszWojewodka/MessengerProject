package Server.Datebase;

import Contract.Credentials;

import java.util.*;

public class AuthenticationLogic {

    //Field with no-modifier are visible only in package
    Map<String, Credentials> loggedUsers;
    Set<Credentials> registeredUsers;

    //Constructor with no-modifier is visible only in package
    AuthenticationLogic() {

        loggedUsers = Collections.synchronizedMap(new HashMap<>());
        registeredUsers = Collections.synchronizedSet(new HashSet<>());
    }

    public void registerUser(Credentials credentials) {

        registeredUsers.add(credentials);
    }

    public boolean isUserRegistered(Credentials credentials) {

        return registeredUsers.contains(credentials);
    }

    public void logUserIn(String token, Credentials credentials) {

        loggedUsers.put(token, credentials);
    }

    public String getTokenIfUserIsAlreadyLoggedOn(Credentials credentials) {

        String suspectToken = getTokenForSpecifiedCredentials(credentials);
        return suspectToken;
    }

    public void logUserOut(String token) {

        loggedUsers.remove(token);
    }

    public boolean isTokenAlreadyInDatabase(String token) {

        return loggedUsers.containsKey(token);
    }

    private String getTokenForSpecifiedCredentials(Credentials credentials) {

        for (String token : loggedUsers.keySet()) {
            if (loggedUsers.get(token).equals(credentials))
                return token;
        }
        return null;
    }
}
