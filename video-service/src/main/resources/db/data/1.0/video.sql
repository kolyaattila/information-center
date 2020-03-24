create table public.Video
(
    id          bigserial constraint video_pkey primary key,
    external_id varchar(255) not null unique,
    name        varchar(255) not null,
    path        varchar(255) not null,
    title       varchar(255) not null,
    description varchar(255) not null,
    chapter     varchar(255) not null,
    topic_id    bigint       not null
);