package Contract;

import Contract.DTO.Credentials;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Authentication {

    @WebMethod
    void registerUser(Credentials credentials) throws Exception;

    @WebMethod
    String logInAndGetToken(Credentials credentials) throws Exception;

    @WebMethod
    void logOut();
}
