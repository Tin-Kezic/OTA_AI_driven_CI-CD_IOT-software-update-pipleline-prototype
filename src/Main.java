import agent.OtaAgent;
import model.Device;
import model.UpdatePackage;

public class Main {

    public static void main(String[] args) {

        Device device =
                new Device(
                        "ESP32-001",
                        "1.0.0");

        UpdatePackage updatePackage =
                new UpdatePackage(
                        "1.1.0");

        OtaAgent agent = new OtaAgent();

        agent.execute(
                device,
                updatePackage);

        System.out.println(
                "Device version: "
                        + device.getCurrentVersion()
        );
    }
}