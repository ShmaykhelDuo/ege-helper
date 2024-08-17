create table users
(
    id            uuid primary key,
    username      varchar(64) not null unique,
    password_hash text        not null,
    is_admin      boolean     not null
);

insert into users (id, username, password_hash, is_admin)
values ('658b58dc-86b2-4abd-b66c-d52dadc9e697', 'admin',
        '$2a$12$kUGQYhyZoQO/tJeWT2NQlO5eq20KLoZ6bGqAB.e9gkWSOIyugHV9u', true);

create table images
(
    id    uuid primary key,
    name  text not null,
    type  text not null,
    image oid  not null
);

create table tags
(
    id   uuid primary key,
    name text not null
);

create table tasks
(
    id     uuid primary key,
    text   text not null,
    answer text
);

create table tasks_images
(
    task_id  uuid not null references tasks (id),
    image_id uuid not null references images (id),
    primary key (task_id, image_id)
);

create table tasks_tags
(
    task_id uuid not null references tasks (id),
    tag_id  uuid not null references tags (id),
    primary key (task_id, tag_id)
);

create table user_tasks
(
    task_id uuid primary key references tasks (id),
    user_id uuid references users (id)
);

create table fetched_tasks
(
    task_id        uuid primary key references tasks (id),
    source_name    varchar(16) not null,
    source_task_id text        not null,
    unique (source_name, source_task_id)
);