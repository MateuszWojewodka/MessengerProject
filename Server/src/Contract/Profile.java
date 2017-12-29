package Contract;

import Contract.DTO.Notifications;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Profile {

    @WebMethod
    String[] getFriendsList() throws Exception;

    @WebMethod
    void sendFriendRequest(String friendName) throws Exception;

    @WebMethod
    void removeFriend(String friendName) throws Exception ;

    @WebMethod
    void acceptFriendRequest(String friendName) throws Exception;

    @WebMethod
    void rejectFriendRequest(String friendName) throws Exception ;

    @WebMethod
    Notifications getNotifications() throws Exception;
}
