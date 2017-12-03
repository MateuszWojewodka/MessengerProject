package Server;

import Contract.Conversation;

import javax.jws.WebService;

@WebService(endpointInterface = "Contract.Conversation")
public class ConversationImpl implements Conversation {

    @Override
    public String talkToMe() {

        System.out.println("I've talked to someone!");
        return "Hey! I'm talking to you!";
    }
}
