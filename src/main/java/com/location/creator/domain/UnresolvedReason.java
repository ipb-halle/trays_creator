package com.location.creator.domain;

public enum UnresolvedReason {
    EMPTY_PATH("Location path is null!"),
    NOT_VALID_ROOM("The room in location path is not exist!"),
    NO_DEVICE("No information about device in location path!");

    private final String reason;

    UnresolvedReason(String reason) {
        this.reason = reason;
    }

    public String getMessageContent() {
        return reason;
    }
}
