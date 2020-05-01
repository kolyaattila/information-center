create table public.Question (
    id                bigserial constraint question_pkey primary key,
    name              varchar(1000) not null,
    created           timestamp default CURRENT_DATE,
    external_id       varchar(50) not null unique,
    topic_id          varchar(30),
    difficulty        varchar(10) not null default 'EASY',
    chapter           varchar(100),
    verified          boolean not null default 'false',
    book              varchar(100),
    question_number   int,
    CONSTRAINT question_check_difficulty CHECK (difficulty in ('EASY','HARD','MEDIUM'))
);
