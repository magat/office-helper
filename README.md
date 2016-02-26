# Office Helper

## What is Office Helper ?
 
Office helper is a tool which aims to assist people ordering products.
 
## Requirements

- Java 1.8
- PostgreSQL 9.4
- Apache Tomcat 8.0.30 
- Apache Maven 3.3.9

## Installation

### Create database

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
  quantity INT,
  url character varying(255),
  status character varying(10) NOT NULL,
  comments text,
  date_ordered timestamp WITH time zone,
  date_received timestamp WITH time zone,
  date_deadline timestamp WITH time zone,
  author BIGINT NOT NULL references author(id)
);

ALTER TABLE request ADD CONSTRAINT request_id_pkey PRIMARY KEY(id);
```

### Configure database access

- Edit `db.sample.properties` in *data/src/main/resources/*
- Rename `db.sample.properties` to `db.properties` 
- Edit `db.test.sample.properties` in *data/src/test/resources/*
- Rename `db.test.sample.properties` to `db.test.properties`