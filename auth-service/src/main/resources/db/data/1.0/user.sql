create table public.User
(
	id bigserial not null constraint user_pkey primary key,
	created timestamp,
	password varchar(255),
	username varchar(255) constraint uk_permission_name unique
);