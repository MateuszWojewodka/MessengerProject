package Contract.DTO;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Hashtable;

@XmlRootElement
public class Message implements KvmSerializable {

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
    @XmlElement
    private long messageTime;

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
        this.messageTime = System.currentTimeMillis();
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

    public long getMessageTime() {
        return messageTime;
    }

    public void markAsRead() {readByReceiver = true;}

    @Override
    public Object getProperty(int i) {

        switch (i)
        {
            case 0:
                return messageId;
            case 1:
                return messageContent;
            case 2:
                return sender;
            case 3:
                return receiver;
            case 4:
                return readByReceiver;
            case 5:
                return messageTime;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 6;
    }

    @Override
    public void setProperty(int index, Object value) {

        switch (index) {
            case 0:
                messageId = Integer.parseInt(value.toString());
                break;
            case 1:
                messageContent = value.toString();
                break;
            case 2:
                sender = value.toString();
                break;
            case 3:
                receiver= value.toString();
                break;
            case 4:
                readByReceiver = Boolean.getBoolean(value.toString());
                break;
            case 5:
                messageTime = Long.parseLong(value.toString());
            default: break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch(index) {

            case 0:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "messageId";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "messageContent";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "sender";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "receiver";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.BOOLEAN_CLASS;
                propertyInfo.name = "readByReceiver";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.LONG_CLASS;
                propertyInfo.name = "messageTime";
            default: break;
        }
    }
}
