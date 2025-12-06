create table if not exists readers(
    id serial primary key,
    fullname varchar(29) not null
);

create table if not exists books(
    id serial primary key,
    title varchar(99) not null,
    fullname varchar(29) not null,
    reader_id bigint default null,
    foreign key (reader_id) references readers(id)
);

create index if not exists reader_id_index on books(reader_id);

