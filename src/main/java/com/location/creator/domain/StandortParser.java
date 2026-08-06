package com.location.creator.domain;

import java.util.ArrayList;
import java.util.List;

public final class StandortParser {

    private StandortParser() {
    }

    public static List<LocationNode> parsePath(String locationPath) {

        if (locationPath == null) return null;
        List<LocationNode> nodes = new ArrayList<>();
        String building = locationPath.trim().substring(0, 1).toUpperCase();
        LocationNode buildNode = new LocationNode(LocationTypes.BUILDING, building);
        nodes.add(buildNode);
        Rooms room = LocationResolver.fromStandort(locationPath);
        LocationNode roomNode = new LocationNode(LocationTypes.ROOM, room.code());
        nodes.add(roomNode);


        LocationNode deviceNode = null;
        LocationNode schelfDrawNode = null;
        String device = locationPath.replaceAll(room.code(), "").substring(0, 1);
        String[] numbers = locationPath.replaceAll(room.code(), "").split("[^0-9]+");
        List<String> numberList = new ArrayList<>();
        for (String number : numbers) {
            if (number.isEmpty()) {
                continue;
            }
            numberList.add(number);
        }
        switch (device) {
            case "K" -> {
                deviceNode = new LocationNode(LocationTypes.REFRIGERATOR, device + numberList.get(0));
                schelfDrawNode = new LocationNode(LocationTypes.SHELF, numberList.get(1));
            }
            case "G" -> {
                deviceNode = new LocationNode(LocationTypes.FREEZER, device + numberList.get(0));
                schelfDrawNode = new LocationNode(LocationTypes.DRAWER, numberList.get(1));
            }
            case "P" -> {
                deviceNode = new LocationNode(LocationTypes.BENCH, device + numberList.get(0));
            }
        }
        nodes.add(deviceNode);
        nodes.add(schelfDrawNode);

        return nodes;

    }
}
