package tools;

import model.Device;

public class RollbackTool {
    public void rollback(Device device) {
        System.out.println(
                "ROLLBACK: " + device.getId()
                        + " -> " + device.getPreviousVersion()
        );
        device.setCurrentVersion(device.getPreviousVersion());
    }
}