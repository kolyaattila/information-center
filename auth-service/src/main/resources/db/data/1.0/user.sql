create table public.user
(
	id bigserial not null constraint user_pkey primary key,
	created timestamp DEFAULT CURRENT_DATE,
	password varchar(255),
	username varchar(255) constraint uk_user_username unique,
	account_expired boolean not null,
	account_locked boolean not null,
	credentials_expired boolean not null,
	enabled boolean not null
);
