package model;

public class Device {

    private String id;
    private String currentVersion;

    public Device(String id, String currentVersion) {
        this.id = id;
        this.currentVersion = currentVersion;
    }

    public String getId() {
        return id;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
}