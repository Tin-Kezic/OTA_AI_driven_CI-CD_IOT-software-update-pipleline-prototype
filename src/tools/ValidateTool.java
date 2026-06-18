package tools;

import model.Device;
import model.UpdatePackage;

public class ValidateTool {

    public boolean validateUpdate(Device device,
                                  UpdatePackage updatePackage) {

        System.out.println("Validating update...");

        return !device.getCurrentVersion()
                .equals(updatePackage.getVersion());
    }
}