create table public.Question (
    id                bigserial constraint question_pkey primary key,
    name              varchar(255) not null,
    created           timestamp,
    external_id       varchar(255) not null unique,
    topic_id          varchar(255) not null,
    difficulty        varchar(10) not null,
    CONSTRAINT question_check_difficulty CHECK (difficulty in ('EASY','HARD','MEDIUM'))
);
