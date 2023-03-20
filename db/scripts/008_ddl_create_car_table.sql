create table car(
    id serial primary key,
    name varchar not null,
    engine_id int not null unique references engine(id),
    driver_id int not null unique references driver(id)
);