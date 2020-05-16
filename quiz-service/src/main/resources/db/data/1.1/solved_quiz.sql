create table public.Solved_quiz (
    id                bigserial     constraint solved_quiz_pkey primary key,
    created           timestamp     default CURRENT_DATE,
    external_id       varchar(50)   not null unique,
    quiz_id           bigint        not null,
    passed            boolean,
    note              NUMERIC(2,2),
    completed_quiz    boolean       default false
);

ALTER TABLE Solved_quiz
ADD CONSTRAINT solved_quiz_quiz_id_fk FOREIGN KEY (quiz_id)
REFERENCES Quiz(id);
