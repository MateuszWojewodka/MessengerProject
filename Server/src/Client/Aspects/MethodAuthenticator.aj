package Client.Aspects;

import Client.Modules.ServiceBaseHandler;

public aspect MethodAuthenticator {

    before(ServiceBaseHandler service):
            target(service) &&
            execution(* *(..)) &&
            @annotation(Client.Annotations.TokenAuthenticated){

        service.putTokenToRequest();
    }
}
