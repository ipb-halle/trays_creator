#!/bin/bash

# Wertet die Antworten aus ../api_jsons/barcodes/ aus und beantwortet die Frage:
# Wird ein selbst gesetzter Barcode uebernommen, stillschweigend ueberschrieben
# oder abgelehnt?
# Ergebnis: ../api_jsons/barcodes/barcode_auswertung.md  (+ Ausgabe im Terminal)

set -euo pipefail

python3 - <<'PY'
import json, glob, os, pathlib, datetime

ordner = '../api_jsons/barcodes'

def lade(pfad):
    """Antwortdatei lesen: JSON-Koerper + angehaengte Zeile 'HTTP <code>'."""
    text = open(pfad, encoding='utf-8').read().strip()
    code = None
    zeilen = text.splitlines()
    if zeilen and zeilen[-1].startswith('HTTP '):
        code = zeilen[-1].split()[1]
        text = '\n'.join(zeilen[:-1])
    try:
        return json.loads(text), code
    except json.JSONDecodeError:
        return None, code

zeilen_md = []
def sag(text=''):
    print(text)
    zeilen_md.append(text)

sag('# Barcode beim Anlegen einer Location — Auswertung')
sag()
sag(f'Erzeugt: {datetime.date.today().isoformat()}')
sag()

antworten = sorted(glob.glob(os.path.join(ordner, '*_response.json')))
if not antworten:
    sag('Keine Antwortdateien gefunden. Erst ./11_start_barcode_tests.sh laufen lassen.')
else:
    sag('| Variante | HTTP | gesendeter Barcode | zurueckgegebener Barcode | Ergebnis |')
    sag('| --- | --- | --- | --- | --- |')

for pfad in antworten:
    basis    = os.path.basename(pfad).replace('_response.json', '')
    variante = basis.split('_', 1)[0]
    req_pfad = os.path.join(ordner, basis + '_request.json')

    gesendet = '-'
    if os.path.exists(req_pfad):
        req = json.load(open(req_pfad, encoding='utf-8'))
        attrs = req['data']['attributes']
        if 'barcode' in attrs:
            gesendet = attrs['barcode']
        for f in attrs.get('fields', []):
            if f.get('id') == 'Barcode':
                gesendet = f['content']['value']

    daten, code = lade(pfad)

    if daten is None:
        sag(f'| {variante} | {code or "?"} | `{gesendet}` | - | Antwort nicht lesbar |')
        continue

    if 'errors' in daten:
        fehler = daten['errors'][0]
        detail = fehler.get('detail') or fehler.get('title') or '?'
        sag(f'| {variante} | {code or fehler.get("status","?")} | `{gesendet}` | - | ABGELEHNT: {detail} |')
        continue

    zurueck = daten.get('data', {}).get('attributes', {}).get('barcode', '-')

    if variante == 'ohne':
        urteil = f'Auto-Nummer vergeben ({zurueck})'
    elif zurueck == gesendet:
        urteil = 'UEBERNOMMEN — eigener Barcode ist moeglich'
    else:
        urteil = 'UEBERSCHRIEBEN — Server ignoriert den Wunschwert'

    sag(f'| {variante} | {code or "?"} | `{gesendet}` | `{zurueck}` | {urteil} |')

sag()
sag('## Lesehilfe')
sag()
sag('- **ohne** ist der Kontrolllauf: er zeigt, bei welcher Nummer die Auto-Zaehlung gerade steht.')
sag('- **attribut** schickt den Barcode als Top-Level-Attribut in `data.attributes.barcode`.')
sag('- **feld** schickt ihn als Eintrag in `data.attributes.fields` mit `"id":"Barcode"`.')
sag('- Kommt bei **beiden** Varianten der Wunschwert zurueck, kann der Import eigene Barcodes setzen.')
sag('- Wird ueberschrieben oder abgelehnt, laeuft die Instanz auf Auto-Nummerierung und wir')
sag('  duerfen den Barcode nicht senden (bisherige Annahme aus Session 0).')

pfad_md = pathlib.Path(ordner) / 'barcode_auswertung.md'
pfad_md.write_text('\n'.join(zeilen_md) + '\n', encoding='utf-8')
print()
print(f'geschrieben: {pfad_md}')
PY
