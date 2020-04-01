create table public.role
(
    id          bigserial constraint role_pkey primary key,
    description varchar(255),
    name        varchar(255) not null constraint uk_role_name unique,
    created     timestamp DEFAULT CURRENT_DATE
);
