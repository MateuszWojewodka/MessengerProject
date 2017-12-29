package Client.Database;

import Contract.DTO.Message;
import Contract.DTO.Notifications;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

//Singleton class to mock Client Database
public enum Database {

    INSTANCE;

    public Map<String, List<Message>> conversations;
    public AtomicReference<Notifications> notifications;

    private Database() {

        conversations = Collections.synchronizedMap(new HashMap<>());

        notifications = new AtomicReference<>();
        notifications.set(new Notifications());
    }
}
