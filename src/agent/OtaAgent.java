package agent;

import model.*;
import tools.*;
import telemetry.TelemetryService;

import java.util.List;

public class OtaAgent {

    private OtaState state = OtaState.PREPARED;

    private DeployTool deployTool = new DeployTool();
    private ActivateTool activateTool = new ActivateTool();
    private RollbackTool rollbackTool = new RollbackTool();
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

        // --- CANARY DEPLOY ---
        for (Device device : canaryDevices) {
            deployTool.deploy(device, updatePackage);

            boolean ok = activateTool.activate(device, updatePackage);

            // ❌ rollback if activation fails
            if (!ok) {
                rollbackTool.rollback(device);
                state = OtaState.FAILED;
                System.out.println("FAILED during CANARY activation");
                return;
            }
        }

        state = OtaState.CANARY_DEPLOYED;

        boolean canaryPassed = canaryTool.evaluate();

        state = OtaState.CANARY_EVALUATION;

        // ❌ rollback decision based on telemetry
        if (!rolloutTool.continueRollout(canaryPassed)) {

            System.out.println("Canary FAILED → rolling back canary devices");

            for (Device device : canaryDevices) {
                rollbackTool.rollback(device);
            }

            state = OtaState.FAILED;
            return;
        }

        state = OtaState.FULL_ROLLOUT;

        // --- PRODUCTION ROLLOUT ---
        for (Device device : productionDevices) {
            deployTool.deploy(device, updatePackage);

            boolean ok = activateTool.activate(device, updatePackage);

            // ❌ rollback per device failure
            if (!ok) {
                rollbackTool.rollback(device);
                state = OtaState.FAILED;
                System.out.println("FAILED during PRODUCTION rollout");
                return;
            }
        }

        state = OtaState.ACTIVATED;
        System.out.println("Rollout COMPLETED SUCCESSFULLY");
    }
}