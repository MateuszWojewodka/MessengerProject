package Client.ServiceHandlersWithKSoap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Proxy;

abstract class ServiceBaseHandler {

    private String moduleName;

    protected ServiceBaseHandler(String moduleName) {
        this.moduleName = moduleName;
    }

    protected Object callMethodAndGetSoapResponse(String methodName, Object ...params) throws Exception {

        SoapObject request = getSoapRequest(methodName, params);
        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        ht.call(Configuration.SOAP_ACTION, envelope);

        return envelope.getResponse();
    }

    private SoapObject getSoapRequest(String methodName, Object ...params) {

        //important to do it like that, otherwise way namespace is added in wrong way
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
