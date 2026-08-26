package com.location.creator.restTest;

import com.location.creator.domain.LocationNode;
import com.location.creator.domain.LocationTypes;
import com.location.creator.rest.CreateLocationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationClientTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("https://example.com/api");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();

    @Test
    public void createLocation_successfullyCreates_201() {
        String typeId = "11111111-2222-3333-4444-555555555555";
        LocationNode node = LocationNode.of(LocationTypes.ROOM, "R302", "R302");
        ObjectMapper mapper = JsonMapper.builder().build();

        ObjectNode request = CreateLocationRequest.of(typeId, node.name(), null).toJson(mapper);
        server.expect("http://example.com/api/inventory/locatons")
                .andExpect(HttpMethod.GET)
                .andExpect(request)
                .andRespond(201, new ClassPathResource("fixtures/create_room_response.json"));

        LocationClient client = new LocationClient(restClient, mapper);
        String id = client.createLocation(request);
        assertThat(id).isEqualTo("");
        server.verify();
    }

    @Test
    public void createLocation_checkTheRequest_true() {
    }

    @Test
    public void createLocation_creationFailed_400() {
    }
}
