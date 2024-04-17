--liquibase formatted sql

--changeset oldboy:1
ALTER TABLE company_locales
ALTER COLUMN company_id TYPE INT;