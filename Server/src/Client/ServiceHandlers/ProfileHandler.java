package Client.ServiceHandlers;

import Client.Annotations.TokenAuthenticated;
import Client.Database.DatabaseHandler;
import Contract.DTO.Notifications;
import Contract.Profile;

import java.util.Timer;
import java.util.TimerTask;

public class ProfileHandler extends ServiceBaseHandler <Profile>{

    //volatile means that cache memory is updating with main memory with every call
    private static volatile ProfileHandler instance = null;

    //thread safe operation
    public static ProfileHandler getInstance() throws Exception {
        if (instance == null) {
            synchronized (ProfileHandler.class) {
                if (instance == null) {
                    instance = new ProfileHandler();
                }
            }
        }
        return instance;
    }

    private Timer timerToUpdateNotifications = new Timer();

    private ProfileHandler() throws Exception {
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
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    public void removeFriend(String friendName) throws Exception {
        try {
            serviceObject.removeFriend(friendName);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    public void acceptFriendRequest(String friendName) throws Exception{
        try {
            serviceObject.acceptFriendRequest(friendName);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    public void rejectFriendRequest(String friendName) throws Exception{
        try {
            serviceObject.rejectFriendRequest(friendName);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @TokenAuthenticated
    public Notifications getNotifications() throws Exception{

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
                try {
                    Notifications n = serviceObject.getNotifications();
                    DatabaseHandler.setNotications(n);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        timerToUpdateNotifications.schedule(timerTask, 1000, 300);
    }

    private  void stopNotificationUpdater() {
        timerToUpdateNotifications.cancel();
    }
}
