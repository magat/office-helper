# What is Office Helper ?
 
...  

# Installation

In order to launch the application, you need to :

- Add db.properties in 'data/src/main/resources/db.properties'
```
db.username=User
db.pswd=pwd
db.url=jdbc:postgresql://localhost:5432/<YOUR_DB_NAME>
db.driver=org.postgresql.Driver
```

- Add db.test.properties in 'data/src/test/resources/db.test.properties' with the same structure :
```
db.test.username=User
db.test.pswd=pwd
db.test.url=jdbc:postgresql://localhost:5432/<YOUR_TEST_DB_NAME>
db.test.driver=org.postgresql.Driver
```

- The DB should match the following structure :
```
CREATE TABLE author (
  id BIGSERIAL NOT NULL,
  first_name character varying(255) NOT NULL,
  last_name character varying(255), /*NOT NULL TODO : For future implementations*/
  email character varying(255) /*NOT NULL TODO : For future implementations*/
);

ALTER TABLE author ADD CONSTRAINT author_id_pkey PRIMARY KEY(id);

CREATE TABLE request (
  id BIGSERIAL NOT NULL,
  date_created timestamp WITH time zone NOT NULL,
  title character varying(255) NOT NULL,
  url character varying(255),
  status character varying(10) NOT NULL,
  comments text,
  date_ordered timestamp WITH time zone,
  date_received timestamp WITH time zone,
  author BIGINT NOT NULL references author(id)
);

ALTER TABLE request ADD CONSTRAINT request_id_pkey PRIMARY KEY(id);
```