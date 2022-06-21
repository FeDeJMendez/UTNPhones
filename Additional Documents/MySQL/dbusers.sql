
DROP USER IF EXISTS 'backoffice'@'%';
CREATE USER 'backoffice'@'%' IDENTIFIED BY 'backoffice';

DROP USER IF EXISTS 'client'@'%';
CREATE USER 'client'@'%' IDENTIFIED BY 'client';

DROP USER IF EXISTS 'infrastructure'@'%';
CREATE USER 'infrastructure'@'%' IDENTIFIED BY 'infrastructure';

DROP USER IF EXISTS 'facturation'@'%';
CREATE USER 'facturation'@'%' IDENTIFIED BY 'facturation';



## SHOW GRANTS FOR 'backoffice'@'%';
## SET PASSWORD FOR 'backoffice'@'%' = 'backoffice123';
## REVOKE SELECT, INSERT, UPDATE, DELETE ON persons FROM 'backoffice'@'%';



### Backoffice grants #####
GRANT SELECT, INSERT, UPDATE, DELETE ON persons TO 'backoffice'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON phonelines TO 'backoffice'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON rates TO 'backoffice'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON cities TO 'backoffice'@'%';
GRANT SELECT ON users TO 'backoffice'@'%';
GRANT SELECT ON v_CallsByClient TO 'backoffice'@'%';
GRANT SELECT ON v_Calls TO 'backoffice'@'%';
GRANT SELECT ON v_Bills TO 'backoffice'@'%';

### Clients grants #####
GRANT SELECT ON persons TO 'client'@'%';
GRANT SELECT ON v_CallsByClient TO 'client'@'%';
GRANT SELECT ON v_Calls TO 'client'@'%';
GRANT SELECT ON v_Bills TO 'client'@'%';

### Infrastructure grants #####
GRANT SELECT ON phonelines TO 'infrastructure'@'%';
GRANT SELECT ON cities TO 'infrastructure'@'%';
GRANT INSERT ON calls TO 'infrastructure'@'%';

### Facturation grants #####
GRANT SELECT ON phonelines TO 'facturation'@'%';
GRANT SELECT, UPDATE ON calls TO 'facturation'@'%';
GRANT SELECT ON persons TO 'facturation'@'%';
GRANT INSERT ON bills TO 'facturation'@'%';