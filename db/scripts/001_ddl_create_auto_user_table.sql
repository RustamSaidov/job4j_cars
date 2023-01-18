CREATE TABLE auto_user
(
    id       SERIAL PRIMARY KEY,
    login    varchar unique not null,
    password varchar        not null
);


--comment on table auto_user is 'Таблица автолюбителей';
--comment on table auto_user.id is 'Идентификатор автолюбителя';
--comment on table auto_user.login is 'Логин автолюбителя';
--comment on table auto_user.password is 'Пароль автолюбителя';