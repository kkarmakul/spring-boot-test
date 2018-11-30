CREATE TABLE IF NOT EXISTS registry.client
(
  uuid          UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
  deleted       BOOLEAN NOT NULL DEFAULT false,
  email         VARCHAR(63),
  family_name   VARCHAR(31),
  first_name    VARCHAR(31),
  login         VARCHAR(31) NOT NULL,
  password_hash VARCHAR(255)
);
