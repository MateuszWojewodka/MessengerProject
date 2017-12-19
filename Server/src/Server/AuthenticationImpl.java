package Server;

import Contract.Authentication;
import Contract.Credentials;
import Server.Datebase.AuthenticationLogic;
import Server.Datebase.Database;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.crypto.Data;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;
import java.util.Random;

@WebService(endpointInterface = "Contract.Authentication")
public class AuthenticationImpl implements Authentication {

    @Resource
    WebServiceContext webServiceCtx;

    @Override
    public void registerUser(Credentials credentials) {

        //Database.INSTANCE.registerUser(credentials);
        Database.INSTANCE.authenticationLogic.registerUser(credentials);
    }

    @Override
    public String logInAndGetToken(Credentials credentials) throws Exception {

        if (!Database.INSTANCE.authenticationLogic.isUserRegistered(credentials))
            throw new Exception("User is not registered.");

        String token;

        token = Database.INSTANCE.authenticationLogic.getTokenIfUserIsAlreadyLoggedOn(credentials);
        if (token != null) return token;

        token = generateTokenForUser();
        Database.INSTANCE.authenticationLogic.logUserIn(token, credentials);

        return token;
    }

    @Override
    public void logOut() {

        String token = getTokenFromHttpRequest();
        Database.INSTANCE.authenticationLogic.logUserOut(token);
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

    private String generateTokenForUser(){

        String token;

        do {
           token =  generateRandomToken();
        } while (Database.INSTANCE.authenticationLogic.isTokenAlreadyInDatabase(token));

        return token;
    }

    private String generateRandomToken() {

        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < Configuration.TOKEN_LENGHT; i++) {
            int num = rand.nextInt(Configuration.TOKEN_GENERATOR_ALPHABET.length());
            sb.append(Configuration.TOKEN_GENERATOR_ALPHABET.charAt(num));
        }

        return sb.toString();
    }
}
