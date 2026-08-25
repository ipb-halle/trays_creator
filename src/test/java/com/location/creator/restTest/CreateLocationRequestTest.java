package com.location.creator.restTest;


import com.location.creator.domain.LocationNode;
import com.location.creator.domain.LocationTypes;
import com.location.creator.rest.CreateLocationRequest;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateLocationRequestTest {

    @Test
    void of_createsBodyForNonTray() {
        // 8        4   4   4       12 <- UUID
        String typeId = "11111111-2222-3333-4444-555555555555";
        LocationNode node = LocationNode.of(LocationTypes.REFRIGERATOR, "K1", "R302");
        String ancestorEid = "location:37935741-e8ec-4622-aa6a-814b22c8a1ac:ivt";
        ObjectMapper mapper = JsonMapper.builder().build();

        ObjectNode body = CreateLocationRequest.of(typeId, node.name(), ancestorEid).toJson(mapper);
        assertThat(body.path("data").path("type").asString()).isEqualTo("inventoryLocation");
        assertThat(body.path("data").path("attributes").path("name").asString()).isEqualTo(node.name());
        assertThat(body.path("data").path("attributes").path("typeId").asString()).isEqualTo(typeId);
        assertThat(body.path("data").path("attributes").path("ancestors").size()).isEqualTo(1);
        assertThat(body.path("data").path("attributes").path("ancestors").path(0).path("id").asString()).isEqualTo("37935741-e8ec-4622-aa6a-814b22c8a1ac");
        assertThat(body.path("data").path("attributes").path("fields").path(0).path("content").path("value").asString()).isEqualTo("Default");
        assertThat(body.path("data").path("attributes").path("isGrid").isMissingNode()).isTrue();
        assertThat(body.path("data").path("attributes").path("rows").isMissingNode()).isTrue();
        assertThat(body.path("data").path("attributes").path("columns").isMissingNode()).isTrue();
    }


    @Test
    public void forRoom_createsBodyWithoutAncestorForRoot() {
        // 8        4   4   4       12 <- UUID
        String typeId = "11111111-2222-3333-4444-555555555555";
        LocationNode node = LocationNode.of(LocationTypes.ROOM, "R302", "R302");
        ObjectMapper mapper = JsonMapper.builder().build();

        ObjectNode body = CreateLocationRequest.of(typeId, node.name(), null).toJson(mapper);
        System.out.println(body);
        JsonNode ancestors = body.path("data").path("attributes").path("ancestors");

        assertThat(ancestors.size()).isEqualTo(0);
    }

    @Test
    public void forTray_createsBodyForTray() {
        // 8        4   4   4       12 <- UUID
        String typeId = "11111111-2222-3333-4444-555555555555";
        String ancestorId = "shelf:12345";
        LocationNode node = LocationNode.of(LocationTypes.TRAY, "TM001", "R302");
        ObjectMapper mapper = JsonMapper.builder().build();

        ObjectNode body = CreateLocationRequest.ofTray(typeId, node.name(), ancestorId, 8, 3).toJson(mapper);
        System.out.println(body);
        JsonNode attributes = body.path("data").path("attributes");

        assertThat(attributes.path("isGrid").asBoolean()).isTrue();
        assertThat(attributes.path("rows").asInt()).isEqualTo(8);
        assertThat(attributes.path("columns").asInt()).isEqualTo(3);
        assertThat(attributes.path("fields").size()).isEqualTo(0);
    }


}
