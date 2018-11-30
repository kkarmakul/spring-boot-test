INSERT INTO registry.client
  (uuid, login, email, family_name, first_name, deleted)
VALUES
  ('36dc24c7-7cd1-4c73-b6cd-ab460b4c7636', 'login_get', 'mail-get@somehost.xz', 'Client', 'forGet', false),
  ('a947b26b-0793-4971-a8b2-5277d360cf05', 'login_mod', 'mail-mod@somehost.xz', 'Client', 'forChange', false),
  ('8848c59b-a40d-49be-ae4a-01e9762f5dcf', 'login_del', 'mail-del@somehost.xz', 'Client', 'forDeletion', false);

INSERT INTO registry.client
(login, email, family_name, first_name, deleted)
VALUES
  ('login1', 'mail@somehost1.xz', 'Client1', 'forSearch1', false),
  ('login11', 'mail@somehost11.xz', 'Client11', 'forSearch11', false),
  ('login111', 'mail@somehost111.xz', 'Client111', 'forSearch111', true),
  ('login3', 'mail@somehost2.xz', 'Client3', 'forSearch3', true);
