package Server.Database;

import Contract.DTO.Notifications;

import javax.management.Notification;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class Profile {

    public Set<String> friends;
    public Notifications notification;

    public Profile() {
        friends = Collections.synchronizedSet(new HashSet<>());
        notification = new Notifications();
    }

    public void addFriend(String friendName) {
        friends.add(friendName);
    }

    public void removeFriend(String friendName) {
        friends.remove(friendName);
    }

    public void addFriendRequest(String userRequestingName) {
        notification.friendRequestsSenders.add(userRequestingName);
    }

    public void removeFriendRequest(String userRequestingName) {
        notification.friendRequestsSenders.remove(userRequestingName);
    }

    public Notifications getNotification() {
        return notification;
    }
}
