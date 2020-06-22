create table Review(
    id                bigserial     primary key,
    rating            integer       not null,
    external_id       varchar(50)   not null unique,
    message           varchar(500),
    course_id         bigint        not null CONSTRAINT fk_review_course_id references Course,
    account_id        varchar(50),
    created           timestamp     default CURRENT_TIMESTAMP,
    CONSTRAINT review_check_rating CHECK (rating in (0, 1, 2, 3, 4 ,5))
);
