package Client.Modules;

import Client.Annotations.TokenAuthenticated;
import Client.Database.DatabaseHandler;
import Contract.DTO.Notifications;
import Contract.Profile;

import java.util.Timer;
import java.util.TimerTask;

public class ProfileModule extends ServiceBaseHandler <Profile>{

    //volatile means that cache memory is updating with main memory with every call
    private static volatile ProfileModule instance = null;

    //thread safe operation
    public static ProfileModule getInstance() throws Exception {
        if (instance == null) {
            synchronized (ProfileModule.class) {
                if (instance == null) {
                    instance = new ProfileModule();
                }
            }
        }
        return instance;
    }

    private Timer timerToUpdateNotifications = new Timer();

    private ProfileModule() throws Exception {
        super(Profile.class);
        startNotificationUpdater();
    }

    @TokenAuthenticated
    public String[] getFriendsList() throws Exception {

        String[] result = new String[0];
        try {
            result = serviceObject.getFriendsList();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @TokenAuthenticated
    public void sendFriendRequest(String friendName) throws Exception{

        try {
            serviceObject.sendFriendRequest(friendName);
            System.out.println("Friend request has been sent to " + friendName);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    public void removeFriend(String friendName) throws Exception {
        try {
            serviceObject.removeFriend(friendName);
            System.out.println(friendName + " has been removed from your friendlist.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    public void acceptFriendRequest(String friendName) throws Exception{
        try {
            serviceObject.acceptFriendRequest(friendName);
            System.out.println(friendName + " friend request has been accepted.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    public void rejectFriendRequest(String friendName) throws Exception{
        try {
            serviceObject.rejectFriendRequest(friendName);
            System.out.println(friendName + " friend request has been rejected.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    public Notifications getNotifications() {

        Notifications result = new Notifications();
        try {
            result = serviceObject.getNotifications();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private void startNotificationUpdater() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run(){
                Notifications n = getNotifications();
                DatabaseHandler.setNotications(n);
            }
        };
        timerToUpdateNotifications.schedule(timerTask, 1000, 300);
    }

    private  void stopNotificationUpdater() {
        timerToUpdateNotifications.cancel();
    }
}
