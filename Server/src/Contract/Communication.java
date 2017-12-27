package Contract;

import Contract.DTO.Message;

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
    Message[] getConversationMessagesFromLatest(String friendUserName, int messagesCount) throws Exception;

    @WebMethod
    Message[] getConversationMessagesFromLatestToSpecified(String friendUserName, int specifiedMessageId) throws Exception;

    @WebMethod
    Message[] getConversationMessagesFromSpecifiedOne(String friendUserName, int lastMessageId, int messageCount) throws Exception;
}
