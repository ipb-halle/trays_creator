package com.location.creator.domain;

import java.util.ArrayList;
import java.util.List;

public final class StandortParser {

    private static final char NO_DEVICE = '\0';

    private StandortParser() {
    }

    public static ParseResultRecord parsePath(String locationPath) {

        if (locationPath == null) {
            return new ParseResultRecord(locationPath, new ArrayList<>(), ParsePathUnresolvedReason.EMPTY_PATH);
        }

        String normalizedLocationPath = locationPath.trim().toUpperCase();

        List<LocationNode> nodes = new ArrayList<>();

        Rooms room = LocationResolver.fromStandort(normalizedLocationPath);
        LocationNode roomNode = new LocationNode(LocationTypes.ROOM, room.code());

        String building = room.code().substring(0, 1);
        LocationNode buildNode = new LocationNode(LocationTypes.BUILDING, building);

        nodes.add(buildNode);
        nodes.add(roomNode);

        String locationPathCleaned = normalizedLocationPath.replace(".", "").replace("-", "");

        char deviceChar = getDeviceLetter(locationPathCleaned);

        List<String> numbers = extractNumbers(normalizedLocationPath, deviceChar);

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

        LocationNode deviceNode = new LocationNode(deviceType, String.valueOf(deviceChar) + numbers.get(0));
        nodes.add(deviceNode);

        if (numbers.size() == 2 && childType != null) {
            LocationNode childNode = new LocationNode(childType, numbers.get(1));
            nodes.add(childNode);
        }

        return new ParseResultRecord(locationPath, nodes, null);

    }

    private static char getDeviceLetter(String locationPathCleaned) {
        int length = locationPathCleaned.length();
        for (int i = 0; i < length; i++) {
            char c = locationPathCleaned.charAt(i);
            //   Buchstabe K G oder P und noch danach ein symbol gibt und dieses symbol it eine Ziffer
            if ((c == 'K' || c == 'G' || c == 'P') && i + 1 < length) {
                char number = locationPathCleaned.charAt(i + 1);
                if (Character.isDigit(number)) return c;
            }
        }
        return NO_DEVICE;
    }

    private static List<String> extractNumbers(String locationPath, char deviceLetter) {
        int indexOfLetter = locationPath.indexOf(deviceLetter);
        String[] numbers = locationPath.substring(indexOfLetter).split("[^0-9]+");
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
