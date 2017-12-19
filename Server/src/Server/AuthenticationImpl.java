package Server;

import Contract.Authentication;
import Contract.DTO.Credentials;
import Server.Datebase.Database;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.Random;

@WebService(endpointInterface = "Contract.Authentication")
public class AuthenticationImpl implements Authentication {

    @Resource
    WebServiceContext webServiceCtx;

    @Override
    public void registerUser(Credentials credentials) throws Exception {

        if (Database.INSTANCE.authentication.isUserRegistered(credentials.username))
            throw new Exception("This username is already taken.");

        Database.INSTANCE.authentication.registerUser(credentials);
    }

    @Override
    public String logInAndGetToken(Credentials credentials) throws Exception {

        if (!Database.INSTANCE.authentication.isUserRegistered(credentials.username))
            throw new Exception("User is not registered.");

        String token;

        token = Database.INSTANCE.authentication.getTokenIfUserIsAlreadyLoggedOn(credentials);
        if (token != null) return token;

        token = generateTokenForUser();
        Database.INSTANCE.authentication.logUserIn(token, credentials);

        return token;
    }

    @Override
    public void logOut() {

        String token = RequestHandling.getTokenFromHttpRequest(webServiceCtx);
        Database.INSTANCE.authentication.logUserOut(token);
    }

    private String generateTokenForUser(){

        String token;

        do {
           token =  generateRandomToken();
        } while (Database.INSTANCE.authentication.isTokenAlreadyInDatabase(token));

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
