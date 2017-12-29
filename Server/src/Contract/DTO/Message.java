package Contract.DTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

    @XmlElement
    private int messageId;
    @XmlElement
    private String messageContent;
    @XmlElement
    private String sender;
    @XmlElement
    private String receiver;
    @XmlElement
    private boolean readByReceiver;

    public Message() {}

    public Message(int messageId,
                   String messageContent,
                   String sender,
                   String receiver) {

        this.messageId = messageId;
        this.messageContent = messageContent;
        this.sender = sender;
        this.receiver = receiver;
        readByReceiver = false;
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

    public boolean isReadByReceiver() {return readByReceiver;}

    public void markAsRead() {readByReceiver = true;}
}
