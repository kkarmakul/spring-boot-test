CREATE ROLE admin WITH LOGIN PASSWORD 'MsgAdmin';
DROP DATABASE IF EXISTS site_messages;
CREATE DATABASE site_messages;
GRANT ALL PRIVILEGES ON DATABASE site_messages TO admin;
