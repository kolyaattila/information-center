create table public.Subscription
(
	id bigserial not null constraint subscription_pkey primary key,
	created timestamp not null default CURRENT_TIMESTAMP,
	email varchar(255) not null constraint uk_subscription_email unique,
	first_name varchar(255) not null,
	last_name varchar (255) not null,
	unsubscription boolean not null default false
);