create table topic
(
    id          bigserial    not null
        constraint topic_pkey
            primary key,
    name        varchar(255) not null,
    created     timestamp default CURRENT_TIMESTAMP,
    external_id varchar(50)  not null
        constraint topic_external_id_key
            unique,
    course_id   bigint       not null
        constraint topic_course_id_fk
            references course
);

alter table topic
    owner to course_service_user;

INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (1, '​What Is Time Management?', '2020-06-21 13:57:22.419000', 'd9ab3c1d-d080-4e2f-869c-0ccba4844bba', 1);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (2, 'Five Steps to Get on Top of Your Schedule', '2020-06-21 13:57:34.794000', '7648ddca-ff01-4548-b452-9c35bdf73fad', 1);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (3, '​​​Five Great Time Management Strategies', '2020-06-21 14:16:41.693000', 'bc498940-eef1-405d-9c2a-2ab0ede0c726', 1);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (4, 'Five Tools To Help You Be More Productive', '2020-06-21 14:16:54.810000', 'fd6f8e99-8af7-4002-b105-c8b64ce193a9', 1);