package agent;

import model.Device;
import model.OtaState;
import model.UpdatePackage;
import tools.ActivateTool;
import tools.DeployTool;
import tools.ValidateTool;

public class OtaAgent {

    private OtaState state = OtaState.PREPARED;

    private ValidateTool validateTool =
            new ValidateTool();

    private DeployTool deployTool =
            new DeployTool();

    private ActivateTool activateTool =
            new ActivateTool();

    public void execute(Device device,
                        UpdatePackage updatePackage) {

        System.out.println("Current state: " + state);

        boolean validated =
                validateTool.validateUpdate(
                        device,
                        updatePackage);

        if (!validated) {
            state = OtaState.FAILED;
            System.out.println("Validation failed");
            return;
        }

        state = OtaState.VALIDATED;
        System.out.println("State -> VALIDATED");

        boolean deployed =
                deployTool.deploy(
                        device,
                        updatePackage);

        if (!deployed) {
            state = OtaState.FAILED;
            return;
        }

        state = OtaState.DEPLOYED;
        System.out.println("State -> DEPLOYED");

        boolean activated =
                activateTool.activate(
                        device,
                        updatePackage);

        if (!activated) {
            state = OtaState.FAILED;
            return;
        }

        state = OtaState.ACTIVATED;
        System.out.println("State -> ACTIVATED");
    }
}