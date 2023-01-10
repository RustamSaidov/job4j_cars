auto_post(id, description, created, auto_user_id)

CREATE TABLE auto_user_posts
(
    id       SERIAL PRIMARY KEY,
    description    varchar  not null,
    created date,
    auto_user_id int references auto_users(id)
);

