#!/bin/bash

set -euo pipefail

BASE=$(grep -E "^base_url=" ../.env.properties | cut -d= -f2-)
KEY=$(grep -E "^api_key=" ../.env.properties | cut -d= -f2-)
json=../api_jsons/locations_id.json
outdir=../api_jsons/locations_mit_ancestors

mkdir -p "$outdir"

while IFS=$'\t' read -r key value; do

curl -s -H "X-API-Key: $KEY" -H "accept: application/vnd.api+json" "$BASE/inventory/locations/$value" \
 > ../api_jsons/locations_mit_ancestors/$value.json

done < <(jq -r 'to_entries[] | [.key, .value] | @tsv'  $json )