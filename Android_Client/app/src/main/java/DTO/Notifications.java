package DTO;

import java.util.*;

public class Notifications {

    public Set<String> friendRequestsSenders;
    public Set<String> newMessagesSenders;

    public Notifications()  {

        friendRequestsSenders = Collections.synchronizedSet(new HashSet<String>());
        newMessagesSenders = Collections.synchronizedSet(new HashSet<String>());
    }
}
