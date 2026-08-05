#!/bin/bash

#Starten -> create_location.sh <name> <typeId> <ancestor eid> <security yes| no>
set -euo pipefail

BASE=$(grep -E '^base_url=' ../.env.properties | cut -d= -f2-)
KEY=$(grep -E '^api_key=' ../.env.properties | cut -d= -f2-)
NAME="$1"; TYPE="$2"; ANC="$3"; SEC="${4:-yes}"

# diese Konstuktion ist ein Elvis operator
#             ?           TRUE                :  FALSE
[ -n "$ANC" ] && ANCJSON="[{\"id\":\"$ANC\"}]" || ANCJSON="[]"
[ "$SEC" = "yes" ] && FIELDS='[{"id":"242870a3-dc72-4c84-82e7-6ab8cfc854e7","content":{"value":"Default"}}]' || FIELDS='[]'
GRID=""
if [ -n "${5:-}" ] && [ -n "${6:-}" ]; then
  GRID=",\"isGrid\":true,\"rows\":$5,\"columns\":$6"
fi
BODY="{\"data\":{\"type\":\"inventoryLocation\",\"attributes\":{\"name\":\"$NAME\",\"typeId\":\"$TYPE\",\"ancestors\":$ANCJSON,\"fields\":$FIELDS$GRID}}}"


outdir=../api_jsons/response_create_locations_test

mkdir -p "$outdir"

curl -s -w "\nHTTP %{http_code}\n" -X POST \
  -H "X-API-Key: $KEY" \
  -H "content-type: application/vnd.api+json" \
  -H "accept: application/vnd.api+json" \
  -d "$BODY" \
  "$BASE/inventory/locations" | tee "../api_jsons/response_create_locations_test/$NAME.json"