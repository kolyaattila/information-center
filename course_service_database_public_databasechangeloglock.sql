create table databasechangeloglock
(
    id          integer not null
        constraint pk_databasechangeloglock
            primary key,
    locked      boolean not null,
    lockgranted timestamp,
    lockedby    varchar(255)
);

alter table databasechangeloglock
    owner to course_service_user;

INSERT INTO public.databasechangeloglock (id, locked, lockgranted, lockedby) VALUES (1, false, null, null);