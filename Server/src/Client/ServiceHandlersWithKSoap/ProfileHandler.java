package Client.ServiceHandlersWithKSoap;

import Contract.DTO.Notifications;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ProfileHandler extends ServiceBaseHandler {

    ProfileHandler() {
        super(Configuration.PROFILE_MODULE_NAME);
    }

    String[] getFriendsList() throws Exception {

        SoapObject soapResult = (SoapObject) callMethodWithParametersAndGetSoapResponse(
                Configuration.GET_FRIEND_LIST_METHOD_NAME,
                null);

        return retrieveStringArrayFromSoapObject(soapResult);
    }

    void sendFriendRequest(String friendName) throws Exception {

        callMethodWithParametersAndGetSoapResponse(
                Configuration.SEND_FRIEND_REQUEST_METHOD_NAME,
                friendName);
    }

    void removeFriend(String friendName) throws Exception {

        callMethodWithParametersAndGetSoapResponse(
                Configuration.REMOVE_FRIEND_METHOD_NAME,
                friendName);
    }

    void acceptFriendRequest(String friendName) throws Exception {

        callMethodWithParametersAndGetSoapResponse(
                Configuration.ACCEPT_FRIEND_REQUEST_METHOD_NAME,
                friendName);
    }

    void rejectFriendRequest(String friendName) throws Exception {

        callMethodWithParametersAndGetSoapResponse(
                Configuration.REJECT_FRIEND_REQUEST_METHOD_NAME,
                friendName);
    }

    Notifications getNotifications() throws Exception {

        SoapObject soapResult = (SoapObject) callMethodWithParametersAndGetSoapResponse(
                Configuration.GET_NOTIFICATIONS_METHOD_NAME,
                null);

        return retreiveNotificationsFromSoapResponse(soapResult);
    }

    private Notifications retreiveNotificationsFromSoapResponse(SoapObject soapObject) {

        Notifications result = new Notifications();

        for(int i = 0; i < soapObject.getPropertyCount(); i++) {

            PropertyInfo pi = new PropertyInfo();
            soapObject.getPropertyInfo(i, pi);

            if (pi.getName() == "friendRequestsSenders")
                result.friendRequestsSenders.add(soapObject.getProperty(i).toString());
            else if(pi.getName() == "newMessagesSenders")
                result.newMessagesSenders.add(soapObject.getProperty(i).toString());
        }

        return result;
    }

    private List<String> getClassFieldNames(Class<?> className) {

        List<String> result = new ArrayList<>();

        Field[] fields = className.getFields();

        for (Field field : fields) {
            result.add(field.getName());
        }

        return result;
    }

    private String[] retrieveStringArrayFromSoapObject(SoapObject soapObject) {

        String[] result = new String[soapObject.getPropertyCount()];

        for (int i = 0; i < soapObject.getPropertyCount(); i++) {

            result[i] = soapObject.getProperty(i).toString();
        }

        return result;
    }
}
