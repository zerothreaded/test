
create table constituency
(
   id INTEGER,
   name VARCHAR(48)   
);

create table person
(
   id 			INTEGER,
   member_id 	INTEGER,
   person_id	INTEGER,
   name 		VARCHAR(32),
   party		VARCHAR(48),
   constituency	VARCHAR(48),
   CONSTRAINT PK_ID UNIQUE (id)   
);

