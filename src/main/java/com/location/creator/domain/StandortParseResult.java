package com.location.creator.domain;

import java.util.List;

public record StandortParseResult(List<ResolvedTray> resolved, List<UnresolvedTray> unresolved) {
}
