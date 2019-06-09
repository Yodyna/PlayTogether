INSERT INTO user(id, username, password) values (1, "Marcin", "$2a$10$BtLXuVK8Pa5ChVavjvDUVuN/XFWlidjGmu1zxwN2p/Y86gYysY6Ke");
INSERT INTO user(id, username, password) values (2, "Dominik", "$2a$10$wcZijD7VZxBySfnKPumba.IW4EmJsEuMLMxDtmv8X8odfVxNXASiu");
INSERT INTO user(id, username, password) values (3, "Tomek", "$2a$10$MDeusfQQ5kHdtxEJly9Cj.NjsUAjpKblWHvqo0R358B1pxWN7ZG5K");
INSERT INTO user_role values (1, "admin permissions", "ROLE_ADMIN");
INSERT INTO user_role values (2, "user permission", "ROLE_USER");
INSERT INTO user_roles values (1,1);
INSERT INTO user_roles values (2,1);
INSERT INTO user_roles values (3,2);