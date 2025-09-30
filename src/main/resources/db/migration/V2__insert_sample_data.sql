-- Flyway Migration: Insert sample data
-- Sample vets: 101, 102

INSERT INTO vet_salary (id, vet_id, vet_name, salary)
VALUES
    (1, 101, 'John Doe', 50.00),
    (2, 102, 'Jane Doe', 65.00),
    (3, 1, 'James Carter', 50.00),
    (4, 2, 'Helen Leary',  55.00),
    (5, 3, 'Linda Douglas', 65.00),
    (6, 4, 'Rafael Ortega',  60.00),
    (7, 5, 'Henry Stevens', 70.00),
    (8, 6, 'Sharon Jenkins',  40.00)
ON CONFLICT (vet_id) DO UPDATE SET salary = EXCLUDED.salary;

-- Sample payments
INSERT INTO vet_payment (id, vet_id, payment_date, amount)
VALUES
    (1, 101, DATE '2025-09-01', 0.00),
    (2, 102, DATE '2025-09-01', 0.00);

-- Reset sequence to the highest existing ID + 1
SELECT setval('vet_payment_id_seq', (SELECT COALESCE(MAX(id), 1) FROM vet_payment));