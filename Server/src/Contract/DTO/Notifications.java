package Contract.DTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
public class Notifications {

    @XmlElement
    public Set<String> friendRequestsSenders;
    @XmlElement
    public Set<String> newMessagesSenders;

    public Notifications()  {

        friendRequestsSenders = Collections.synchronizedSet(new HashSet<>());
        newMessagesSenders = Collections.synchronizedSet(new HashSet<>());
    }
}
