package Contract;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Conversation {

    @WebMethod
    void registerUser(Credentials credentials);

    @WebMethod
    String logInAndGetToken(Credentials credentials) throws Exception;

    @WebMethod
    void logOut();
}
