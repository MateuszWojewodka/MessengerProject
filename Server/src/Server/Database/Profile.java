package Server.Database;

import Contract.DTO.Notifications;

import javax.management.Notification;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Profile {

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

    public boolean isFriendRequestInNotifications(String requestingUserName) {
        return notification.friendRequestsSenders.contains(requestingUserName);
    }

    public void addNewMessageNotification(String messageSender) {
        notification.newMessagesSenders.add(messageSender);
    }

    public void removeNewMessageNotification(String messageSender) {
        notification.newMessagesSenders.remove(messageSender);
    }

    public Notifications getNotification() {
        return notification;
    }
}
