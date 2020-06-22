create table databasechangelog
(
    id            varchar(255) not null,
    author        varchar(255) not null,
    filename      varchar(255) not null,
    dateexecuted  timestamp    not null,
    orderexecuted integer      not null,
    exectype      varchar(10)  not null,
    md5sum        varchar(35),
    description   varchar(255),
    comments      varchar(255),
    tag           varchar(255),
    liquibase     varchar(20),
    contexts      varchar(255),
    labels        varchar(255),
    deployment_id varchar(10)
);

alter table databasechangelog
    owner to course_service_user;

INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('topic_db_1.0', 'attila', 'classpath:db/data/1.0/initial_schema.yaml', '2020-06-21 13:30:34.594111', 1, 'EXECUTED', '7:9c2cfa0fd18f3ee8dbfc6448583d9878', 'sqlFile', '', null, '3.5.4', null, null, '2735434572');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('course_db_1.1', 'attila', 'classpath:db/data/1.1/add_course.yaml', '2020-06-21 13:30:34.612851', 2, 'EXECUTED', '7:3519adaabaad8cf923df6b014a3351b1', 'sqlFile', '', null, '3.5.4', null, null, '2735434572');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('update_topic_db_1.1', 'attila', 'classpath:db/data/1.1/add_course.yaml', '2020-06-21 13:30:34.627802', 3, 'EXECUTED', '7:1b5eaff87a38994c91e44a6e3841bc28', 'sqlFile', '', null, '3.5.4', null, null, '2735434572');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('review_db_1.1', 'attila', 'classpath:db/data/1.1/add_course.yaml', '2020-06-21 13:30:34.645553', 4, 'EXECUTED', '7:fb2281ac28714956e107002c53ab70d2', 'sqlFile', '', null, '3.5.4', null, null, '2735434572');