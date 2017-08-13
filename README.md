# Indix Key Value Store

Configure list of ports in src/main/resources/config.properties and deploy war file in configured (Tomcat) servers.

Put data to map of one server(port eg: 4455). It is replicated to all other configured servers(port eg: 4466)

$ curl -H "Content-type: application/json" -XPOST http://localhost:4455/set/key -d 'VALUE'

OK


Get data from any servers(port eg: 4466)

$ curl -H "Accept: application/json" http://localhost:4466/get/key

VALUE