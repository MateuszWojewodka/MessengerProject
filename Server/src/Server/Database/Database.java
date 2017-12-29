package Server.Database;

import Contract.DTO.Credentials;
import Contract.DTO.Message;

import java.util.*;

//Singleton class
public enum Database {

    INSTANCE;

    Map<String, Credentials> loggedUsers;
    Map<String, String> registeredUsers;
    Map<UsersPair, Map<Integer, Message>> conversationsWithFriends;
    Map<String, Profile> usersProfiles;

    private Database() {
        loggedUsers = Collections.synchronizedMap(new HashMap<>());
        registeredUsers = Collections.synchronizedMap(new HashMap<>());
        conversationsWithFriends = Collections.synchronizedMap(new HashMap<>());
        usersProfiles = Collections.synchronizedMap(new HashMap<>());
    }
}
