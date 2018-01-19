package Helpers;

/**
 * Created by WOJTEK on 2018-01-16.
 */

public class UserAndMessage {

    public DTO.Message message;
    public String user;

    public UserAndMessage(String user, DTO.Message message) {

        this.user = user;
        this.message = message;
    }
}
