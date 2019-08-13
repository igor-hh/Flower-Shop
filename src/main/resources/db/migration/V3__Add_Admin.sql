INSERT INTO users ("id", "active", "address", "balance", "discount", "full_name", "login", "password", "phone")
VALUES ('1', '1', 'admin@admin.com', 2000, 5, 'Admin Admin', 'admin', 'admin123', '+77777777777');

INSERT INTO user_role ("user_id", "roles") VALUES ('1', 'ADMIN');

alter sequence users_id_seq restart with 2;