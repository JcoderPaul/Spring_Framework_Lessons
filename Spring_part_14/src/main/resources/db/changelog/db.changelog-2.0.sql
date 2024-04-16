--liquibase formatted sql

--changeset oldboy:1
ALTER TABLE users
ADD COLUMN created_at TIMESTAMP;

ALTER TABLE users
ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE users
ADD COLUMN created_by VARCHAR(32);

ALTER TABLE users
ADD COLUMN modified_by VARCHAR(32);