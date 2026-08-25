#!/bin/bash

# Startet die drei Barcode-Versuche nacheinander gegen die Trial-Instanz.
# ACHTUNG: legt drei ECHTE Test-Locations an. Danach in der Signals-Oberflaeche aufraeumen.
#
# Reihenfolge und Zweck:
#   1) ohne     -> Kontrolllauf: welche Auto-Nummer vergibt der Server gerade?
#   2) attribut -> Barcode als Top-Level-Attribut mitgeschickt
#   3) feld     -> Barcode als Feld-Eintrag mitgeschickt
#
# Danach auswerten mit: ./12_barcode_ergebnisse_auswerten.sh

set -euo pipefail

# Typ Room, Ancestor = Test_Building_Sergei (reine uuid, ohne 'location:' und ':ivt')
TYPE_ROOM="b9fab5b8-6c26-47f8-8694-320c7c439879"
ANCESTOR="f438fa95-e58a-4c09-843d-dbebfd084fcb"

# Zeitstempel haelt die Namen eindeutig - Signals verlangt global eindeutige Namen
# fuer nicht bewegliche Orte, ein zweiter Lauf wuerde sonst am Namen scheitern.
STAMP=$(date +%Y%m%d-%H%M%S)

# Buchstaben-Praefix, damit der Wunsch-Barcode NIE mit der fortlaufenden
# Auto-Nummerierung (10-stellig, rein numerisch) kollidieren kann.
BARCODE_ATTRIBUT="SW-BC-ATTR-$STAMP"
BARCODE_FELD="SW-BC-FELD-$STAMP"

echo "=== 1/3 Kontrolllauf ohne Barcode ==================================="
./barcode_test_post.sh ohne     "Test Barcode Kontrolle $STAMP" "$TYPE_ROOM" "$ANCESTOR"

echo
echo "=== 2/3 Barcode als Top-Level-Attribut =============================="
./barcode_test_post.sh attribut "Test Barcode Attribut $STAMP"  "$TYPE_ROOM" "$ANCESTOR" "$BARCODE_ATTRIBUT"

echo
echo "=== 3/3 Barcode als Feld-Eintrag ==================================="
./barcode_test_post.sh feld     "Test Barcode Feld $STAMP"      "$TYPE_ROOM" "$ANCESTOR" "$BARCODE_FELD"

echo
echo "Fertig. Antworten liegen in ../api_jsons/barcodes/"
echo "Auswertung starten mit: ./12_barcode_ergebnisse_auswerten.sh"
