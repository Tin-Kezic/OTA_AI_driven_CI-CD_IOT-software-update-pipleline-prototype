package telemetry;

public class TelemetryService {

    private final double successRate;
    private final int crashCount;

    public TelemetryService(double successRate, int crashCount) {
        this.successRate = successRate;
        this.crashCount = crashCount;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public int getCrashCount() {
        return crashCount;
    }
}