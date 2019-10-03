-- drop table account;

create table account (
sno int NOT NULL AUTO_INCREMENT,
userid varchar(50) NOT NULL,
password varchar(20) NOT NULL,
PRIMARY KEY(sno)
);

insert into account (userid, password) values('admin', 'admin');

-- drop table records;

create table records (
id int NOT NULL AUTO_INCREMENT,
members int NOT NULL,
batch varchar(8) NOT NULL,
eventType varchar(10) NOT NULL,
j1 int DEFAULT '0',
j2 int DEFAULT '0',
j3 int DEFAULT '0',
PRIMARY KEY(id)
);

insert into records (members, batch, eventType, j1, j2, j3) values('5','THIRD', 'DRAMA', '10', '10', '10');