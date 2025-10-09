-- Insert sample users with new credentials (ignore duplicates)
INSERT IGNORE INTO users (email, password, first_name, last_name, contact_number, role, is_active, created_at, updated_at) VALUES
('admin@gmail.com', '$2a$10$N.wmKF7YymcqAUc5EjkKBOxLvwIghGHHWcg8GmG7jbYvVF6fGK7Ky', 'Admin', 'User', '1234567890', 'ADMIN', true, NOW(), NOW()),
('agent@gmail.com', '$2a$10$N.wmKF7YymcqAUc5EjkKBOxLvwIghGHHWcg8GmG7jbYvVF6fGK7Ky', 'John', 'Agent', '1234567891', 'AGENT', true, NOW(), NOW()),
('buyer@gmail.com', '$2a$10$N.wmKF7YymcqAUc5EjkKBOxLvwIghGHHWcg8GmG7jbYvVF6fGK7Ky', 'Jane', 'Buyer', '1234567892', 'BUYER', true, NOW(), NOW()),
('seller@gmail.com', '$2a$10$N.wmKF7YymcqAUc5EjkKBOxLvwIghGHHWcg8GmG7jbYvVF6fGK7Ky', 'Bob', 'Seller', '1234567893', 'SELLER', true, NOW(), NOW());