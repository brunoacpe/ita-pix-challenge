#Proposta do projeto.

A ideia do projeto é criar uma API que vai ser capaz de cadastrar chaves pix para correntistas no itaú.


#Url do swagger
* http://localhost:8090/swagger-ui.html

#Variáveis de ambiente para rodar o projeto.

* SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/pix-database;<br />
* SPRING_DATASOURCE_USERNAME=bruno.pacheco;<br />
* SPRING_DATASOURCE_PASSWORD=123456;<br />
* SPRING_JPA_DDL_AUTO=update;<br />
* SPRING_DATASOURCE_DRIVERNAME=org.postgresql.Driver;<br />
* server.port=8090;<br />
* REGEX_EMAIL=(?:[a-z0-9!#$%&'*+/\=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/\=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])