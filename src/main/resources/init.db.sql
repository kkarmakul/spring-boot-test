CREATE ROLE client_test_application WITH LOGIN PASSWORD 'client_test_pwd';
DROP DATABASE IF EXISTS client_test;
CREATE DATABASE client_test;
GRANT ALL PRIVILEGES ON DATABASE client_test TO client_test_application;

\c client_test;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" SCHEMA pg_catalog;
CREATE EXTENSION IF NOT EXISTS "pgcrypto" SCHEMA pg_catalog;
