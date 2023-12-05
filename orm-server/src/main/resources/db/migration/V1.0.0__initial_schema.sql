create table if not exists Book (
   id  bigserial not null,
    title varchar(255) not null,
    primary key (id)
);

create table if not exists Person (
   id  bigserial not null,
    age int4 not null,
    name varchar(255) not null,
    primary key (id)
);

create table if not exists person_book (
   id  bigserial not null,
    book_id int8 not null,
    person_id int8 not null,
    primary key (id)
);

alter table if exists person_book
   add constraint UniquePersonIdBookId unique (person_id, book_id);
