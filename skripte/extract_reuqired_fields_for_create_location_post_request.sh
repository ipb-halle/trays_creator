python3 - << 'PY'
#Ziel: pro Typ-Dump die Felder rausziehen, die required && !readOnly sind, mit id + key + title.

import json, glob

for index, path in enumerate(glob.glob('../api_jsons/types/*.json'), start=1):
  t =json.load(open(path))
  #print(f"{t}:{index}")
  eid=t['id']
  typ=t['attributes']['name']
  movable = t['attributes']['isMovable']
  fields = t['attributes']['fields']
  print(f"\neid = {eid}, " + f"typ = {typ}, " + f"movable = {movable} \n")
  for index, field in enumerate(fields, start = 1):
    definition = field['definition']
    is_required= definition.get('isRequired')
    read_only= definition.get('readOnly')
    if( is_required):
      field_id = field.get('id')
      field_key = definition.get('key')
      field_title = definition.get('title')
      print(f"field id = {field_id},  field key =  {field_key},  field title =  {field_title}, is_required = {is_required}, read_only= {read_only}")
PY