package tools;

import model.Device;
import model.UpdatePackage;

public class DeployTool {

    public boolean deploy(Device device,
                          UpdatePackage updatePackage) {

        System.out.println(
                "Deploying version "
                        + updatePackage.getVersion()
                        + " to device "
                        + device.getId()
        );

        return true;
    }
}