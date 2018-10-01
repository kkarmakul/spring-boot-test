CREATE ROLE site_msg_application WITH LOGIN PASSWORD 'site_msg_pwd';
DROP DATABASE IF EXISTS site_messages;
CREATE DATABASE site_messages;
GRANT ALL PRIVILEGES ON DATABASE site_messages TO site_msg_application;

\c site_messages;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" SCHEMA pg_catalog;
