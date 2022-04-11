--Creating uuid PostgreSQL extension.
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table if not exists public.pix_registers(
    register_id uuid DEFAULT NOT NULL uuid_generate_v4 () ,
    key_type varchar(9) NOT NULL,
    key_value varchar(77) NOT NULL,
    account_type varchar(10) NOT NULL,
    agency_number integer NOT NULL,
    account_number integer NOT NULL,
    user_first_name varchar(30) NOT NULL,
    user_last_name varchar(30),
    created_at DATE NOT NULL,
    deleted_at DATE,
)