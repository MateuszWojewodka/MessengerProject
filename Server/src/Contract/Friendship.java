package Contract;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Friendship {

    @WebMethod
    void addFriend(String userName);

    @WebMethod
    void removeFriend(String userName);
}
