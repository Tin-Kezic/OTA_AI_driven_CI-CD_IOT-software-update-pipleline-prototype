package model;

public class Device {
    private String id;
    private String currentVersion;
    private boolean canary;

    public Device(String id,
                  String currentVersion,
                  boolean canary) {

        this.id = id;
        this.currentVersion = currentVersion;
        this.canary = canary;
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
    public boolean isCanary() {
        return canary;
    }
}