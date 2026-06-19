package agent;

import model.Device;
import model.OtaState;
import model.UpdatePackage;
import tools.*;

import java.util.List;

public class OtaAgent {

    private OtaState state =
            OtaState.PREPARED;

    private ValidateTool validateTool =
            new ValidateTool();

    private DeployTool deployTool =
            new DeployTool();

    private ActivateTool activateTool =
            new ActivateTool();

    private CanaryEvaluationTool canaryTool =
            new CanaryEvaluationTool();

    private RolloutDecisionTool rolloutTool =
            new RolloutDecisionTool();

    public void executeCanary(
            List<Device> canaryDevices,
            List<Device> productionDevices,
            UpdatePackage updatePackage) {

        state = OtaState.VALIDATED;

        for (Device device : canaryDevices) {

            deployTool.deploy(
                    device,
                    updatePackage);

            activateTool.activate(
                    device,
                    updatePackage);
        }

        state = OtaState.CANARY_DEPLOYED;

        boolean canaryPassed =
                canaryTool.evaluate();

        state = OtaState.CANARY_EVALUATION;

        if (!rolloutTool.continueRollout(
                canaryPassed)) {

            state = OtaState.FAILED;

            System.out.println(
                    "Canary rollout failed");

            return;
        }

        state = OtaState.FULL_ROLLOUT;

        for (Device device :
                productionDevices) {

            deployTool.deploy(
                    device,
                    updatePackage);

            activateTool.activate(
                    device,
                    updatePackage);
        }

        state = OtaState.ACTIVATED;

        System.out.println(
                "Rollout completed");
    }
}