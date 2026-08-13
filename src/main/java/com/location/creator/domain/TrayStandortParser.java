package com.location.creator.domain;

import java.util.ArrayList;
import java.util.List;

public final class TrayStandortParser {


    private TrayStandortParser() {
    }

    public static TrayParseResult parseTray(List<Tray> trays) {
        List<ResolvedTray> resolved = new ArrayList<>();
        List<UnresolvedTray> unresolved = new ArrayList<>();
        for (Tray t : trays) {
            ParseResult parseResult = StandortParser.parsePath(t.standort());
            if (parseResult.isResolved()) {
                ResolvedTray resolvedTray = new ResolvedTray(t, parseResult.resolvedNodes());
                resolved.add(resolvedTray);
            } else {
                UnresolvedTray unresolvedTray = new UnresolvedTray(t, parseResult.reason());
                unresolved.add(unresolvedTray);
            }
        }
        return new TrayParseResult(resolved, unresolved);
    }
}
