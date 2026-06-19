package agent;

import model.*;
import tools.*;
import telemetry.TelemetryService;

import java.util.List;

public class OtaAgent {

    private OtaState state = OtaState.PREPARED;

    private DeployTool deployTool = new DeployTool();
    private ActivateTool activateTool = new ActivateTool();
    private RolloutDecisionTool rolloutTool = new RolloutDecisionTool();

    private CanaryEvaluationTool canaryTool;

    public OtaAgent(TelemetryService telemetry) {
        this.canaryTool = new CanaryEvaluationTool(telemetry);
    }

    public void executeCanary(
            List<Device> canaryDevices,
            List<Device> productionDevices,
            UpdatePackage updatePackage) {

        state = OtaState.VALIDATED;

        for (Device device : canaryDevices) {
            deployTool.deploy(device, updatePackage);
            activateTool.activate(device, updatePackage);
        }

        state = OtaState.CANARY_DEPLOYED;

        boolean canaryPassed = canaryTool.evaluate();

        state = OtaState.CANARY_EVALUATION;

        if (!rolloutTool.continueRollout(canaryPassed)) {
            state = OtaState.FAILED;
            System.out.println("Canary rollout FAILED ❌");
            return;
        }

        state = OtaState.FULL_ROLLOUT;

        for (Device device : productionDevices) {
            deployTool.deploy(device, updatePackage);
            activateTool.activate(device, updatePackage);
        }

        state = OtaState.ACTIVATED;
        System.out.println("Rollout COMPLETED ✅");
    }
}