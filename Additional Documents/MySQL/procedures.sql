
##### Liquidate One Phoneline #####

DROP PROCEDURE IF EXISTS p_GenerateBillPhoneline;
DELIMITER $$
	CREATE PROCEDURE p_GenerateBillPhoneline (in vIdPhoneline int)
    BEGIN
		DECLARE vDni int;
        DECLARE vNumber varchar(10);
        DECLARE vTotalcalls int;
        DECLARE vTotalprice double(8,2);
		-- -- --
        SELECT p.dni, 
				pl.number,
				IFNULL(COUNT(*),0) as totalcalls, 
                IFNULL(SUM(c.total),0) as totalprice
			INTO vDni, vNumber, vTotalcalls, vTotalprice
			FROM calls c
			INNER JOIN persons p
				ON c.origin_phoneline_id = p.phoneline_id
					AND c.origin_phoneline_id = vIdPhoneline
			INNER JOIN phonelines pl
				ON c.origin_phoneline_id = pl.id
			WHERE c.idBill IS NULL
			GROUP BY pl.id;
        -- -- --
        IF (vTotalcalls > 0) THEN
			INSERT INTO bills (dni, number, totalcalls, totalprice)
				VALUES (vDni, vNumber, vTotalcalls, vTotalprice);
			-- -- --
			UPDATE calls
				SET idBill = last_insert_id() /*(SELECT MAX(id) FROM bills)*/
				WHERE origin_phoneline_id = vIdPhoneline
					AND idBill IS NULL;
		END IF;
    END;
$$
DELIMITER ;



##### Liquidate One Client #####

DROP PROCEDURE IF EXISTS p_GenerateBillClient;
DELIMITER $$
	CREATE PROCEDURE p_GenerateBillClient (in vIdClient int)
    BEGIN
		DECLARE vIdPhoneline int;
        -- -- --
        SELECT phoneline_id 
			INTO vIdPhoneline
			FROM persons
            WHERE id = vIdClient;
		-- -- --
		IF ((vIdPhoneline != 0) AND (vIdPhoneline IS NOT NULL)) THEN
			CALL p_GenerateBillPhoneline(vIdPhoneline);
        END IF;
    END;
$$
DELIMITER ;



##### Return the Calls of a Client By Date using the view v_CallsByClient #####

DROP PROCEDURE IF EXISTS p_CallsByClientAndDate;
DELIMITER $$
	CREATE PROCEDURE p_CallsByClientAndDate (
    in vIdClient int,
    in vdateCalls date
    )
    BEGIN
		SELECT originNumber,
			originCityName,
			destinationNumber,
			destinationCityName,
			total,
			duration,
			starttime
		FROM v_CallsByClient
			WHERE idClient = vIdClient
			AND DATE(starttime) = vdateCalls;
    END;
$$
DELIMITER ;

## SELECT * FROM calls;
## CALL p_CallsByClientAndDate (2, "2018-02-16", "2018-02-17");



##### Return the Calls of a Client BETWEEN Dates using the view v_Calls #####

DROP PROCEDURE IF EXISTS p_CallsByClientBetweenDates;
DELIMITER $$
	CREATE PROCEDURE p_CallsByClientBetweenDates (
    in vIdClient int,
    in vdateStartCalls date,
    in vdateEndCalls date
    )
    BEGIN
		SELECT id, starttime, duration,total, idBill, origin_phoneline_id, destination_phoneline_id
		FROM v_Calls
			WHERE idClient = vIdClient
				AND DATE(starttime) BETWEEN vdateStartCalls AND vdateEndCalls;
    END;
$$
DELIMITER ;



##### Return the Bills of a Client BETWEEN Dates using the view v_Bills #####

DROP PROCEDURE IF EXISTS p_BillsByClientBetweenDates;
DELIMITER $$
	CREATE PROCEDURE p_BillsByClientBetweenDates (
    in vIdClient int,
    in vdateStartCalls date,
    in vdateEndCalls date
    )
    BEGIN
		SELECT id, dni, number, totalcalls, costprice, totalprice, datecreation, expiration, paid
		FROM v_Bills
			WHERE idClient = vIdClient
				AND DATE(datecreation) BETWEEN vdateStartCalls AND vdateEndCalls;
    END;
$$
DELIMITER ;


