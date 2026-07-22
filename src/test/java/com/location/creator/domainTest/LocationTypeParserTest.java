package com.location.creator.domainTest;

import com.location.creator.domain.LocationTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LocationTypeParserTest {

    @Test
    public void test_fromName_method_if_location_is_not_in_enum_list() {
        String testWord = "Bin";
        LocationTypes type = LocationTypes.fromName(testWord);
        assertNull(type);
    }

    @Test
    public void test_location_is_null(){
        assertNull(LocationTypes.fromName(null));
    }

    @Test
    public void test_fromName_method_if_it_in_Enum_list() {
        String room = "Room";
        String refrigerator = "Refrigerator";
        String freezer = "Freezer";
        String bench = "Bench";
        String shelf = "Shelf";
        String tray = "Tray";

        LocationTypes roomType = LocationTypes.fromName(room);
        assertEquals(LocationTypes.ROOM, roomType);

        LocationTypes freezerType = LocationTypes.fromName(freezer);
        assertEquals(LocationTypes.FREEZER, freezerType);

        LocationTypes benchType = LocationTypes.fromName(bench);
        assertEquals(LocationTypes.BENCH, benchType);

        LocationTypes frigeType = LocationTypes.fromName(refrigerator);
        assertEquals(LocationTypes.REFRIGERATOR, frigeType);

        LocationTypes shelfType = LocationTypes.fromName(shelf);
        assertEquals(LocationTypes.SHELF, shelfType);

        LocationTypes trayType = LocationTypes.fromName(tray);
        assertEquals(LocationTypes.TRAY, trayType);
    }




}
