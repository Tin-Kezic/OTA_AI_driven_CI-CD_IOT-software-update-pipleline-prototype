package tools;

import model.Device;
import model.UpdatePackage;

public class ActivateTool {

    public boolean activate(Device device,
                            UpdatePackage updatePackage) {

        System.out.println(
                "Activating version "
                        + updatePackage.getVersion()
        );

        device.setCurrentVersion(
                updatePackage.getVersion()
        );

        return true;
    }
}