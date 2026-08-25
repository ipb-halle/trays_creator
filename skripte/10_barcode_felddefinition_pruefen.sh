#!/bin/bash

# Liest NUR lokal aus dem Typ-Katalog, kein API-Call.
# Ziel: pro Location-Typ zeigen, wie das Barcode-Feld definiert ist
#       (readOnly? calculated? isRequired?) -> sagt, ob Schreiben laut Katalog erlaubt waere.
# Ergebnis: ../api_jsons/barcodes/barcode_felddefinitionen.json

set -euo pipefail

mkdir -p ../api_jsons/barcodes

python3 - <<'PY'
import json, pathlib

katalog = json.load(open('../api_jsons/inventory_types_location.json'))['data']
ergebnis = {}

for typ in katalog:
    attrs = typ['attributes']
    name = attrs.get('name', 'unbekannt')
    for feld in attrs.get('fields', []):
        d = feld.get('definition', {})
        if d.get('key') in ('Barcode', 'PE_INV_SYSTEM_Barcode'):
            ergebnis[name] = {
                'typeId':     typ.get('id'),
                'key':        d.get('key'),
                'type':       d.get('type'),
                'isRequired': d.get('isRequired'),
                'readOnly':   d.get('readOnly'),      # None = Schluessel fehlt = nicht readOnly
                'calculated': d.get('calculated'),
                'hidden':     d.get('hidden'),
            }
            break

pfad = pathlib.Path('../api_jsons/barcodes/barcode_felddefinitionen.json')
pfad.write_text(json.dumps(ergebnis, indent=2, ensure_ascii=False))

print(f"{len(ergebnis)} Typen mit Barcode-Feld gefunden -> {pfad}\n")
schreibbar = [n for n, v in ergebnis.items() if not v['readOnly'] and not v['calculated']]
gesperrt   = [n for n, v in ergebnis.items() if v['readOnly'] or v['calculated']]
print(f"laut Katalog beschreibbar ({len(schreibbar)}): {', '.join(sorted(schreibbar)) or '-'}")
print(f"laut Katalog gesperrt    ({len(gesperrt)}): {', '.join(sorted(gesperrt)) or '-'}")
print("\nACHTUNG: Der Katalog kennt nur Felder, nicht die Auto-Nummerierung der Instanz.")
print("Ob ein selbst gesetzter Barcode wirklich uebernommen wird, zeigt erst ein echter POST")
print("-> ./11_start_barcode_tests.sh")
PY
