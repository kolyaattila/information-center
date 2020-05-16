create table public.Question2Quiz (
    quiz_id         bigint not null,
    question_id     bigint not null,
    constraint question2quiz_quiz_id_question_id_pk PRIMARY KEY (quiz_id, question_id)
);


