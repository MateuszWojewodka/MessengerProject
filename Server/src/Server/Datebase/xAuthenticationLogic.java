package Server.Datebase;

import Contract.DTO.Credentials;

import java.util.*;

public class xAuthenticationLogic {

    //Collections are synchronized
    Map<String, Credentials> loggedUsers;
    Set<Credentials> registeredUsers;

    //Constructor with no-modifier is visible only in package
    xAuthenticationLogic() {

        loggedUsers = Collections.synchronizedMap(new HashMap<>());
        registeredUsers = Collections.synchronizedSet(new HashSet<>());
    }

    public void registerUser(Credentials credentials) {

        registeredUsers.add(credentials);
        Database.INSTANCE.profiles.createUserProfile(credentials.username);
    }

    public boolean isUserRegistered(String credentials) {

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

    public boolean isUserLoggedOn(String token) {

        return loggedUsers.containsKey(token);
    }

    public String getUserNameFromToken(String token) {

        return loggedUsers.get(token).username;
    }

    private String getTokenForSpecifiedCredentials(Credentials credentials) {

        for (String token : loggedUsers.keySet()) {
            if (loggedUsers.get(token).equals(credentials))
                return token;
        }
        return null;
    }
}
