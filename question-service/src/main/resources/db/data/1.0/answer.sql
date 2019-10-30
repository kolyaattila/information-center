create table Answer(
    id                bigserial constraint answer_pkey primary key,
    name              varchar(255) not null,
    created           timestamp,
    external_id       varchar(255) not null unique,
    question_id       bigint not null constraint fk_answer_question_id references Question,
    is_correct        boolean not null default 'false'
);