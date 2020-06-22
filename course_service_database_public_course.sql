create table course
(
    id             bigserial     not null
        constraint course_pkey
            primary key,
    name           varchar(255)  not null,
    created        timestamp default CURRENT_TIMESTAMP,
    external_id    varchar(50)   not null
        constraint course_external_id_key
            unique,
    author         varchar(50)   not null,
    description    varchar(2000) not null,
    enable         boolean   default false,
    price          numeric(2)    not null,
    image          varchar(200),
    number_videos  numeric       not null,
    number_quizzes numeric       not null
);

alter table course
    owner to course_service_user;

INSERT INTO public.course (id, name, created, external_id, author, description, enable, price, image, number_videos, number_quizzes) VALUES (1, 'Time management', '2020-06-21 13:56:26.329000', '2a0f3f99-2c5d-46a4-8d32-5516175f1176', 'Kolya Attila', 'When someone brings up essential skills for independent living, time management might not be at the top of your list. It’s probably not one you would think of until your to do list has gotten out of control. But learning how to manage your time wisely is an essential life skill that most young adults don’t “leave the nest” doing well.

Knowing how to manage your time wisely is essential to climbing the ladder of success. The more successful you get, the less time you have. We hope this information will equip you in mentoring a young adult as they grow more independent and hopefully you’ll learn some useful tips too!', true, 30, 'time-management-course', 5, 4);