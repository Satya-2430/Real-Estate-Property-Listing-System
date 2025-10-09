-- Insert sample contacts
INSERT INTO contacts (name, email, phone_number, subject, message, status, created_at) VALUES
('John Smith', 'john@example.com', '1234567890', 'Property Inquiry', 'I am interested in the luxury villa listing', 'NEW', NOW()),
('Sarah Johnson', 'sarah@example.com', '0987654321', 'General Question', 'What are your commission rates?', 'NEW', NOW());

-- Insert sample visitors
INSERT INTO visitors (ip_address, user_agent, page_visited, session_id, visited_at) VALUES
('192.168.1.1', 'Mozilla/5.0 Chrome/91.0', '/properties', 'sess_001', NOW()),
('192.168.1.2', 'Mozilla/5.0 Firefox/89.0', '/dashboard', 'sess_002', NOW());

-- Insert sample admin metrics
INSERT INTO admin_metrics (total_users, total_properties, total_transactions, total_revenue, created_at, updated_at) VALUES
(100, 50, 200, 500000.00, NOW(), NOW());