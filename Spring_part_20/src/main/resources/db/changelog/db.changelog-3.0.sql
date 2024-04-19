--liquibase formatted sql

--changeset oldboy:1
ALTER TABLE users
ADD COLUMN image VARCHAR(64);

--changeset oldboy:2
ALTER TABLE users_aud
ADD COLUMN image VARCHAR(64);