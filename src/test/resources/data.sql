-- Usuario 1: Facturable (billable = true)
INSERT INTO users (id, name, email, first_name, family_name, identity, address, city, province, postal_code)
VALUES (1, 'User One', 'user1@example.com', 'User', 'One', '12345678A', 'Calle Mayor 1', 'Madrid', 'Madrid', '28001');

-- Usuario 2: No Facturable (billable = false)
INSERT INTO users (id, name, email, first_name, family_name, identity, address, city, province, postal_code)
VALUES (2, 'User Two', 'user2@example.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL);