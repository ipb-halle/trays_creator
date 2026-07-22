package com.location.creator.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.MissingNode;

@Slf4j
@Service
public class LocationTypeClient {

    private final RestClient restClient;
    private final int PAGE_LIMIT = 100;
    private final ObjectMapper objectMapper;

    public LocationTypeClient(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }


    public JsonNode fetchAllTypes() {
        int offset = 0;
        boolean hasMore = true;


        while (hasMore) {
            final int currentOffset = offset;
            try {

                String tree = restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/inventory/types")
                                .queryParam("entityType", "location")
                                .queryParam("page[offset]", currentOffset)
                                .queryParam("page[limit]", PAGE_LIMIT)
                                .build())
                        .retrieve()
                        .body(String.class);

                log.debug("JSON BODY : {}", tree);

                JsonNode root = objectMapper.readTree(tree);
                JsonNode data = root.path("data");
                return data;

            } catch (Exception e) {
                log.warn("Es ist ein Fehler aufgetretten bei locationType: {}", e.getMessage());
            }

        }
        return MissingNode.getInstance();
    }
}
