#!/bin/bash

BASE=$(grep -E '^base_url=' ../.env.properties | cut -d= -f2-)
KEY=$(grep -E '^api_key=' ../.env.properties | cut -d= -f2-)

outdir=../api_jsons/response_create_locations_test

mkdir -p "$outdir"

curl -s -w "\nHTTP %{http_code}\n" -X POST \
  -H "X-API-Key: $KEY" \
  -H "content-type: application/vnd.api+json" \
  -H "accept: application/vnd.api+json" \
  -d '{"data":{"type":"inventoryLocation","attributes":{"name":"Test_Building_Sergei","typeId":"62648727-2ea8-41e1-8e67-d1ee4d7d4af5","fields":[ {"id":"242870a3-dc72-4c84-82e7-6ab8cfc854e7","content":{"value":"Default"}} ]}}}' \
  "$BASE/inventory/locations" | tee ../api_jsons/response_create_locations_test/create_building_response.json