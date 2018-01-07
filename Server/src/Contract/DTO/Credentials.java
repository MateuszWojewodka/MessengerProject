package Contract.DTO;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Hashtable;

@XmlRootElement
public class Credentials implements KvmSerializable{

    @XmlElement
    public String username;
    @XmlElement
    public String password;

    public Credentials() {}

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;

        if (!Credentials.class.isAssignableFrom(obj.getClass()))
            return false;

        final Credentials other = (Credentials) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username))
            return false;

        if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return new String(username + password).hashCode();
    }

    @Override
    public Object getProperty(int i) {

        switch(i)
        {
            case 0:
                return username;
            case 1:
                return password;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 2;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                username = value.toString();
                break;
            case 1:
                password = value.toString();
                break;
            default: break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch(index) {

            case 0:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "username";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "password";
                break;
            default: break;
        }
    }
}
