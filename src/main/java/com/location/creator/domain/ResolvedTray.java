package com.location.creator.domain;

import java.util.List;

public record ResolvedTray(Tray tray, List<LocationNode> path) {
}
