create table public.answer2answered_question (
    answer_id               bigint not null,
    answered_question_id    bigint not null,
    constraint answer2answered_question_pk PRIMARY KEY (answer_id, answered_question_id)
);


