package Client.ServiceHandlers;

import Client.Enums.ServiceTypes;
import Contract.Authentication;
import Contract.Communication;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ServiceBaseHandler <serviceClassType> {

    private static final String SERVER = "localhost";
    private static final String PORT = "8888";
    private static final String NAMESPACE_URI = "http://ServicesImplementation.Server/";

    //this token is shared by every inherited class
    protected static AtomicReference<String> sharedToken = new AtomicReference<>();

    protected serviceClassType serviceObject;
    protected Class<serviceClassType> serviceClass;
    protected ServiceTypes serviceType;

    protected ServiceBaseHandler(Class<serviceClassType> serviceClass) throws Exception{

        if(sharedToken.get() == null) sharedToken.set("");
        this.serviceClass = serviceClass;
        serviceType = getServiceTypeFromClassType();
        getPort();
    }

    public void putTokenToRequest() {

        Map<String, Object> req_ctx = ((BindingProvider) serviceObject).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://"+SERVER+":"+PORT+"/"+serviceType.getPath()+"?wsdl");

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Token", Collections.singletonList(sharedToken.get()));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    }

    private void getPort() throws Exception {

        URL serviceWsdlURL = new URL("http://"+SERVER+":"+PORT+"/"+serviceType.getPath()+"?wsdl");
        QName serviceQName = new QName(NAMESPACE_URI,  serviceType.getLocalPart());

        Service service = Service.create(serviceWsdlURL, serviceQName);
        serviceObject = service.getPort(serviceClass);
    }

    private ServiceTypes getServiceTypeFromClassType() throws Exception {

        if (Communication.class.isAssignableFrom(serviceClass))
            return ServiceTypes.COMMUNICATION;
        else if (Authentication.class.isAssignableFrom(serviceClass))
            return  ServiceTypes.AUTHENTICATION;
        else
            throw new Exception("No ServiceType exist for given type");
    }
}
