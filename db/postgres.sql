CREATE USER pps WITH PASSWORD 'pps';
ALTER ROLE pps SET client_encoding TO 'utf8';

CREATE SCHEMA pps;

ALTER
SCHEMA pps OWNER TO pps;


create table pps."role"(
	id SERIAL PRIMARY key,
	name varchar(255)
);
ALTER TABLE pps.role OWNER TO pps;

insert into pps.role ("name") values ('ROLE_ADMIN');
insert into pps.role ("name") values ('ROLE_USER');

create table pps."user"(
	id SERIAL PRIMARY key,
	email varchar(255) UNIQUE,
	password varchar(255),
	role_id int,
	CONSTRAINT "FK_USER_ROLE" FOREIGN KEY (role_id) REFERENCES pps.role (id)
);
ALTER TABLE pps.user OWNER TO pps;

insert into pps.user ("email", "password", "role_id")
values ('admin@mail.com', '$2a$10$NGalZt0.E2WP/XWo7MwKMOR7tTXH2xF2mUTDwEc6d0mX/IbeT5EIe', 1);  'admin123'
insert into pps.user ("email", "password", "role_id")
values ('user@mail.com', '$2a$10$GClS5ECqdhnm8wmh/hlrLOSuvx0LmI39JBv4qCX10MfZnT4sPH3uO', 2);

create table pps."category"(
	id SERIAL PRIMARY key,
	name varchar(255)
);
ALTER TABLE pps.category OWNER TO pps;

insert into pps.category ("name") values ('Living thing');
insert into pps.category ("name") values ('Machine');
insert into pps.category ("name") values ('Nature');


create table pps."picture"(
	id SERIAL PRIMARY key,
	name varchar(255),
	description varchar(255),
	status varchar(255),
	path text,
	category_id int,
	CONSTRAINT "FK_PICTURE_CATEGORY" FOREIGN KEY (category_id) REFERENCES pps.category (id)
);
ALTER TABLE pps.picture OWNER TO pps;

insert into pps.picture ("name", "description", "status", "path", "category_id")
values ('nature.jpg', 'This is an image of nature', 'ACCEPTED', 'uploaded\313ad5ee-0958-43f6-8774-11bdebf828e2.jpg', 3);
insert into pps.picture ("name", "description", "status", "path", "category_id")
values ('machine.png', 'This is an image of machine', 'ACCEPTED', 'uploaded\ee4728d8-89f6-4c45-988f-2d9cba65f058.png', 2);
insert into pps.picture ("name", "description", "status", "path", "category_id")
values ('dog.jpg', 'This is a picture of dog', 'ACCEPTED', 'uploaded\5004bbbc-b7dc-48b7-ad14-1e6a9af5b9bd.jpg', 1);
