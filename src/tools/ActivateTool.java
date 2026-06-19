package tools;

import model.Device;
import model.UpdatePackage;

public class ActivateTool {

    public boolean activate(Device device,
                            UpdatePackage updatePackage) {

        System.out.println(
                "Activating version " + updatePackage.getVersion()
                        + " on " + device.getId()
        );

        // simulacija greške (npr. uređaj 102 faila)
        if (device.getId().contains("102")) {
            System.out.println("Activation FAILED on " + device.getId());
            return false;
        }

        device.setCurrentVersion(updatePackage.getVersion());
        return true;
    }
}