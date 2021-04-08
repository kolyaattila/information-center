INSERT INTO public.course (id, name, created, external_id, author, description, enable, price, image, number_videos, number_quizzes) VALUES (1, 'Time management', '2020-06-21 13:56:26.329000', '2a0f3f99-2c5d-46a4-8d32-5516175f1176', 'Kolya Attila', 'When someone brings up essential skills for independent living, time management might not be at the top of your list. It’s probably not one you would think of until your to do list has gotten out of control. But learning how to manage your time wisely is an essential life skill that most young adults don’t “leave the nest” doing well.

Knowing how to manage your time wisely is essential to climbing the ladder of success. The more successful you get, the less time you have. We hope this information will equip you in mentoring a young adult as they grow more independent and hopefully you’ll learn some useful tips too!', true, 30, 'time-management-course', 5, 4);
INSERT INTO public.course (id, name, created, external_id, author, description, enable, price, image, number_videos, number_quizzes) VALUES (2, 'Bazele marketingului', '2021-04-07 19:26:23.459000', 'be9b931d-9cc6-4957-9452-271272ec77d0', 'attila.kolya@fortech.ro', 'Bazele marketingului', true, 10, 'bazele-marketingului', 1, 2);
INSERT INTO public.course (id, name, created, external_id, author, description, enable, price, image, number_videos, number_quizzes) VALUES (3, 'Curs resurse umane', '2021-04-07 19:26:47.264000', 'b4690de0-e5b2-4ad7-ade4-a50f89cae413', 'attila.kolya@fortech.ro', 'Curs resurse umane', true, 12, 'resurse-umane', 3, 2);

INSERT INTO public.review (id, rating, external_id, message, course_id, account_id, created) VALUES (1, 5, '815da5b1-3f1c-4e7e-a379-2c054e7f6910', null, 1, 'f830e526-1e9f-4c0d-9b0d-4c53c59ff892', '2021-04-07 19:24:19.249000');
INSERT INTO public.review (id, rating, external_id, message, course_id, account_id, created) VALUES (2, 4, 'df75b4da-cd61-4a3b-b4f9-7de1c3c90a80', null, 2, 'f830e526-1e9f-4c0d-9b0d-4c53c59ff892', '2021-04-07 19:30:24.881000');
INSERT INTO public.review (id, rating, external_id, message, course_id, account_id, created) VALUES (3, 4, '3a03aec8-5c73-4973-98d4-e0585438c754', null, 3, 'f830e526-1e9f-4c0d-9b0d-4c53c59ff892', '2021-04-07 19:31:48.907000');

INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (1, '​What Is Time Management?', '2020-06-21 13:57:22.419000', 'd9ab3c1d-d080-4e2f-869c-0ccba4844bba', 1);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (2, 'Five Steps to Get on Top of Your Schedule', '2020-06-21 13:57:34.794000', '7648ddca-ff01-4548-b452-9c35bdf73fad', 1);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (3, '​​​Five Great Time Management Strategies', '2020-06-21 14:16:41.693000', 'bc498940-eef1-405d-9c2a-2ab0ede0c726', 1);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (4, 'Five Tools To Help You Be More Productive', '2020-06-21 14:16:54.810000', 'fd6f8e99-8af7-4002-b105-c8b64ce193a9', 1);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (5, 'Subiectul 1', '2021-04-07 19:30:49.097000', '2b7431c6-be8a-4653-a30c-ac6b3e33b17a', 2);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (6, 'Subiectul 2', '2021-04-07 19:30:56.264000', 'be27751d-585b-4ce4-9146-90eac53fb42f', 2);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (7, 'Subiectul 4', '2021-04-07 19:31:02.115000', '5c04a396-b7f2-4dbb-9428-ae7703aa806f', 2);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (8, 'Subiectul 1', '2021-04-07 19:31:58.602000', '0b6ded59-2053-452c-afa2-1e9d053af9ea', 3);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (9, 'Subiectul 2', '2021-04-07 19:32:03.971000', '4d02c738-0563-49b7-8cd0-777275c1360a', 3);
INSERT INTO public.topic (id, name, created, external_id, course_id) VALUES (10, 'Subiectul 4', '2021-04-07 19:32:10.629000', '29db3ef9-3372-459d-9c19-bf903ea0829a', 3);

ALTER SEQUENCE S_COURSE_0 RESTART WITH 4;
ALTER SEQUENCE S_REVIEW_0 RESTART WITH 4;
ALTER SEQUENCE S_TOPIC_0 RESTART WITH 11;
