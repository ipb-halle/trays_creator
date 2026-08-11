package com.location.creator.domain;

import java.util.List;

public record ParseResult(
        String path,
        List<LocationNode> resolvedNodes,
        UnresolvedReason reason
) {

    public static ParseResult resolved(String path, List<LocationNode> resolvedNodes) {
        return new ParseResult(path, resolvedNodes, null);
    }

    public static ParseResult unresolved(String path, UnresolvedReason reason) {
        return new ParseResult(path, List.of(), reason);
    }

    public boolean isResolved() {
        return reason == null;
    }
}
