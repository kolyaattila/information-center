create table public.video (
    id                bigserial constraint video_pkey primary key,
    external_id       varchar(50) not null unique,
    title             varchar(255) not null,
    created           timestamp default CURRENT_DATE,
    path              varchar(255) not null,
    description       varchar(500) not null,
    topic_id          varchar(50) not null,
    course_id         varchar(50) not null,
    video_duration    varchar(10) not null
);
