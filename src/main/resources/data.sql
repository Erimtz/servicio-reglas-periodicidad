-- Datos iniciales de la tabla product_rule (se cargan al arrancar, después de que Hibernate crea la tabla)
INSERT INTO product_rule (client_id, product_type, frequency, active) VALUES ('C-1001', 'DEBIT_CARD',      'MONTHLY',     TRUE);
INSERT INTO product_rule (client_id, product_type, frequency, active) VALUES ('C-1001', 'PAYROLL_CREDIT',  'BIWEEKLY',    TRUE);
INSERT INTO product_rule (client_id, product_type, frequency, active) VALUES ('C-1002', 'PAYROLL_CREDIT',  'FORTNIGHTLY', TRUE);
INSERT INTO product_rule (client_id, product_type, frequency, active) VALUES ('C-1002', 'INVESTMENTS',     'MONTHLY',     TRUE);
INSERT INTO product_rule (client_id, product_type, frequency, active) VALUES ('C-1003', 'PERSONAL_CREDIT', 'MONTHLY',     TRUE);
INSERT INTO product_rule (client_id, product_type, frequency, active) VALUES ('C-1003', 'MORTGAGE_CREDIT', 'MONTHLY',     TRUE);
INSERT INTO product_rule (client_id, product_type, frequency, active) VALUES ('C-1003', 'DEBIT_CARD',      'MONTHLY',     TRUE);
INSERT INTO product_rule (client_id, product_type, frequency, active) VALUES ('C-1004', 'PAYROLL_CREDIT',  'MONTHLY',     TRUE);
INSERT INTO product_rule (client_id, product_type, frequency, active) VALUES ('C-1005', 'MORTGAGE_CREDIT', 'MONTHLY',     FALSE);
