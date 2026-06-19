import agent.OtaAgent;
import model.Device;
import model.UpdatePackage;
import telemetry.TelemetryService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Device> canaryDevices = List.of(
                new Device("ESP32-CANARY-001", "1.0.0", true),
                new Device("ESP32-CANARY-002", "1.0.0", true)
        );

        List<Device> productionDevices = List.of(
                new Device("ESP32-101", "1.0.0", false),
                new Device("ESP32-102", "1.0.0", false),
                new Device("ESP32-103", "1.0.0", false)
        );

        UpdatePackage updatePackage = new UpdatePackage("1.1.0");

        System.out.println("\n===== SCENARIO 1: SUCCESS =====");

        // ✅ uspješan rollout
        TelemetryService successTelemetry =
                new TelemetryService(99.0, 1);

        OtaAgent successAgent = new OtaAgent(successTelemetry);

        successAgent.executeCanary(
                canaryDevices,
                productionDevices,
                updatePackage
        );

        System.out.println("\n===== SCENARIO 2: FAILURE =====");

        // ❌ neuspješan rollout
        TelemetryService failTelemetry =
                new TelemetryService(80.0, 20);

        OtaAgent failAgent = new OtaAgent(failTelemetry);

        failAgent.executeCanary(
                canaryDevices,
                productionDevices,
                updatePackage
        );

        System.out.println("\n=== FINAL VERSIONS ===");

        productionDevices.forEach(d ->
                System.out.println(d.getId() + " -> " + d.getCurrentVersion())
        );
    }
}