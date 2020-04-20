create table public.Message
(
	id bigserial not null constraint message_pkey primary key,
	created timestamp not null default CURRENT_TIMESTAMP,
	email varchar(100) not null,
	name varchar(50) not null,
	message varchar(1000) not null,
	uid varchar (100) not null constraint uk_message_uid unique
);
