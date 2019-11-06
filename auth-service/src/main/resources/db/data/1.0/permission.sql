create table "public"."Permission"
(
	id bigserial not null constraint permission_pkey primary key,
	created timestamp,
	description varchar(255),
	name varchar(255) constraint uk_permission_name unique
);
