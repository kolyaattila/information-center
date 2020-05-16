create table public.Answered_question (
    id                bigserial     constraint answered_question_pkey primary key,
    created           timestamp     default CURRENT_DATE,
    external_id       varchar(50)   not null unique,
    user_id           varchar(50)   not null,
    correct_answer    boolean       not null,
    solved_quiz_id    bigint        not null
);
