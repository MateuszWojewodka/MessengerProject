package Server;

import Contract.Credentials;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//Singleton class
public enum Database {

    INSTANCE;

    public Map<String, Credentials> loggedUsers;

    private Database() {

        loggedUsers = Collections.synchronizedMap(new HashMap<>());
    }
}
