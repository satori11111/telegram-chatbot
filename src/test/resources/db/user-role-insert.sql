INSERT INTO "user" (id, first_name, last_name, password, email)
VALUES
    (2, 'UserName 1', 'LastName 1', 'Password 1', 'email@gmail.com'),
    (3, 'UserName 2', 'LastName 2', 'Password 2', 'Email@gmail.com 2');

INSERT INTO user_role (user_id, role_id)
VALUES
    (2, 1),
    (3, 1);
