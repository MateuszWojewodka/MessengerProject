package ServiceHandlersWithKSoap;

import DTO.Message;
import org.ksoap2.serialization.SoapObject;

public class CommunicationHandler extends ServiceBaseHandler {

    public CommunicationHandler() {
        super(Configuration.COMMUNICATION_MODULE_NAME);
    }

    public void sendMessageToFriend(String friendUserName, String message) throws Exception {

        callMethodWithParametersAndGetSoapResponse(
                Configuration.SEND_MESSAGE_TO_FRIEND_METHOD_NAME,
                friendUserName, message);
    }

    public Message[] getConversationMessagesFromLatest(String friendUserName, int messagesCount) throws Exception {

        SoapObject response = (SoapObject) callMethodWithParametersAndGetSoapResponse(
                Configuration.GET_CONVERSATION_MESSAGES_FROM_LATEST_METHOD_NAME,
                friendUserName, messagesCount);

        return retrieveMessageArrayFromSoapObject(response);
    }

    public Message[] getConversationMessagesFromLatestToSpecified(String friendUserName, int specifiedMessageId) throws Exception {

        SoapObject response = (SoapObject) callMethodWithParametersAndGetSoapResponse(
                Configuration.GET_CONVERSATION_MESSAGES_FROM_LATEST_TO_SPECIFIED_METHOD_NAME,
                friendUserName, specifiedMessageId);

        return retrieveMessageArrayFromSoapObject(response);
    }

    public Message[] getConversationMessagesFromSpecifiedOne(String friendUserName, int lastMessageId, int messageCount) throws Exception {

        SoapObject response = (SoapObject) callMethodWithParametersAndGetSoapResponse(
                Configuration.GET_CONVERSATION_MESSAGES_FROM_SPECIFIED_ONE_METHOD_NAME,
                friendUserName, lastMessageId, messageCount);

        return retrieveMessageArrayFromSoapObject(response);
    }

    public void markConversationMessagesAsRead(String friendName, int[] messagesId) throws Exception {

        callMethodWithParametersAndGetSoapResponse(
                Configuration.MARK_CONVERSATION_MESSAGES_AS_READ_METHOD_NAME,
                friendName, messagesId);
    }

    private Message[] retrieveMessageArrayFromSoapObject(SoapObject object) {

        Message[] result = new Message[object.getPropertyCount()];

        for(int i=0; i<object.getPropertyCount(); i++) {

            result[i] = retrieveMessageFromSoapObject((SoapObject) object.getProperty(i));
        }

        return result;
    }

    private Message retrieveMessageFromSoapObject(SoapObject object) {

        Message result = new Message();

        for (int i = 0; i < object.getPropertyCount(); i++) {

            result.setProperty(i, object.getProperty(i));
        }

        return result;
    }
}
