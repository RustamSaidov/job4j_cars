CREATE TABLE auto_post
(
    id SERIAL PRIMARY KEY,
    description varchar  not null,
    created date,
    auto_user_id int references auto_users(id)
);

comment on table auto_post is '������� ���������� �����������';
comment on table auto_post.id is '������������� ���������� �����������';
comment on table auto_post.description is '�������� ���������� ����������';
comment on table auto_post.created is '���� �������� ���������� ����������';
comment on table auto_post.auto_user_id is '������������� ������������ ����������';

