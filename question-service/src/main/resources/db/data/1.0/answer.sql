create table public.Answer (
    id                bigserial constraint answer_pkey primary key,
    name              varchar(500) not null,
    created           timestamp default CURRENT_DATE,
    external_id       varchar(50) not null unique,
    question_id       bigint not null constraint fk_answer_question_id references Question,
    correct           boolean not null default 'false',
    reason            varchar(500),
    key               varchar (1) not null,
    question_number   int,
    book              varchar(100),
    CONSTRAINT answer_check_key CHECK (key in ('A','B','C','D','E'))
);
