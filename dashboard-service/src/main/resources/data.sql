-- Insert sample dashboard metrics
INSERT INTO dashboard_metrics (user_id, metric_type, metric_value, metric_date, created_at, updated_at) VALUES
(3, 'TOTAL_FAVORITES', 5, NOW(), NOW(), NOW()),
(3, 'TOTAL_BOOKINGS', 3, NOW(), NOW(), NOW()),
(4, 'TOTAL_LISTINGS', 12, NOW(), NOW(), NOW()),
(4, 'ACTIVE_LISTINGS', 8, NOW(), NOW(), NOW());

-- Insert sample activity logs
INSERT INTO activity_log (user_id, activity_type, activity_description, entity_id, entity_type, created_at) VALUES
(3, 'PROPERTY_VIEW', 'Viewed luxury villa property', 1, 'PROPERTY', NOW()),
(3, 'BOOKING_CREATED', 'Created booking for apartment', 2, 'BOOKING', NOW()),
(4, 'PROPERTY_LISTED', 'Listed new property', 3, 'PROPERTY', NOW());