package com.location.creator.domain;

import java.util.ArrayList;
import java.util.List;

public record ParseResultRecord(
        String path,
        List<LocationNode> resolvedNodes,
        UnresolvedReason reason
) {

    public static ParseResultRecord resolved(String path, List<LocationNode> resolvedNodes) {
        return new ParseResultRecord(path, resolvedNodes, null);
    }

    public static ParseResultRecord unresolved(String path,  UnresolvedReason reason) {
        return new ParseResultRecord(path, List.of(), reason);
    }

    public boolean isResolved() {
        return reason == null;
    }
}
