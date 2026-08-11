package com.location.creator.domain;

import java.util.List;

public record TrayParseResult(List<ResolvedTray> resolved, List<UnresolvedTray> unresolved) {
}
