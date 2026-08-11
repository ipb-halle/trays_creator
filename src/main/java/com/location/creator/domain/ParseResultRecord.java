package com.location.creator.domain;

import java.util.List;

public record ParseResultRecord(
        String path,
        List<LocationNode> resolvedNodes,
        ParsePathUnresolvedReason reason
) {
}
