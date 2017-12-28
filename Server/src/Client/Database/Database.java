package Client.Database;

import Contract.DTO.Message;

import java.util.*;

//Singleton class to mock Client Database
public enum Database {

    INSTANCE;

    public Map<String, List<Message>> conversations;

    private Database() {

        conversations = Collections.synchronizedMap(new HashMap<>());
    }
}
