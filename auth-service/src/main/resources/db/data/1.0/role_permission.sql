create table public.role_permission
(
	role_id bigint not null constraint fk_role_permission_role_id references role,
	permission_id bigint not null constraint fk_role_permission_permission_id references permission,
	constraint role_permission_pk PRIMARY KEY (permission_id, role_id)
);
