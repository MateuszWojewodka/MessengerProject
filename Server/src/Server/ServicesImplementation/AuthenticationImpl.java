package Server.ServicesImplementation;

import Contract.Authentication;
import Contract.DTO.Credentials;
import Server.Configuration;
import Server.Database.DatabaseHandler;

import javax.jws.WebService;
import java.util.Random;

@WebService(endpointInterface = "Contract.Authentication")
public class AuthenticationImpl  extends ServiceBaseImpl implements Authentication {

    @Override
    public void registerUser(Credentials credentials) throws Exception {

        DatabaseHandler.addUserToRegisteredUsers(credentials);
    }

    @Override
    public String logInAndGetToken(Credentials credentials) throws Exception {

        throwExceptionIfUserIsNotRegistered(credentials.username);

        String token;
        token = DatabaseHandler.checkIfUserIsLoggedOnAndGetToken(credentials);

        if (token == null) {
            token = generateTokenForUser();
            DatabaseHandler.addUserToLoggedUsers(token, credentials);
        }
        return token;
    }

    @Override
    public void logOut() {

        String token = getTokenFromHttpRequest();
        DatabaseHandler.removeUserFromLoggedUsers(token);
    }

    private String generateTokenForUser(){

        String token;

        do {
           token =  generateRandomToken();
        } while (DatabaseHandler.isTokenInUseByLoggedUser(token));

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
