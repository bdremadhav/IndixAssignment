# Indix Key Value Store

Configure list of ports in src/main/resources/config.properties and deploy jar file in configured (Tomcat) servers.

Put data to map of one server(eg: 4455). It is replicated to all other configured servers(eg: 4466)
$ curl -H "Content-type: application/json" -XPOST http://localhost:4455/set/key -d 'VALUE'
OK

Get data from any servers(eg: 4466)
$ curl -H "Accept: application/json" http://localhost:4466/get/key
VALUE