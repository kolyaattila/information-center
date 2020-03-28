create table public.video (
    id                bigserial constraint video_pkey primary key,
    uid               varchar(255) not null unique,
    title             varchar(255) not null,
    created           timestamp default CURRENT_DATE,
    path              varchar(255) not null,
    description       varchar(255) not null,
    topic_id          varchar(255) not null,
    chapter_id        varchar(255) not null,
);
