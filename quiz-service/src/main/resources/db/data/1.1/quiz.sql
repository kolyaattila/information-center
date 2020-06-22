create table public.Quiz (
    id                bigserial     constraint quiz_pkey primary key,
    created           timestamp     default CURRENT_DATE,
    external_id       varchar(50)   not null unique,
    type              varchar(10)   not null,
    duration          integer,
    school_id         bigint        CONSTRAINT fk_quiz_school_id references School,
    course_id         varchar(50),
    chapter_id        varchar(50),
    enable            boolean       default 'false',
    CONSTRAINT quiz_check_type CHECK (type in ('EXAM','DAILY','COURSE','CHAPTER'))
);
