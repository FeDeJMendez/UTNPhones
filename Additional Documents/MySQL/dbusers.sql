
CREATE USER 'backoffice'@'%' IDENTIFIED BY 'backoffice';
CREATE USER 'client'@'%' IDENTIFIED BY 'client';
CREATE USER 'infrastructure'@'%' IDENTIFIED BY 'infrastructure';
CREATE USER 'facturation'@'%' IDENTIFIED BY 'facturation';

### Backoffice grants #####
GRANT SELECT, INSERT, UPDATE, DELETE ON clients TO 'backoffice'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON phonelines TO 'backoffice'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON rates TO 'backoffice'@'%';
GRANT SELECT ON users TO 'backoffice'@'%';
GRANT SELECT ON v_CallsByClient TO 'backoffice'@'%';
GRANT SELECT ON v_Calls TO 'backoffice'@'%';
GRANT SELECT ON v_Bills TO 'backoffice'@'%';

### Clients grants #####
GRANT SELECT ON users TO 'client'@'%';
GRANT SELECT ON v_CallsByClient TO 'client'@'%';
GRANT SELECT ON v_Calls TO 'client'@'%';
GRANT SELECT ON v_Bills TO 'client'@'%';

### Infrastructure grants #####
GRANT SELECT ON phonelines TO 'infrastructure'@'%';
GRANT SELECT ON cities TO 'infrastructure'@'%';
GRANT INSERT ON calls TO 'infrastructure'@'%';

### Facturation grants #####
GRANT SELECT ON phonelines TO 'facturation'@'%';
GRANT SELECT ON calls TO 'facturation'@'%';
GRANT SELECT ON persons TO 'facturation'@'%';
GRANT UPDATE ON calls TO 'facturation'@'%';
GRANT INSERT ON bills TO 'facturation'@'%';