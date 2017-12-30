package Server.Database;

public class UsersPair {

    private String firstUser;
    private String secondUser;
    private int hashCode;

    public UsersPair(String firstUser, String secondUser) {

        this.firstUser = firstUser;
        this.secondUser = secondUser;

        generateHashCode();
    }

    private void generateHashCode() {

        int fHashCode = firstUser.hashCode();
        int sHashCode = secondUser.hashCode();

        if (fHashCode > sHashCode)
            hashCode = new String(firstUser+secondUser).hashCode();
        else
            hashCode = new String(secondUser+firstUser).hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;

        if (!UsersPair.class.isAssignableFrom(obj.getClass()))
            return false;

        final UsersPair other = (UsersPair) obj;
        if (isFirstEqualsToFirstAndSecondToSecond(other))
            return true;

        if (isFirstEqualsToSecondAndSecondToFirst(other))
            return true;

        return false;
    }

    private boolean isFirstEqualsToFirstAndSecondToSecond(UsersPair other) {
        if ((this.firstUser == null) ? (other.firstUser != null) : !this.firstUser.equals(other.firstUser))
            return false;

        if ((this.secondUser == null) ? (other.secondUser != null) : !this.secondUser.equals(other.secondUser))
            return false;

        return true;
    }

    private boolean isFirstEqualsToSecondAndSecondToFirst(UsersPair other) {
        if ((this.firstUser == null) ? (other.secondUser != null) : !this.firstUser.equals(other.secondUser))
            return false;

        if ((this.secondUser == null) ? (other.firstUser != null) : !this.secondUser.equals(other.firstUser))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
