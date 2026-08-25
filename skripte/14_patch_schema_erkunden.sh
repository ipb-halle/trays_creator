#!/bin/bash

# Findet heraus, WELCHE Attribute der PATCH-Endpunkt einer Location ueberhaupt annimmt.
# Hintergrund: PATCH lehnt 'barcode' ab - aber auch 'name'. Also muss erst geklaert werden,
# was das Schema erlaubt, sonst weiss man nicht, ob 'barcode' gesperrt ist oder nur
# unser Body falsch war.
#
# WICHTIG: PATCH braucht IMMER ?force=true (oder einen taggenauen ?digest=<wert> aus
# einem frischen GET) - sonst antwortet der Server pauschal "Missing required parameters"
# und man haelt faelschlich alles fuer verboten.
#
# Aufruf: ./14_patch_schema_erkunden.sh [location-uuid]
# Ergebnis: ../api_jsons/barcodes/schema_<attribut>.json + Uebersicht im Terminal
#
# ACHTUNG: angenommene PATCHes aendern die Test-Location wirklich. Es werden deshalb
# nur unschaedliche Werte geschickt (gleiche Werte wie bisher bzw. leere Werte).

set -euo pipefail

BASE=$(grep -E '^base_url=' ../.env.properties | cut -d= -f2-)
KEY=$(grep -E '^api_key=' ../.env.properties | cut -d= -f2-)

ID="${1:-631552bd-4af6-4b69-82ff-43bc4eb0705d}"
outdir=../api_jsons/barcodes
mkdir -p "$outdir"

SECURITY='{"id":"242870a3-dc72-4c84-82e7-6ab8cfc854e7","content":{"value":"Default"}}'

probiere() {  # probiere <name> <attributes-json>
  local titel="$1" attrs="$2"
  local body="{\"data\":{\"type\":\"inventoryLocation\",\"attributes\":$attrs}}"
  curl -s -w "\nHTTP %{http_code}\n" -X PATCH \
    -H "X-API-Key: $KEY" \
    -H "content-type: application/vnd.api+json" \
    -H "accept: application/vnd.api+json" \
    -d "$body" \
    "$BASE/inventory/locations/$ID?force=true" > "$outdir/schema_$titel.json"

  local code
  code=$(tail -n 2 "$outdir/schema_$titel.json" | grep -o 'HTTP [0-9]*' | tail -n 1 | cut -d' ' -f2)
  printf '%-14s HTTP %-4s ' "$titel" "$code"
  python3 -c "
import json,sys
z = open(sys.argv[1], encoding='utf-8').read().strip().splitlines()
if z and z[-1].startswith('HTTP '): z = z[:-1]
try:
    d = json.loads('\n'.join(z))
except Exception:
    print(); sys.exit()
if 'errors' in d:
    print('ABGELEHNT:', d['errors'][0].get('detail','?')[:160])
else:
    print('ANGENOMMEN')
" "$outdir/schema_$titel.json"
}

echo "PATCH-Schema erkunden fuer Location $ID"
echo "-------------------------------------------------------------------"

probiere "leer"        '{}'
probiere "fields"      "{\"fields\":[$SECURITY]}"
probiere "description" '{"description":"Testbeschreibung"}'
probiere "name"        '{"name":"Test Barcode Kontrolle umbenannt"}'
probiere "barcode"     '{"barcode":"SW-BC-PATCH-TEST"}'
probiere "status"      '{"status":"AVAILABLE"}'
probiere "isGrid"      '{"isGrid":false}'
probiere "ancestors"   '{"ancestors":[{"id":"f438fa95-e58a-4c09-843d-dbebfd084fcb"}]}'

echo "-------------------------------------------------------------------"
echo "Antworten liegen in $outdir/schema_*.json"
