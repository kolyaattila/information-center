create table public.Course
(
    id                bigserial     primary key,
    name              varchar(255)  not null,
    created           timestamp     default CURRENT_TIMESTAMP,
    external_id       varchar(50)   not null unique,
    author            varchar(50)   not null,
    description       varchar(2000) not null,
    enable            boolean       default 'false',
    price             NUMERIC(2)    not null,
    image             varchar(200),
    number_videos     NUMERIC        not null,
    number_quizzes    NUMERIC        not null
);
