#!/usr/bin/env python3
"""CRUD por HTTP. Crea datos identificados y elimina únicamente esos datos al terminar."""
import json,sys,uuid,urllib.request,urllib.error
base=(sys.argv[1] if len(sys.argv)>1 else 'http://127.0.0.1:9080/api').rstrip('/')
checks=[];created=[]
def req(method,path,data=None,status=200):
    body=None if data is None else json.dumps(data).encode()
    request=urllib.request.Request(base+'/'+path,data=body,method=method,headers={'Content-Type':'application/json'})
    try:
        with urllib.request.urlopen(request,timeout=20) as response: code=response.status;raw=response.read()
    except urllib.error.HTTPError as ex:code=ex.code;raw=ex.read()
    assert code==status,f'{method} {path}: {code}, esperado {status}: {raw.decode()}'
    checks.append({'metodo':method,'ruta':path,'estado':code})
    return json.loads(raw) if raw else None
def make(cat,data):
    row=req('POST',cat,data,201)
    key=str(row['id']) if 'id' in row else f"{row['profesorId']}-{row['materiaId']}"
    created.append((cat,key));return row,key
def remove(cat,key):
    req('DELETE',cat+'/'+key,status=204);created.remove((cat,key));req('GET',cat+'/'+key,status=404)
try:
    assert req('GET','health')['conexion'] is True
    marker='REST '+uuid.uuid4().hex[:8]
    a,ak=make('alumnos',{'nombre':marker,'edad':20,'promedio':8.5})
    p,pk=make('profesores',{'nombre':marker,'especialidad':'Programación'})
    m,mk=make('materias',{'nombre':marker,'creditos':8})
    m2,m2k=make('materias',{'nombre':marker+' B','creditos':6})
    am,amk=make('alumno-materia',{'alumnoId':a['id'],'materiaId':m['id'],'calificacion':8.5})
    pm,pmk=make('profesor-materia',{'profesorId':p['id'],'materiaId':m['id']})
    for cat,key in [('alumnos',ak),('materias',mk),('profesores',pk),('alumno-materia',amk),('profesor-materia',pmk)]:
        req('GET',cat);req('GET',cat+'/'+key)
        if cat in ['alumnos','materias','profesores']:req('DELETE',cat+'/'+key,status=409)
    req('POST','alumno-materia',{'alumnoId':a['id'],'materiaId':m['id'],'calificacion':9},409)
    req('POST','profesor-materia',{'profesorId':p['id'],'materiaId':m['id']},409)
    req('POST','alumno-materia',{'alumnoId':9223372036854775806,'materiaId':m['id'],'calificacion':9},409)
    changes=[('alumnos',ak,{'nombre':marker+' editado','edad':21,'promedio':9.25}),('materias',mk,{'nombre':marker+' editada','creditos':10}),('profesores',pk,{'nombre':marker+' editado','especialidad':'Bases de datos'}),('alumno-materia',amk,{'alumnoId':a['id'],'materiaId':m2['id'],'calificacion':9.75}),('profesor-materia',pmk,{'profesorId':p['id'],'materiaId':m2['id']})]
    for cat,key,body in changes:
        req('PUT',cat+'/'+key,body)
        nextkey=f"{p['id']}-{m2['id']}" if cat=='profesor-materia' else key
        if nextkey!=key:created.remove((cat,key));created.append((cat,nextkey))
        row=req('GET',cat+'/'+nextkey)
        assert all(row[k]==value for k,value in body.items()),f'No persiste el cambio de {cat}'
    for invalid in [{'nombre':'','edad':20,'promedio':9},{'nombre':'X','edad':20.5,'promedio':9},{'nombre':'X','edad':20,'promedio':11},{'nombre':'X','edad':20,'promedio':8.123},{'nombre':'X','edad':20,'promedio':8,'invalido':True}]:req('POST','alumnos',invalid,400)
    req('GET','no-existe',status=404);req('GET','alumnos/abc',status=400)
    for cat,key in list(reversed(created)):remove(cat,key)
    print(json.dumps({'resultado':'APROBADO','base_url':base,'total':len(checks),'comprobaciones':checks},ensure_ascii=False,indent=2))
finally:
    for cat,key in list(reversed(created)):
        try:req('DELETE',cat+'/'+key,status=204)
        except Exception:pass
