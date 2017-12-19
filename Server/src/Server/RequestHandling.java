package Server;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;

public class RequestHandling {

   public static String getTokenFromHttpRequest(WebServiceContext webServiceCtx) {

        MessageContext messageCtx = webServiceCtx.getMessageContext();
        Map http_headers = (Map) messageCtx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List tokens = (List) http_headers.get("Token");

        String token = "";
        if (tokens != null)
            token = tokens.get(0).toString();

        return token;
    }
}
