package com.location.creator.domain;

public enum UnresolvedReason {
    EMPTY_PATH("Location path is missing or empty."),
    NOT_VALID_ROOM("No known room found at the beginning of the location path."),
    NO_DEVICE("No device (K, G or P followed by a number) found in the location path."),
    AMBIGUOUS_NUMBERS("More than two numbers after the device — the hierarchy is ambiguous.");

    private final String reason;

     UnresolvedReason(String reason) {
        this.reason = reason;
    }

    public String message() {
        return reason;
    }
}
