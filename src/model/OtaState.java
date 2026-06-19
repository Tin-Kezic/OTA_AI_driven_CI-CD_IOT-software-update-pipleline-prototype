package model;

public enum OtaState {
    PREPARED,
    VALIDATED,

    CANARY_DEPLOYED,
    CANARY_EVALUATION,

    FULL_ROLLOUT,

    DEPLOYED,
    ACTIVATED,
    FAILED
}