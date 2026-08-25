#!/bin/bash

# Prueft, ob der Barcode einer BESTEHENDEN Location nachtraeglich geaendert werden kann.
#
# Aufruf: ./13_test_barcode_aendern.sh [location-uuid] [neuer-barcode]
#   ohne Argumente wird die Test-Location "Test Barcode Kontrolle 20260825-103558"
#   genommen (hat aktuell die Auto-Nummer 0000000208).
#
# Ablauf:
#   0) GET  -> IST-Zustand + 'digest' merken
#   1) PATCH ohne Zusatz
#   2) PATCH mit ?force=true      (falls 1 an fehlendem digest scheitert)
#   3) PATCH mit ?digest=<wert>   (optimistisches Sperren)
#   4) GET  -> hat sich der Barcode wirklich geaendert?
#
# ERGEBNIS (25.08.2026): NEIN. Der PATCH-Endpunkt erlaubt im Schema nur 'fields' und
# 'description'. 'barcode', 'name', 'status', 'isGrid' und 'ancestors' werden mit
# 400 "not allowed by the schema" abgewiesen - auch mit ?force=true.
# Ausserdem braucht JEDER PATCH ein ?force=true (oder ?digest=<wert> aus einem
# frischen GET), sonst kommt pauschal 400 "Missing required parameters".
# Siehe ./14_patch_schema_erkunden.sh
#
# Ergebnis: ../api_jsons/barcodes/patch_*.json

set -euo pipefail

BASE=$(grep -E '^base_url=' ../.env.properties | cut -d= -f2-)
KEY=$(grep -E '^api_key=' ../.env.properties | cut -d= -f2-)

ID="${1:-631552bd-4af6-4b69-82ff-43bc4eb0705d}"
NEU="${2:-SW-BC-PATCH-$(date +%Y%m%d-%H%M%S)}"

outdir=../api_jsons/barcodes
mkdir -p "$outdir"

hole() {   # hole <dateiname-suffix>  -> GET auf die Location
  curl -s -w "\nHTTP %{http_code}\n" \
    -H "X-API-Key: $KEY" -H "accept: application/vnd.api+json" \
    "$BASE/inventory/locations/$ID" > "$outdir/patch_$1.json"
}

lies() {   # lies <datei> <attributname> -> Wert aus der Antwort ziehen
  python3 -c "
import json,sys
zeilen = open(sys.argv[1], encoding='utf-8').read().strip().splitlines()
if zeilen and zeilen[-1].startswith('HTTP '): zeilen = zeilen[:-1]
try:
    d = json.loads('\n'.join(zeilen))
    print(d.get('data', {}).get('attributes', {}).get(sys.argv[2], ''))
except Exception:
    print('')
" "$1" "$2"
}

code() {   # code <datei> -> angehaengte HTTP-Zeile auslesen
  tail -n 2 "$1" | grep -o 'HTTP [0-9]*' | tail -n 1
}

echo "=== 0/4 IST-Zustand holen ==========================================="
hole "0_vorher"
ALT=$(lies "$outdir/patch_0_vorher.json" barcode)
DIGEST=$(lies "$outdir/patch_0_vorher.json" digest)
NAME=$(lies "$outdir/patch_0_vorher.json" name)
echo "Location : $NAME  ($ID)"
echo "Barcode  : '$ALT'   ->   Wunsch: '$NEU'"
echo "digest   : '$DIGEST'"

BODY_MIT_ID="{\"data\":{\"type\":\"inventoryLocation\",\"id\":\"$ID\",\"attributes\":{\"barcode\":\"$NEU\"}}}"
BODY_OHNE_ID="{\"data\":{\"type\":\"inventoryLocation\",\"attributes\":{\"barcode\":\"$NEU\"}}}"
BODY_NAME="{\"data\":{\"type\":\"inventoryLocation\",\"attributes\":{\"name\":\"$NAME (umbenannt)\"}}}"

echo "$BODY_OHNE_ID" > "$outdir/patch_request.json"

patche() {  # patche <dateiname-suffix> <body> <query-string oder leer>
  local suffix="$1" body="$2" query="${3:-}"
  echo "--> PATCH $BASE/inventory/locations/$ID$query"
  echo "    Body: $body"
  curl -s -w "\nHTTP %{http_code}\n" -X PATCH \
    -H "X-API-Key: $KEY" \
    -H "content-type: application/vnd.api+json" \
    -H "accept: application/vnd.api+json" \
    -d "$body" \
    "$BASE/inventory/locations/$ID$query" > "$outdir/patch_$suffix.json"
  echo "    $(code "$outdir/patch_$suffix.json")"
  python3 -c "
import json,sys
z = open(sys.argv[1], encoding='utf-8').read().strip().splitlines()
if z and z[-1].startswith('HTTP '): z = z[:-1]
try:
    d = json.loads('\n'.join(z))
except Exception:
    sys.exit()
if 'errors' in d:
    print('    Fehler:', d['errors'][0].get('detail', '?')[:300])
" "$outdir/patch_$suffix.json"
}

echo
echo "=== 1/6 PATCH mit id im Body (barcode) =============================="
patche "1_mit_id" "$BODY_MIT_ID"

echo
echo "=== 2/6 PATCH ohne id im Body (barcode) ============================="
patche "2_ohne_id" "$BODY_OHNE_ID"

echo
echo "=== 3/6 PATCH ohne id + ?force=true (barcode) ======================="
patche "3_force" "$BODY_OHNE_ID" "?force=true"

echo
echo "=== 4/6 PATCH ohne id + ?digest=<wert> (barcode) ===================="
if [ -n "$DIGEST" ]; then
  patche "4_digest" "$BODY_OHNE_ID" "?digest=$DIGEST"
else
  echo "    uebersprungen - kein digest in der GET-Antwort gefunden"
fi

echo
echo "=== 5/6 Kontroll-PATCH: nur den NAMEN aendern ======================="
echo "    (beweist, ob der Endpunkt ueberhaupt funktioniert)"
patche "5_kontrolle_name" "$BODY_NAME"

echo
echo "=== 6/6 Kontrolle: Zustand danach ==================================="
hole "4_nachher"
NACHHER=$(lies "$outdir/patch_4_nachher.json" barcode)

echo
echo "-------------------------------------------------------------------"
echo "vorher : '$ALT'"
echo "wunsch : '$NEU'"
echo "nachher: '$NACHHER'"
if [ "$NACHHER" = "$NEU" ]; then
  echo "ERGEBNIS: Barcode LAESST SICH AENDERN."
elif [ "$NACHHER" = "$ALT" ]; then
  echo "ERGEBNIS: Barcode UNVERAENDERT - Aenderung nicht moeglich (siehe Fehler oben)."
else
  echo "ERGEBNIS: unerwarteter Wert - Antworten in $outdir/ pruefen."
fi
echo "-------------------------------------------------------------------"
