create table public.Role
(
    id          bigserial constraint role_pkey primary key,
    description varchar(255),
    name        varchar(255) not null constraint uk_role_name unique,
    priority    integer not null
);
