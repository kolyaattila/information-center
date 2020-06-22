create table public.Topic
(
    id                bigserial constraint topic_pkey primary key,
    name              varchar(255) not null,
    created           timestamp default CURRENT_TIMESTAMP,
    external_id       varchar(255) not null unique
  );
