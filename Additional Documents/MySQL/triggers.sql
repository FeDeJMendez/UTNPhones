
 ##### ADD NEW PHONELINE -> Set Status = true and find city_id #####
 
DROP TRIGGER IF EXISTS TBI_AddStatusAndCityInNewPhoneline;
DELIMITER $$
	CREATE TRIGGER TBI_AddStatusAndCityInNewPhoneline
		BEFORE INSERT ON phonelines FOR EACH ROW
	BEGIN
		SET NEW.status = true;
        SET NEW.city_id = f_FindCity(NEW.number);
    END;
$$



 ##### ADD NEW RATE -> Check if end in the end of the day #####
 
DROP TRIGGER IF EXISTS TBI_CheckEndNewRate;
DELIMITER $$
	CREATE TRIGGER TBI_CheckEndNewRate
		BEFORE INSERT ON rates FOR EACH ROW
	BEGIN
		IF NEW.endtime = '00:00:00' THEN
			SET NEW.endtime = '23:59:59';
        END IF;
    END;
$$



##### ADD NEW CALL -> Set idBill = 0 and calculate total #####

DROP TRIGGER IF EXISTS TBI_NewCall;
DELIMITER $$
	CREATE TRIGGER TBI_NewCall
		BEFORE INSERT ON calls FOR EACH ROW
	BEGIN
		DECLARE vTotal int;
        DECLARE vNumberOriginCity varchar(10);
        DECLARE vNumberDestinationCity varchar(10);
        DECLARE vIdOriginCity int;
        DECLARE vIdDestinationCity int;
        -- -- -- Set not Bill
        SET NEW.idBill = 0;
        -- -- -- Get the numbers
        SELECT number INTO vNumberOriginCity 
			FROM phonelines 
            WHERE id = NEW.origin_phoneline_id;
        SELECT number INTO vNumberDestinationCity 
			FROM phonelines 
            WHERE id = NEW.destination_phoneline_id;
		-- -- -- Get the idCities
        SELECT f_FindCity(vNumberOriginCity) 
			INTO vIdOriginCity;
        SELECT f_FindCity(vNumberDestinationCity) 
			INTO vIdDestinationCity;
		-- -- -- Calculate Price
        SELECT f_CalculateCallPrice(NEW.starttime, NEW.duration, vIdOriginCity , vIdDestinationCity) 
			INTO vTotal;
        SET NEW.total = vTotal;
    END;
$$



 ##### ADD NEW BILL -> Set costPrice (without IVA), datecreation, expiration (15 days), paid (false) #####
 
DROP TRIGGER IF EXISTS TBI_AddDataInNewBill;
DELIMITER $$
	CREATE TRIGGER TBI_AddDataInNewBill
		BEFORE INSERT ON bills FOR EACH ROW
	BEGIN
		SET NEW.costprice = NEW.totalprice / 1.21;
        SET NEW.datecreation = CURDATE();
        SET NEW.expiration = TIMESTAMPADD(DAY,15,new.datecreation);
        SET NEW.paid = false;        
    END;
$$







