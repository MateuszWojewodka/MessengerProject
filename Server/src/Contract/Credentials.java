package Contract;

public class Credentials {

    public String username;
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
}
