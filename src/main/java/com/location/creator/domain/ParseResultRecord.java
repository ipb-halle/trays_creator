package com.location.creator.domain;

import java.util.ArrayList;
import java.util.List;

public record ParseResultRecord(
        String path,
        List<LocationNode> resolvedNodes,
        UnresolvedReason reason
) {

    public static ParseResultRecord resolved(String path, List<LocationNode> resolvedNodes, UnresolvedReason reason) {
        isResolved(true);
        return new ParseResultRecord(path, resolvedNodes, reason);
    }

    public static ParseResultRecord unresolved(String path, List<LocationNode> resolvedNodes, UnresolvedReason reason) {
        isResolved(false);
        return new ParseResultRecord(path, resolvedNodes, reason);
    }

    public static boolean isResolved(boolean descision) {
        return  descision;
    }
}
