package Contract.DTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@XmlRootElement
public class Notifications {

    @XmlElement
    public List<String> friendRequestsSenders;
    @XmlElement
    public List<String> newMessagesSenders;

    public Notifications() {

        friendRequestsSenders = Collections.synchronizedList(new ArrayList<>());
        newMessagesSenders = Collections.synchronizedList(new ArrayList<>());
    }
}
