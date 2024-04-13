ALTER TABLE public.users
ADD COLUMN created_at TIMESTAMP;

ALTER TABLE public.users
ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE public.users
ADD COLUMN created_by VARCHAR(32);

ALTER TABLE public.users
ADD COLUMN modified_by VARCHAR(32);