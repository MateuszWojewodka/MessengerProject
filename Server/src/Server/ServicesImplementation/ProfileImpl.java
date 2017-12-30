package Server.ServicesImplementation;

import Contract.DTO.Notifications;
import Contract.Profile;
import Server.Database.DatabaseHandler;

import javax.jws.WebService;
import javax.management.Notification;

@WebService(endpointInterface = "Contract.Profile")
public class ProfileImpl extends ServiceBaseImpl implements Profile {

    public ProfileImpl() {
        Thread notificationTracer = new NotificationTrackerThread();
    }

    @Override
    public String[] getFriendsList() throws Exception {
        return DatabaseHandler.getFriendList(getNameOfUser());
    }

    @Override
    public void sendFriendRequest(String friendName) throws Exception {

        throwExceptionIfUserIsNotRegistered(friendName);

        DatabaseHandler.addFriendRequestToUserProfile(friendName, getNameOfUser());
    }

    @Override
    public void removeFriend(String friendName) throws Exception {
        DatabaseHandler.removeFriendFromUser(getNameOfUser(), friendName);
    }

    @Override
    public void acceptFriendRequest(String friendName) throws Exception {

        throwExceptionIfUserDoesntHaveFriendRequest(getNameOfUser(), friendName);

        DatabaseHandler.addFriendToUser(getNameOfUser(), friendName);
        DatabaseHandler.addFriendToUser(friendName, getNameOfUser());

        DatabaseHandler.removeFriendRequestFromUserProfile(getNameOfUser(), friendName);
    }

    @Override
    public void rejectFriendRequest(String friendName) throws Exception {

        throwExceptionIfUserDoesntHaveFriendRequest(getNameOfUser(), friendName);

        DatabaseHandler.removeFriendRequestFromUserProfile(getNameOfUser(), friendName);
    }

    @Override
    public Notifications getNotifications() throws Exception {
        return DatabaseHandler.getNotifications(getNameOfUser());
    }

    private void throwExceptionIfUserDoesntHaveFriendRequest(
            String userName,
            String requestingUser) throws Exception {

        if (!DatabaseHandler.
                isFriendRequestInUsersNotifications(userName, requestingUser))
            throw new Exception("You doesn't have any request from " + userName);
    }
}
