create table public.video (
    id                bigserial constraint video_pkey primary key,
    external_id       varchar(50) not null unique,
    title             varchar(255) not null,
    created           timestamp default CURRENT_DATE,
    path              varchar(255) not null,
    description       varchar(500) not null,
    topic_id          bigint not null CONSTRAINT fk_video_topic_id references topic,
    video_duration    varchar(10) not null
);

CREATE SEQUENCE S_VIDEO_0
    START WITH 1
    INCREMENT BY 1
    CACHE 1;

CREATE INDEX I_VIDEO_0 ON VIDEO (TOPIC_ID);
