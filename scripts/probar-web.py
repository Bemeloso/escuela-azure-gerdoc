#!/usr/bin/env python3
"""Ejercita formularios reales, cookies, CSRF y persistencia compartida con REST."""
import json,sys,uuid,re,html,urllib.request,urllib.parse,urllib.error,http.cookiejar
web=(sys.argv[1] if len(sys.argv)>1 else 'http://127.0.0.1:18081/Web').rstrip('/')
api=(sys.argv[2] if len(sys.argv)>2 else 'http://127.0.0.1:9080/api').rstrip('/')
client=urllib.request.build_opener(urllib.request.HTTPCookieProcessor(http.cookiejar.CookieJar()))
checks=[];created=[]
def get(path):
    with client.open(web+'/'+path,timeout=20) as r:body=r.read().decode()
    checks.append({'pagina':path,'estado':200});return body
def rows(cat):
    with urllib.request.urlopen(api+'/'+cat,timeout=20) as r:return json.load(r)
def submit(model,action,data,expected_error=False):
    page=get(model+'Servlet?accion='+('edit&id='+str(data['id']) if action=='update' else 'new'))
    token=re.search(r'name="csrf" value="([^"]+)"',page).group(1)
    data=dict(data,csrf=html.unescape(token),accion=action)
    body=urllib.parse.urlencode(data).encode()
    with client.open(urllib.request.Request(web+'/'+model+'Servlet',data=body),timeout=20) as r:page=r.read().decode()
    expected='alert-danger' if expected_error else 'Operación realizada correctamente.'
    assert expected in page,f'{model} {action}: {page[:600]}'
    checks.append({'catalogo':model,'accion':action,'resultado':'rechazado por integridad' if expected_error else 'guardado'})
    return page
def basic(model,cat,data):
    page=submit(model,'create',data)
    record=next(r for r in rows(cat) if r.get('nombre')==data['nombre'])
    created.append((model,cat,str(record['id'])))
    return record
try:
    index=get('')
    for link in ['Alumnos.jsp','Materias.jsp','Profesores.jsp','AlumnoMateria.jsp','ProfesorMateria.jsp']:
        assert link in index;get(link)
    marker='WEB '+uuid.uuid4().hex[:8]
    a=basic('Alumno','alumnos',{'nombre':marker+' <b>','edad':20,'promedio':'8.5'})
    p=basic('Profesor','profesores',{'nombre':marker,'especialidad':'Programación'})
    m=basic('Materia','materias',{'nombre':marker,'creditos':8})
    m2=basic('Materia','materias',{'nombre':marker+' B','creditos':6})
    assert '&lt;b&gt;' in get('Alumnos.jsp'),'La vista debe escapar HTML almacenado'
    submit('AlumnoMateria','create',{'alumnoId':a['id'],'materiaId':m['id'],'calificacion':'8.5'})
    am=next(r for r in rows('alumno-materia') if r['alumnoId']==a['id'] and r['materiaId']==m['id'])
    created.append(('AlumnoMateria','alumno-materia',str(am['id'])))
    submit('ProfesorMateria','create',{'profesorId':p['id'],'materiaId':m['id']})
    old=f"{p['id']}-{m['id']}";created.append(('ProfesorMateria','profesor-materia',old))
    for model,cat,key in created[:3]:submit(model,'delete',{'id':key},True)
    edits=[('Alumno','alumnos',str(a['id']),{'nombre':marker+' cambiado','edad':21,'promedio':'9.25'}),('Profesor','profesores',str(p['id']),{'nombre':marker+' cambiado','especialidad':'Bases de datos'}),('Materia','materias',str(m['id']),{'nombre':marker+' cambiada','creditos':10}),('AlumnoMateria','alumno-materia',str(am['id']),{'alumnoId':a['id'],'materiaId':m2['id'],'calificacion':'9.75'}),('ProfesorMateria','profesor-materia',old,{'profesorId':p['id'],'materiaId':m2['id']})]
    for model,cat,key,data in edits:
        submit(model,'update',dict(data,id=key))
        nextkey=f"{p['id']}-{m2['id']}" if model=='ProfesorMateria' else key
        if nextkey!=key:created.remove((model,cat,key));created.append((model,cat,nextkey))
        with urllib.request.urlopen(api+'/'+cat+'/'+nextkey,timeout=20) as r:record=json.load(r)
        for field,value in data.items():
            assert str(record[field])==str(value) or (isinstance(record[field],(int,float)) and float(record[field])==float(value)),f'Persistencia: {model} {field}'
        checks.append({'catalogo':model,'accion':'consulta REST del cambio web','resultado':'persistido'})
    # Las modificaciones sin el token de la sesión se rechazan.
    try:
        client.open(urllib.request.Request(web+'/AlumnoServlet',data=b'accion=delete&id=1'),timeout=20)
        raise AssertionError('Falta protección CSRF')
    except urllib.error.HTTPError as ex:assert ex.code==403
    checks.append({'accion':'POST sin CSRF','estado':403})
    for model,cat,key in list(reversed(created)):
        submit(model,'delete',{'id':key});created.remove((model,cat,key))
        with urllib.request.urlopen(api+'/'+cat,timeout=20) as r:records=json.load(r)
        assert not any((str(x.get('id'))==key if 'id' in x else f"{x['profesorId']}-{x['materiaId']}"==key) for x in records)
    print(json.dumps({'resultado':'APROBADO','web':web,'api':api,'total':len(checks),'comprobaciones':checks},ensure_ascii=False,indent=2))
finally:
    for model,cat,key in reversed(created):
        try:submit(model,'delete',{'id':key})
        except Exception:pass
