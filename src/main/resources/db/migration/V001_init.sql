CREATE TABLE PERSON (
	id varchar(255) not null primary key,
	first_name varchar(255) not null,
	last_name varchar(255) not null
);

create sequence person_sequence start with 1 increment by 1;

insert into PERSON (first_name, last_name) values ('Vladimir', 'Putin');
insert into PERSON (first_name, last_name) values ('Barack', 'Obama');
insert into PERSON (first_name, last_name) values ('Justin', 'Bieber');
insert into PERSON (first_name, last_name) values ('Taylor', 'Swift');
insert into PERSON (first_name, last_name) values ('Lionel', 'Messi');
