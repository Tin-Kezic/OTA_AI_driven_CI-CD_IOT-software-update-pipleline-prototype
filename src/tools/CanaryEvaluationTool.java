package tools;

import telemetry.TelemetryService;

public class CanaryEvaluationTool {

    private TelemetryService telemetry =
            new TelemetryService();

    public boolean evaluate() {

        double successRate =
                telemetry.getSuccessRate();

        int crashes =
                telemetry.getCrashCount();

        System.out.println(
                "Success rate: "
                        + successRate);

        System.out.println(
                "Crash count: "
                        + crashes);

        return successRate >= 95
                && crashes < 10;
    }
}