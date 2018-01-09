package Client.ServiceHandlersWithKSoap;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Array;
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

    protected Object callMethodWithParametersAndGetSoapResponse(String methodName, Object ...params) throws Exception {

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

        if (params != null) {

            for (int i = 0; i < params.length; i++){

                if (params[i].getClass().isArray()) {

                    addArrayObjectToRequest(request, params[i], i);
                }
                else {

                    request.addProperty("arg" + i, params[i]);
                }
            }
        }

        return request;
    }

    private void addArrayObjectToRequest(SoapObject request, Object arrayObject, int objectArgumentNumber) {

        SoapObject soapArray = new SoapObject("", "arg" + objectArgumentNumber);

        for (Object object : (Object[]) arrayObject){
            soapArray.addProperty("item", arrayObject);
        }

        request.addSoapObject(soapArray);
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
