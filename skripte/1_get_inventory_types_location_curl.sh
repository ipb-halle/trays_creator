#!/bin/bash

# gets location types (room, fridge, tray etc. from API GET Endpont /inventory/types)

cd ../ && mkdir -p api_jsons
BASE=$(grep -E '^base_url=' .env.properties | cut -d= -f2-)
KEY=$(grep -E '^api_key=' .env.properties | cut -d= -f2-)

curl -s -H "X-API-Key: $KEY" -H "accept:application/vnd.api+json" \
  "$BASE/inventory/types?entityType=location&page%5Boffset%5D=0&page%5Blimit%5D=100" >  api_jsons/inventory_types_location.json

