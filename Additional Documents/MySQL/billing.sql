
##### Generate Billing All Phonelines #####

DROP PROCEDURE IF EXISTS p_GenerateBills;
DELIMITER $$
	CREATE PROCEDURE p_GenerateBills ()
    BEGIN
		DECLARE vFin int default 0;
        DECLARE vId int;
        DECLARE vDni int;
        DECLARE vNumber varchar(10);
        DECLARE vTotalcalls int;
        DECLARE vTotalprice double(8,2);
        -- -- --
        DECLARE curPhonelineBill CURSOR FOR
			SELECT pl.id,
					pe.dni,
					pl.number,
                    IFNULL(COUNT(*),0) as totalcalls, 
                    IFNULL(SUM(c.total),0) as totalprice                    
				FROM phonelines pl
                INNER JOIN calls c
					ON pl.id = c.origin_phoneline_id
				INNER JOIN persons pe
					ON pl.id = pe.phoneline_id
				WHERE c.idBill = 0
                GROUP BY pl.id;
		-- -- -- 
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET vFin = 1;
        -- -- -- 
        OPEN curPhonelineBill;
        -- -- -- 
        GETDATA : LOOP
			FETCH curPhonelineBill 
				INTO vId, vDni, vNumber, vTotalcalls, vTotalprice;
			IF vFin = 1 THEN
				LEAVE GETDATA;
            END IF;
			INSERT INTO bills (dni, number, totalcalls, totalprice)
				VALUES (vDni, vNumber, vTotalcalls, vTotalprice);
			UPDATE calls
				SET idBill = (SELECT MAX(id) FROM bills)
                WHERE origin_phoneline_id = vId
					AND idBill = 0;
        END LOOP GETDATA;
        -- -- -- 
        CLOSE curPhonelineBill;
    END;
$$
DELIMITER ;


##### Monthly Event (1st of Month) #####

SET GLOBAL event_scheduler = ON;

DROP EVENT IF EXISTS e_Billing;
DELIMITER $$
CREATE EVENT e_Billing
	ON SCHEDULE EVERY 1 DAY 
	STARTS CONCAT(TIMESTAMPADD(DAY, 1, CURDATE()), ' 00:00:01') DO 
    BEGIN
		IF DAY(CURDATE()) = 1 THEN
			CALL p_GenerateBills();
		END IF;
    END;
$$
DELIMITER ;


##### Test Event #####

/*DROP EVENT IF EXISTS e_BillingPruebaaa;
DELIMITER $$
CREATE EVENT e_BillingPruebaaa
	ON SCHEDULE EVERY 1 MINUTE
	STARTS current_timestamp() DO 
    BEGIN
		CALL p_GenerateBills();
    END;
$$*/