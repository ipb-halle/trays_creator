python3 - <<'PY'
import json, re, pathlib
data = json.load(open('../api_jsons/inventory_types_location.json'))['data']
for t in data:
    name = t['attributes'].get('name','unknown')
    slug = re.sub(r'[^A-Za-z0-9]+','_', name).strip('_') or 'unknown'
    pathlib.Path(f'../api_jsons/types/{slug}.json').write_text(json.dumps(t, indent=2, ensure_ascii=False))
print('geschrieben:', len(data))
PY