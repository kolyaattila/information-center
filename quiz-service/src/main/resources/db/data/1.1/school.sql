create table public.School (
    id                bigserial      constraint school_pkey primary key,
    created           timestamp      default CURRENT_DATE,
    external_id       varchar(50)    not null unique,
    name              varchar(100)   not null unique,
    number_questions  smallint       not null
);
