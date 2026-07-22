#!/bin/bash
#bei grep steht -E für extendet regular expressin , d.h. man muss nicht ekranieren
BASE=$(grep -E '^base_url=' ../../.env.properties | cut -d= -f2-)
KEY=$(grep -E '^api_key=' ../../.env.properties | cut -d= -f2-)

echo "Base Url: $BASE"
echo "Base Url: $KEY"

# bei curl steht s für silent als man kann keienen download fotschritt nicht sehen
curl -s -H "X-API-Key: $KEY" -H "accept:application/vnd.api+json" \
"$BASE/inventory/types?entityType=location&page%5Boffset%5D=0&page%5Blimit%5D=100" \
>./resources/fixtures/inventory-types.json

