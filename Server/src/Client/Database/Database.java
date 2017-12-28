package Client.Database;

import Contract.DTO.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Singleton class to mock Client Database
public enum Database {

    INSTANCE;

    List<Message> conversation;

    private Database() {

        conversation = Collections.synchronizedList(new ArrayList<>());
    }
}
