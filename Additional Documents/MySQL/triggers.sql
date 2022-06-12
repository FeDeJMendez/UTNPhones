
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



##### DELETE PHONELINE -> Verification and Delete Line for Client #####

DROP TRIGGER IF EXISTS TBD_VerificationAndEditClientPhonelineForDeletePhoneline;
DELIMITER $$
	CREATE TRIGGER TBD_VerificationAndEditClientPhonelineForDeletePhoneline
		BEFORE DELETE ON phonelines FOR EACH ROW
	BEGIN
		IF EXISTS (SELECT * FROM calls c
					WHERE c.origin_phoneline_id = OLD.id
						OR c.destination_phoneline_id = OLD.id
			)THEN
			SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'THE LINE HAS ASSOCIATED CALL/S.',
				mysql_errno = '1';
		ELSE
			UPDATE persons 
				SET phoneline_id = null 
				WHERE phoneline_id = OLD.id;
		END IF;
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
		DECLARE vCostPrice double(8,2);
        SELECT NEW.totalprice / 1.21 
			INTO vCostPrice;
		SET NEW.costprice = vCostPrice;
        SET NEW.datecreation = CURDATE();
        SET NEW.expiration = TIMESTAMPADD(DAY,15,new.datecreation);
        SET NEW.paid = false;        
    END;
$$



##### DELETE CLIENT -> Liquidate calls and create Bill #####
 
DROP TRIGGER IF EXISTS TBD_LiquidateClient;
DELIMITER $$
	CREATE TRIGGER TBD_LiquidateClient
		BEFORE DELETE ON persons FOR EACH ROW
	BEGIN
		CALL p_GenerateBillPhoneline(IFNULL(OLD.phoneline_id, 0));
    END;
$$



##### UPDATE LINE TO CLIENT -> Liquidate calls and create Bill #####

DROP TRIGGER IF EXISTS TBU_LiquidatePhonelineClient;
DELIMITER $$
	CREATE TRIGGER TBU_LiquidatePhonelineClient
		BEFORE UPDATE ON persons FOR EACH ROW
	BEGIN
		IF ((OLD.phoneline_id != NULL) AND (OLD.phoneline_id != NEW.phoneline_id)) THEN
			CALL p_GenerateBillPhoneline(IFNULL(OLD.phoneline_id, 0));
        END IF;
    END;
$$



