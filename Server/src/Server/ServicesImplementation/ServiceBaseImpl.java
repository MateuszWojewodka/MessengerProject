package Server.ServicesImplementation;

import Server.Database.DatabaseHandler;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;

public abstract class ServiceBaseImpl {

    @Resource
    WebServiceContext webServiceCtx;

    protected String getTokenFromHttpRequest() {

        MessageContext messageCtx = webServiceCtx.getMessageContext();
        Map http_headers = (Map) messageCtx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List tokens = (List) http_headers.get("Token");

        String token = "";
        if (tokens != null)
            token = tokens.get(0).toString();

        return token;
    }

    protected boolean isUserIsRegistered(String username) {

        return DatabaseHandler.isUserInRegisteredUsers(username);
    }

    protected boolean isUserIsLoggedOn() {

        String token = getTokenFromHttpRequest();

        if (token == null || !DatabaseHandler.isTokenInUseByLoggedUser(token))
            return false;
        else
            return true;
    }

    protected String getNameOfUser() throws  Exception{

        String token = getTokenFromHttpRequest();
        return DatabaseHandler.getUsernameFromToken(token);
    }

    public void throwExceptionIfCurrentUserIsNotLoggedOn() throws Exception {

        if (!isUserIsLoggedOn()) {
            throw new Exception("User is not logged on.");
        }
    }

    public void throwExceptionIfUserIsNotRegistered(String username) throws  Exception {

        if (!isUserIsRegistered(username))
            throw new Exception("User " + username + " is not registered.");
    }

    public void throwExceptionIfUserIsNotFriendOf(String userWhichFriendListWillBeChecked) throws Exception {

        if(!DatabaseHandler.isUserOnFriendListOf(getNameOfUser(), userWhichFriendListWillBeChecked))
            throw new Exception(userWhichFriendListWillBeChecked + " doesn't have you on his friend list.");
    }
}
