create table public.user_role
(
    user_id     bigint constraint fk_user_role_user_id references public.user,
    role_id     bigint constraint fk_user_role_role_id references role,
    constraint  user_role_pk PRIMARY KEY (user_id, role_id)
);
