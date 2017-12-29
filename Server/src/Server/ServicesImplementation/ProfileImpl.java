package Server.ServicesImplementation;

import Contract.DTO.Notifications;
import Contract.Profile;
import Server.Database.DatabaseHandler;

import javax.management.Notification;

public class ProfileImpl extends ServiceBaseImpl implements Profile {

    @Override
    public String[] getFriendsList() throws Exception {
        return DatabaseHandler.getFriendList(getNameOfUser());
    }

    @Override
    public void sendFriendRequest(String friendName) throws Exception {
        DatabaseHandler.addFriendRequestToUserProfile(friendName, getNameOfUser());
    }

    @Override
    public void removeFriend(String friendName) throws Exception {
        DatabaseHandler.removeFriendFromUser(getNameOfUser(), friendName);
    }

    @Override
    public void acceptFriendRequest(String friendName) throws Exception {
        DatabaseHandler.addFriendToUser(getNameOfUser(), friendName);
        DatabaseHandler.removeFriendRequestFromUserProfile(getNameOfUser(), friendName);
    }

    @Override
    public void rejectFriendRequest(String friendName) throws Exception {
        DatabaseHandler.removeFriendRequestFromUserProfile(getNameOfUser(), friendName);
    }

    @Override
    public Notifications getNotifications() throws Exception {
        return DatabaseHandler.getNotifications(getNameOfUser());
    }
}
