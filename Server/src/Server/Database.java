package Server;

import Contract.Credentials;


import java.util.*;

//Singleton class
public enum Database {

    INSTANCE;

    private Map<String, Credentials> loggedUsers;

    private Database() {

        loggedUsers = Collections.synchronizedMap(new HashMap<>());
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
