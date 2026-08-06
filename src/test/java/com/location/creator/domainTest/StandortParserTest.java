package com.location.creator.domainTest;

import com.location.creator.domain.LocationNode;
import com.location.creator.domain.LocationTypes;
import com.location.creator.domain.StandortParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StandortParserTest {

    @Test
    public void test_parse_simple_case_of_location_string() {
        String path = "R2-208K1-3";
        LocationNode building = new LocationNode(LocationTypes.BUILDING, "R");
        LocationNode room = new LocationNode(LocationTypes.ROOM, "R2-208");
        LocationNode fridge = new LocationNode(LocationTypes.REFRIGERATOR, "K1");
        LocationNode shelf = new LocationNode(LocationTypes.SHELF, "3");

        List<LocationNode> locationNodes = StandortParser.parsePath(path);
        assertThat(locationNodes).containsExactly(building, room, fridge, shelf);
    }

}
