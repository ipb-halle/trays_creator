python3 - <<'PY'
import json
d = json.load(open('../api_jsons/entities_locations.json'))
result = {}


for index, node in enumerate(d['data'], start=1):
  eid=node['id']
  name=node['attributes']['name']
  result[name] = eid

print(json.dumps(result))
json.dump(result, open('../api_jsons/locations_ids.json', "w"), ensure_ascii=False)
PY