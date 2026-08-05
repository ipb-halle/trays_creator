python3 - <<'PY'
import json
d = json.load(open('../api_jsons/entities_locations.json'))
result = {}


for index, node in enumerate(d['data'], start=1):
  eid=node['id']
  result[f"eid{index}"] = eid

print(json.dumps(result))
json.dump(result, open('../api_jsons/locations_id.json', "w"), ensure_ascii=False)
PY