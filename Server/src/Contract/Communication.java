package Contract;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Communication {

    @WebMethod
    void addFriend(String friendUserName) throws Exception;

    @WebMethod
    void removeFriend(String friendUserName) throws Exception;

    @WebMethod
    int sendMessageToFriendAndGetMessageId(String friendUserName, String message) throws Exception;

    @WebMethod
    String[] getLatestMessagesFromConversation(String friendUserName, int messagesCount) throws Exception;

    @WebMethod
    String[] getMessagesFromConversation(String friendUserName, int latestMessageId, int messageCount) throws Exception;
}
