package Server.Datebase;

import java.util.*;

public class xUserProfileLogic {

    //Synchronized collection
    Map<String, Profile> usersProfiles;

    xUserProfileLogic() {
        usersProfiles = Collections.synchronizedMap(new HashMap<>());
    }

    public void createUserProfile(String username) {
        usersProfiles.put(username, new Profile());
    }

    public void removeUserProfile(String username) {
        usersProfiles.remove(username);
    }

    public void addFriendToUserProfile(String ownerUsername, String friendUsername) {
        usersProfiles.get(ownerUsername).friends.add(friendUsername);
    }

    public void removeFriendFromUserProfile(String ownerUsername, String friendUsername) {
        usersProfiles.get(ownerUsername).friends.remove(friendUsername);
    }

    private class Profile {

        private Set<String> friends;

        public Profile() {
            friends = new HashSet<>();
        }
    }
}
