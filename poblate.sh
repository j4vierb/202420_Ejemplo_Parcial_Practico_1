curl -s -X POST "http://192.168.56.1:8080/api/especialidades/" -H "Content-Type: application/json" -d '{"nombre": "Neurociencias", "descripcion": "Esta especialidad es sobre neurociencia."}' > ./out1.txt
curl -s -X POST "http://192.168.56.1:8080/api/medicos/" -H "Content-Type: application/json" -d '{"nombre": "RMDoctor X", "apellido": "XYZ"}' > ./out1.txt
curl -s -X POST "http://192.168.56.1:8080/api/medicos/" -H "Content-Type: application/json" -d '{"nombre": "RMDoctor Y", "apellido": "RAMEN", "registro":["a", "b", "c"]}' > ./out1.txt
rm ./out1.txt

# asocciate
curl -s -X POST http://192.168.56.1:8080/api/medico/1/especialidades/1 > ./out2.txt
curl -s -X POST http://192.168.56.1:8080/api/medico/2/especialidades/1 > ./out2.txt
rm ./out2.txt

curl -s -X GET http://192.168.56.1:8080/api/medicos/ | jq
