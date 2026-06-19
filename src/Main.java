import agent.OtaAgent;
import model.Device;
import model.UpdatePackage;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Device> canaryDevices = List.of(
                new Device(
                        "ESP32-CANARY-001",
                        "1.0.0",
                        true),

                new Device(
                        "ESP32-CANARY-002",
                        "1.0.0",
                        true)
        );

        List<Device> productionDevices = List.of(
                new Device(
                        "ESP32-101",
                        "1.0.0",
                        false),

                new Device(
                        "ESP32-102",
                        "1.0.0",
                        false),

                new Device(
                        "ESP32-103",
                        "1.0.0",
                        false)
        );

        UpdatePackage updatePackage =
                new UpdatePackage("1.1.0");

        OtaAgent agent = new OtaAgent();

        agent.executeCanary(
                canaryDevices,
                productionDevices,
                updatePackage);

        System.out.println("\n=== Final Versions ===");

        canaryDevices.forEach(device ->
                System.out.println(
                        device.getId()
                                + " -> "
                                + device.getCurrentVersion()
                ));

        productionDevices.forEach(device ->
                System.out.println(
                        device.getId()
                                + " -> "
                                + device.getCurrentVersion()
                ));
    }
}