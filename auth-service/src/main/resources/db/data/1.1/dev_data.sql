INSERT INTO public."user" (id, created, password, username, account_expired, account_locked, credentials_expired, enabled) VALUES (1, '2021-04-08 10:29:48.000000', '$2a$10$8D.PrE335lgsu8VC7WFjEOPNTk53ul9D3rqshagQbw26ED.FtefbK', 'attila.kolya@fortech.ro', false, false, false, true);
INSERT INTO public."user" (id, created, password, username, account_expired, account_locked, credentials_expired, enabled) VALUES (2, '2021-04-08 10:29:49.000000', '$2a$10$8D.PrE335lgsu8VC7WFjEOPNTk53ul9D3rqshagQbw26ED.FtefbK', 'admin@admin.com', false, false, false, true);

ALTER SEQUENCE S_USER_0 RESTART WITH 3;
