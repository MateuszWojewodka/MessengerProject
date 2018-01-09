package Client.ServiceHandlersWithKSoap;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

abstract class ServiceBaseHandler {

    private String moduleName;

    //this token is shared by every inherited class
    protected static AtomicReference<String> sharedToken = new AtomicReference<>();

    protected ServiceBaseHandler(String moduleName) {
        this.moduleName = moduleName;

        if (sharedToken.get() == null)
            sharedToken.set("");
    }

    protected Object callMethodAndGetSoapResponse(String methodName, Object ...params) throws Exception {

        SoapObject request = getSoapRequest(methodName, params);
        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        ht.call(Configuration.SOAP_ACTION, envelope, getHeaderListWithToken(sharedToken.get()));

        return envelope.getResponse();
    }

    private List<HeaderProperty> getHeaderListWithToken(String token) {

        List<HeaderProperty> headerList = new ArrayList<>();
        headerList.add(new HeaderProperty("Token", token));

        return headerList;
    }

    private SoapObject getSoapRequest(String methodName, Object ...params) {

        //important to do it like that, otherwise namespace is added in wrong way
        SoapObject request = new SoapObject("", "ser:" + methodName);
        request.addAttribute("xmlns:ser", Configuration.NAMESPACE);

        for (int i = 0; i < params.length; i++){

            PropertyInfo pi = new PropertyInfo();
            pi.setName("arg" + i);
            pi.setValue(params[i]);
            pi.setType(params[i].getClass());

            request.addProperty(pi);
        }

        return request;
    }

    private HttpTransportSE getHttpTransportSE() {
        HttpTransportSE ht = new HttpTransportSE(
                Proxy.NO_PROXY,
                Configuration.MAIN_REQUEST_URL + moduleName,
                10000);
        return ht;
    }

    private SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        return envelope;
    }
}
