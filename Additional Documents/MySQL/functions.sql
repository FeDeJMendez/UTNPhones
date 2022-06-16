
##### CALCULATE PREFIX AND ID_CITY FROM NUMBER #####

DROP FUNCTION IF EXISTS f_FindCity;
DELIMITER $$
CREATE FUNCTION f_FindCity (inNumber varchar(10))
	RETURNS int DETERMINISTIC
BEGIN
	DECLARE vIdCity int default 0;
    SELECT id INTO vIdCity FROM cities
		WHERE inNumber LIKE CONCAT(prefix,'%')
        ORDER BY LENGTH(prefix) DESC
        LIMIT 1;
	IF vIdCity = 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'THE NUMBER DOES NOT BELONG TO A REGISTERED CITY.',
			mysql_errno = '1';
	END IF;
	RETURN vIdCity;
END;
$$
DELIMITER ;



##### CALCULATE CALL PRICE  #####

DROP FUNCTION IF EXISTS f_CalculateCallPrice;
DELIMITER $$
CREATE FUNCTION f_CalculateCallPrice (
		vStartTime datetime,
        vDuration int,
        vIdCityOrigin int,
        vIdCityDestination int
	)
	RETURNS double(8,2) READS SQL DATA
BEGIN
	DECLARE vEndTime datetime;
    DECLARE vStartLimit datetime;
    DECLARE vTotal double(8,2) default 0.0;
    DECLARE vPrice double(8,2) default 7.0; ## Si no hay tarifa en ese horario cobra $7
    DECLARE vEndRate time default '00:00:00';
    DECLARE vIdRate int default 0;
    DECLARE vMinutesInRate int;
    -- -- --
    SELECT vStartTime INTO vStartLimit;
    SELECT TIMESTAMPADD(MINUTE, vDuration, vStartTime)
		INTO vEndTime;
	-- -- --
	WHILE (vStartLimit < vEndTime) DO
		SELECT 0 INTO vIdRate;
        SELECT '00:00:00' INTO vEndRate;
        SELECT 7.0 INTO vPrice;
		SELECT id, price, endtime INTO vIdRate, vPrice, vEndRate 
			FROM rates 
            WHERE TIME(vStartLimit) >= starttime
				AND TIME(vStartLimit) < endtime
                AND origin_city_id = vIdCityOrigin
                AND destination_city_id = vIdCityDestination;
		-- -- -- 
		IF vIdRate = 0 THEN
			SELECT starttime INTO vEndRate
				FROM rates
                WHERE starttime >= TIME(vStartLimit)
					AND origin_city_id = vIdCityOrigin
					AND destination_city_id = vIdCityDestination 
                ORDER BY starttime
                LIMIT 1;
        END IF;
        -- -- -- 
        IF ((DATE(vStartLimit) = DATE(vEndTime)) AND ((TIME(vEndTime) < vEndRate)) OR (vEndRate = '00:00:00')) THEN
			SELECT TIMESTAMPDIFF(MINUTE, vStartLimit, vEndTime)
				INTO vMinutesInRate;
		ELSEIF (vEndRate = '00:00:00' OR vEndRate = '23:59:59') THEN
			SELECT (TIMESTAMPDIFF(MINUTE, vStartLimit, CONCAT(DATE(vStartLimit), ' 23:59:00')) + 1) 
				INTO vMinutesInRate;
        ELSE
			SELECT TIMESTAMPDIFF(MINUTE, vStartLimit, CONCAT(DATE(vStartLimit), ' ', vEndRate))
				INTO vMinutesInRate;
		END IF;
        -- -- -- 
        SELECT vTotal + (vMinutesInRate * vPrice) INTO vTotal;
        SELECT TIMESTAMPADD(MINUTE, vMinutesInRate, vStartLimit)
			INTO vStartLimit;
    END WHILE;
    -- -- --
    RETURN vTotal;
END;
$$
DELIMITER ;









