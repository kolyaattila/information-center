create table public.Answered_question (
    id                bigserial     constraint answered_question_pkey primary key,
    created           timestamp     default CURRENT_DATE,
    external_id       varchar(50)   not null unique,
    solved_quiz_id    bigint        not null,
    question_id       bigint        not null
);

ALTER TABLE Answered_question
ADD CONSTRAINT Answered_question_question_id_fk FOREIGN KEY (question_id)
REFERENCES Question(id);
