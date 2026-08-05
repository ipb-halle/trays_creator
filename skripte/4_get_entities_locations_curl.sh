#!/bin/bash

# gets locations ( API GET Endpont /entities/locations)

BASE=$(grep -E '^base_url=' ../.env.properties | cut -d= -f2-)
KEY=$(grep -E '^api_key=' ../.env.properties | cut -d= -f2-)

curl -s -H "X-API-Key: $KEY" -H "accept: application/vnd.api+json" \
  "$BASE/entities?page%5Boffset%5D=0&page%5Blimit%5D=20&includeTypes=location&includeOptions=untrashed" > ../api_jsons/entities_locations.json