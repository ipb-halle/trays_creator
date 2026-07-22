package com.location.creator.api;

import com.location.creator.rest.LocationTypeClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api")
public class LocationTypeController {

    private final LocationTypeClient client;

    public LocationTypeController(LocationTypeClient client) {
        this.client = client;
    }


    @GetMapping("/types")
    public JsonNode getTypes() {
        JsonNode jsonNode = client.fetchAllTypes();
        return jsonNode;
    }

}
