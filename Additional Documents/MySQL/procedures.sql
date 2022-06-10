
##### Liquidate One Phoneline #####

DROP PROCEDURE IF EXISTS p_GenerateBillPhoneline;
DELIMITER $$
	CREATE PROCEDURE p_GenerateBillPhoneline (in vIdPhoneline int)
    BEGIN
		DECLARE vDni int;
        DECLARE vNumber varchar(10);
        DECLARE vTotalcalls int;
        DECLARE vTotalprice double(8,2);
		
        SELECT p.dni, pl.number, IFNULL(COUNT(*),0) as totalcalls, IFNULL(SUM(c.total),0) as totalprice
			INTO vDni, vNumber, vTotalcalls, vTotalprice
			FROM calls c
			INNER JOIN persons p
				ON c.origin_phoneline_id = p.phoneline_id
					AND c.origin_phoneline_id = vIdPhoneline
			INNER JOIN phonelines pl
				ON c.origin_phoneline_id = pl.id
			WHERE c.idBill = 0
			GROUP BY pl.id;
        
		INSERT INTO bills (dni, number, totalcalls, totalprice)
			VALUES (vDni, vNumber, vTotalcalls, vTotalprice);
            
		UPDATE calls
			SET idBill = (SELECT MAX(id) FROM bills)
			WHERE origin_phoneline_id = vIdPhoneline
				AND idBill = 0;
    END;
$$



