package com.location.creator.domain;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class StandortParser {

    private static final char NO_DEVICE = '\0';

    private StandortParser() {
    }

    public static List<LocationNode> parsePath(String locationPath) {

        if (locationPath == null) return null;
        List<LocationNode> nodes = new ArrayList<>();

        Rooms room = LocationResolver.fromStandort(locationPath);
        LocationNode roomNode = new LocationNode(LocationTypes.ROOM, room.code());

        String building = room.code().substring(0, 1);
        LocationNode buildNode = new LocationNode(LocationTypes.BUILDING, building);

        nodes.add(buildNode);
        nodes.add(roomNode);

        // extraction of device letter from path K, G, P
        String locationPathCleaned = locationPath.trim().replace(".", "").replace("-", "").toUpperCase();

        char deviceChar = getDeviceLetter(locationPathCleaned);

        List<String> listOfDeviceAndItsChildNumbers = extractNumbers(locationPath, String.valueOf(deviceChar));

        LocationTypes deviceType = switch (deviceChar) {
            case 'K' -> LocationTypes.REFRIGERATOR;
            case 'G' -> LocationTypes.FREEZER;
            case 'P' -> LocationTypes.BENCH;
            default -> null;
        };

        LocationTypes childType = switch (deviceChar) {
            case 'K' -> LocationTypes.SHELF;
            case 'G' -> LocationTypes.DRAWER;
            case 'P' -> null;
            default -> null;
        };

        addDeviceAndChild(deviceType, childType, listOfDeviceAndItsChildNumbers, nodes, deviceChar);

        return nodes;

    }

    private static char getDeviceLetter(String locationPathCleaned) {
        for (int i = 0; i < locationPathCleaned.length(); i++) {
            char c = locationPathCleaned.charAt(i);
            if (c == 'K' || c == 'G' || c == 'P') {
                if ((i != (locationPathCleaned.length() - 1))) {
                    char number = locationPathCleaned.charAt(i + 1);
                    if (Character.isDigit(number)) {
                        return c;
                    }
                }
            }
        }
        return NO_DEVICE;
    }

    private static void addDeviceAndChild(LocationTypes deviceType, LocationTypes childType, List<String> listOfDeviceAndItsChildNumbers, List<LocationNode> nodes, char deviceChar) {
        LocationNode deviceNode;
        LocationNode childNode;
        deviceNode = new LocationNode(deviceType, deviceChar + listOfDeviceAndItsChildNumbers.get(0));
        nodes.add(deviceNode);
        if (listOfDeviceAndItsChildNumbers.size() == 2) {
            childNode = new LocationNode(childType, listOfDeviceAndItsChildNumbers.get(1));
            nodes.add(childNode);
        }
    }

    private static List<String> extractNumbers(String locationPath, String deviceLetter) {

        String[] numbers = locationPath.substring(locationPath.indexOf(deviceLetter)).split("[^0-9]+");
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
