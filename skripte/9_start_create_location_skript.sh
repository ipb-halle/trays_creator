#!/bin/bash

# Hier muss man location für location auskommentieren skript starten, der generierte eid für den nächsten eintrag als parent eid einfügen!!

#  | skript name      | name location (unique)   | location type id                     | ancestor eid             | security yes/no (no only tray)
# 1 ROOM -> unter Building -> DONE
#./create_location.sh 'Test Room Sergei' b9fab5b8-6c26-47f8-8694-320c7c439879 f438fa95-e58a-4c09-843d-dbebfd084fcb yes
# 2 FRIDGE -> unter Room -> DONE
#./create_location.sh 'Test Kühlschrank Sergei' 94ae366c-e37c-4754-8ce2-d5b5b31d4d4f 37935741-e8ec-4622-aa6a-814b22c8a1ac yes
# 3 FREEZER -> unter Room -> DONE
#./create_location.sh 'Test Tiefkühlschrank Sergei' e93bfb17-4d4a-42a5-a112-908876ee2e19 37935741-e8ec-4622-aa6a-814b22c8a1ac yes
# 4 SHELF -> unter FRIDGE -> DONE
#./create_location.sh 'Test Shelf Sergei' cb870ae2-41eb-456d-8374-357c58a9a7d4 6a55924b-f06d-49a4-b489-71e410e5b1f9 yes
# 5 DRAWER -> unter FREEZER -> DONE
#./create_location.sh 'Test Drawer Sergei' 13a9fcfe-101e-4ed8-83b2-69e61b69ec88 1951a130-9d71-42ed-a2d7-6673c91ae771 yes
# 6 TRAY -> unter SHELF -> DONE
#./create_location.sh 'Test Shelf TH-Tray Sergei' e4d8f923-d407-4bee-8121-857a86f9e59d 54c1299d-62e9-4520-8af7-6114b45faeb2 no 8 3
# 7 TRAY -> unter DRAWER -> DONE
#./create_location.sh 'Test Drawer TH-Tray Sergei' e4d8f923-d407-4bee-8121-857a86f9e59d 0c194cba-e10a-40c5-bd6c-68421e8429d4 no 8 3
# 8 TRAY -> unter FRIDGE -> DONE
#./create_location.sh 'Test Fridge TH-Tray Sergei' e4d8f923-d407-4bee-8121-857a86f9e59d 6a55924b-f06d-49a4-b489-71e410e5b1f9 no 8 3
# 9 TRAY -> unter FREEZER -> DONE
#./create_location.sh 'Test Freezer TH-Tray Sergei' e4d8f923-d407-4bee-8121-857a86f9e59d 1951a130-9d71-42ed-a2d7-6673c91ae771 no 8 3