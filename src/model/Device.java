package model;

public class Device {
    private String id;
    private String currentVersion;
    private String previousVersion;
    private boolean canary;

    public Device(String id, String currentVersion, boolean canary) {
        this.id = id;
        this.currentVersion = currentVersion;
        this.previousVersion = currentVersion;
        this.canary = canary;
    }

    public String getId() {
        return id;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.previousVersion = this.currentVersion;
        this.currentVersion = currentVersion;
    }

    public String getPreviousVersion() {
        return previousVersion;
    }

    public boolean isCanary() {
        return canary;
    }
}