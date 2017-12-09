package Server;

import Contract.Conversation;
import Contract.Credentials;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;

@WebService(endpointInterface = "Contract.Conversation")
public class ConversationImpl implements Conversation {

    @Resource
    WebServiceContext webServiceCtx;

    @Override
    public String talkToMe() {

        System.out.println("I've talked to someone!");
        return "Hey! I'm talking to you!";
    }

    @Override
    public String logInAndGetToken(Credentials credentials) {

        if (isUserAlreadyLoggedOn(credentials))
            return getUserTokenFromDatabase(credentials);
        else {
            String token = generateTokenForUser(credentials);
            putUserTokenAndCredentialsPairToDatabase(token, credentials);
            return token;
        }
    }

    @Override
    public void logOut() {

        String token = getTokenFromHttpRequest();
        deleteUserTokenCredentialsPairFromDatabase(token);
    }

    private String getTokenFromHttpRequest() {

        MessageContext messageCtx = webServiceCtx.getMessageContext();
        Map http_headers = (Map) messageCtx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List tokens = (List) http_headers.get("Token");

        String token = "";
        if (tokens != null)
            token = tokens.get(0).toString();

        return token;
    }

    private boolean isUserAlreadyLoggedOn(Credentials credentials) {

        //TODO sprawdzanie czy jest zalogowany
        return false;
    }

    private String getUserTokenFromDatabase(Credentials credentials) {

        //TODO pobieranie tokenu z kontenera
        return "token";
    }

    private String generateTokenForUser(Credentials credentials){

        //TODO generowanie tokenow
        return "token";
    }

    private void putUserTokenAndCredentialsPairToDatabase(String token, Credentials credentials){

        //TODO umieszczanie tokenu w jakims kontenerze
    }

    private void deleteUserTokenCredentialsPairFromDatabase(String token) {

        //TODO usuwanie tokenu i credentiali z kontenera
    }
}
