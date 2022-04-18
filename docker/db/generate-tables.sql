--Creating uuid PostgreSQL extension.
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE public.pix_registers (
	id uuid NOT NULL,
	account_number int4 NULL,
	account_type varchar(10) NULL,
	agency_number int4 NULL,
	created_at timestamp NULL,
	deleted_at timestamp NULL,
	"key_type" varchar(9) NULL,
	key_value varchar(77) NULL,
	user_first_name varchar(30) NULL,
	user_last_name varchar(45) NULL,
	CONSTRAINT pix_registers_pkey PRIMARY KEY (id)
);