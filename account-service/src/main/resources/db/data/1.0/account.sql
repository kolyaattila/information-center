create table public.Account
(
	id bigserial not null constraint account_pkey primary key,
	created timestamp,
	username varchar(255) constraint uk_account_username unique,
	first_name varchar(255),
	last_name varchar (255),
	birthday date,
	photo bytea,
	uid varchar(255)
);
