package Server.ServicesImplementation;

import Contract.Authentication;
import Contract.DTO.Credentials;
import Server.Configuration;
import Server.Database.DatabaseHandler;

import javax.jws.WebService;
import java.util.Random;

@WebService(endpointInterface = "Contract.Authentication")
public class AuthenticationImpl  extends ServiceBaseImpl implements Authentication {

    private final String TOKEN_GENERATOR_ALPHABET = "1234567890abcdefghijklmnoprstuwxyz-+*/=";
    private final int TOKEN_LENGHT = 10;

    @Override
    public void registerUser(Credentials credentials) throws Exception {

        if (isUserIsRegistered(credentials.username)) {
            throw new Exception("User is already registered. Choose another name.");
        }
        DatabaseHandler.addUserToRegisteredUsersAndCreateHisProfile(credentials);
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

        for (int i = 0; i < TOKEN_LENGHT; i++) {
            int num = rand.nextInt(TOKEN_GENERATOR_ALPHABET.length());
            sb.append(TOKEN_GENERATOR_ALPHABET.charAt(num));
        }

        return sb.toString();
    }
}
