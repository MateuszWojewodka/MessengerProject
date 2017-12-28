package Client.Enums;

public enum ServiceTypes {

    AUTHENTICATION("authentication", "AuthenticationImplService"),
    COMMUNICATION("communication", "CommunicationImplService");

    private String path;
    private String localPart;

    ServiceTypes(String path, String localPart){

        this.path = path;
        this.localPart = localPart;
    }

    public String getPath() {
        return path;
    }

    public String getLocalPart() {return localPart;}

}
