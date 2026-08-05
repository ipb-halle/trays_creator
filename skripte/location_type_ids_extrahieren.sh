python3 - <<'PY'
import json
d = json.load(open('../api_jsons/inventory_types_location.json'))
result = {}

for index, node in enumerate(d['data'], start=1):
  eid=node['id']
  name=node['attributes']['name']
  result[name] = eid

print(json.dumps(result))
json.dump(result, open('../api_jsons/location_type_ids.json', "w"), ensure_ascii=False)
PY