#!/bin/bash

# Worker: legt EINE Test-Location an und speichert Request + Response, um zu pruefen,
# ob ein selbst gesetzter Barcode uebernommen, ueberschrieben oder abgelehnt wird.
#
# Aufruf:
#   ./barcode_test_post.sh <variante> <name> <typeId> <ancestor-uuid> [barcode]
#
#   variante = attribut  -> Barcode als Top-Level-Attribut   "attributes":{... "barcode":"..."}
#   variante = feld      -> Barcode als Feld-Eintrag         "fields":[{"id":"Barcode",...}]
#   variante = ohne      -> Kontrolllauf ganz ohne Barcode   (zeigt die naechste Auto-Nummer)
#
#   ancestor-uuid = REINE uuid ohne 'location:' und ohne ':ivt'; leer "" -> Wurzelknoten
#
# Ergebnis: ../api_jsons/barcodes/<variante>_<name>_request.json
#           ../api_jsons/barcodes/<variante>_<name>_response.json

set -euo pipefail

BASE=$(grep -E '^base_url=' ../.env.properties | cut -d= -f2-)
KEY=$(grep -E '^api_key=' ../.env.properties | cut -d= -f2-)

VARIANTE="$1"; NAME="$2"; TYPE="$3"; ANC="${4:-}"; BARCODE="${5:-}"

SECURITY='{"id":"242870a3-dc72-4c84-82e7-6ab8cfc854e7","content":{"value":"Default"}}'

# Elvis-Konstruktion:  Bedingung && DANN || SONST
[ -n "$ANC" ] && ANCJSON="[{\"id\":\"$ANC\"}]" || ANCJSON="[]"

case "$VARIANTE" in
  attribut)
    [ -n "$BARCODE" ] || { echo "FEHLER: Variante 'attribut' braucht einen Barcode-Wert." >&2; exit 1; }
    FIELDS="[$SECURITY]"
    EXTRA=",\"barcode\":\"$BARCODE\""
    ;;
  feld)
    [ -n "$BARCODE" ] || { echo "FEHLER: Variante 'feld' braucht einen Barcode-Wert." >&2; exit 1; }
    FIELDS="[$SECURITY,{\"id\":\"Barcode\",\"content\":{\"value\":\"$BARCODE\"}}]"
    EXTRA=""
    ;;
  ohne)
    FIELDS="[$SECURITY]"
    EXTRA=""
    ;;
  *)
    echo "FEHLER: unbekannte Variante '$VARIANTE' (erlaubt: attribut | feld | ohne)" >&2
    exit 1
    ;;
esac

BODY="{\"data\":{\"type\":\"inventoryLocation\",\"attributes\":{\"name\":\"$NAME\",\"typeId\":\"$TYPE\",\"ancestors\":$ANCJSON,\"fields\":$FIELDS$EXTRA}}}"

outdir=../api_jsons/barcodes
mkdir -p "$outdir"

# Slug: Leerzeichen/Sonderzeichen aus dem Namen raus, damit der Dateiname sauber bleibt
SLUG=$(echo "$NAME" | tr -c 'A-Za-z0-9._-' '_' | sed 's/_\+/_/g; s/^_//; s/_$//')

echo "$BODY" > "$outdir/${VARIANTE}_${SLUG}_request.json"

echo "--> POST $BASE/inventory/locations   (Variante: $VARIANTE, Barcode: '${BARCODE:-<keiner>}')"

curl -s -w "\nHTTP %{http_code}\n" -X POST \
  -H "X-API-Key: $KEY" \
  -H "content-type: application/vnd.api+json" \
  -H "accept: application/vnd.api+json" \
  -d "$BODY" \
  "$BASE/inventory/locations" | tee "$outdir/${VARIANTE}_${SLUG}_response.json"
