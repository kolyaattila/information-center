create table review
(
    id          bigserial   not null
        constraint review_pkey
            primary key,
    rating      integer     not null
        constraint review_check_rating
            check (rating = ANY (ARRAY [0, 1, 2, 3, 4, 5])),
    external_id varchar(50) not null
        constraint review_external_id_key
            unique,
    message     varchar(500),
    course_id   bigint      not null
        constraint fk_review_course_id
            references course,
    account_id  varchar(50),
    created     timestamp default CURRENT_TIMESTAMP
);

alter table review
    owner to course_service_user;

