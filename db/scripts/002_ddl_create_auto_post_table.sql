create TABLE auto_post
(
    id SERIAL PRIMARY KEY,
    description varchar  not null,
    created date,
    auto_user_id int references auto_user(id)
);

--comment on table auto_post is 'Таблица объявлений автомобилей';
--comment on table auto_post.id is 'Идентификатор объявлений автомобилей';
--comment on table auto_post.description is 'описание объявления автомобиля';
--comment on table auto_post.created is 'дата создания объявления автомобиля';
--comment on table auto_post.auto_user_id is 'идентификатор пользователя автомобиля';

