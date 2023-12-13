create database bkacad_pos
use bkacad_pos

create table Books
(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(255),
    Author VARCHAR(255),
    YearOfRelease INT
)
insert into Books(title,author,yearofrelease) values ('The experience','Lizzy',2022)
insert into Books(title,author,yearofrelease) values ('Falling Innocently','pseudo_angel',2019)
insert into Books(title,author,yearofrelease) values ('Now and forever','Maryamaaa',2021)