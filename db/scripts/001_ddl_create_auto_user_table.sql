CREATE TABLE auto_user
(
    id       SERIAL PRIMARY KEY,
    login    varchar unique not null,
    password varchar        not null
);

comment on table auto_user is '������� �������������';
comment on table auto_post.id is '������������� ������������';
comment on table auto_post.login is '����� ������������';
comment on table auto_post.password is '������ ������������';