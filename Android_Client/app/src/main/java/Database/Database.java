package Database;

import DTO.Message;
import DTO.Notifications;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

//Singleton class to mock Client Database
public enum Database {

    INSTANCE;

    public Map<String, List<Message>> conversations;
    public AtomicReference<Notifications> notifications;

    private Database() {

        conversations = Collections.synchronizedMap(new HashMap<String, List<Message>>());

        notifications = new AtomicReference<>();
        notifications.set(new Notifications());
    }
}
