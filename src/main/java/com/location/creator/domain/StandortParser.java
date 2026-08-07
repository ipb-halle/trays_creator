package com.location.creator.domain;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
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

        // extraction of device letter from path K, G, P
        String deviceLetter = locationPath.replace(".", "").replaceAll(room.code(), "").substring(0, 1);

        List<String> numberList = getStrings(locationPath, deviceLetter);

        switch (deviceLetter) {
            case "K" -> {
                deviceNode = new LocationNode(LocationTypes.REFRIGERATOR, deviceLetter + numberList.get(0));
                nodes.add(deviceNode);

                if (numberList.size() >= 2) {
                    if (numberList.size() == 2) {
                        schelfDrawNode = new LocationNode(LocationTypes.SHELF, numberList.get(1));
                    } else {
                        schelfDrawNode = new LocationNode(LocationTypes.SHELF, numberList.get(2));
                    }
                    nodes.add(schelfDrawNode);

                }
            }
            case "G" -> {
                deviceNode = new LocationNode(LocationTypes.FREEZER, deviceLetter + numberList.get(0));
                nodes.add(deviceNode);

                if (numberList.size() >= 2) {
                    if (numberList.size() == 2) {
                        schelfDrawNode = new LocationNode(LocationTypes.DRAWER, numberList.get(1));
                    } else {
                        schelfDrawNode = new LocationNode(LocationTypes.DRAWER, numberList.get(2));
                    }
                    nodes.add(schelfDrawNode);
                }
            }
            case "P" -> {
                deviceNode = new LocationNode(LocationTypes.BENCH, deviceLetter + numberList.get(0));
                nodes.add(deviceNode);

            }
        }

        return nodes;

    }

    private static @NonNull List<String> getStrings(String locationPath, String deviceLetter) {
        String device = switch (deviceLetter) {
            case "K" -> locationPath.substring(locationPath.indexOf("K"));
            case "G" -> locationPath.substring(locationPath.indexOf("G"));
            case "P" -> locationPath.substring(locationPath.indexOf("P"));
            default -> null;
        };

        assert device != null;
        String[] numbers = device.split("[^0-9]+");

        List<String> numberList = new ArrayList<>();
        for (String number : numbers) {
            if (number.isEmpty()) {
                continue;
            }
            numberList.add(number);
        }
        return numberList;
    }
}
