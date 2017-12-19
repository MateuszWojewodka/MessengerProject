package Contract.DTO;

public class Message {

    private int messageId;
    private String messageContent;
    private String sender;
    private String receiver;

    public Message(int messageId,
                   String messageContent,
                   String sender,
                   String receiver) {

        this.messageId = messageId;
        this.messageContent = messageContent;
        this.sender = sender;
        this.receiver = receiver;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
