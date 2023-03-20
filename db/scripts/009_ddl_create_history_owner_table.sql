create table history_owner(
    id serial primary key,
    car_id int not null references car(id),
    driver_id int not null references driver(id),
    start_at date,
    end_at date
);