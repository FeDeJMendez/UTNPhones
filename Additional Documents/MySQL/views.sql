
DROP VIEW  IF EXISTS v_CallsByClient;
-- -- --
CREATE VIEW v_CallsByClient AS
	SELECT pe.id AS idClient,
			plo.number AS originNumber,
			cio.name originCityName,
			pld.number destinationNumber,
			cid.name destinationCityName,
			ca.total,
			ca.duration,
			ca.starttime
		FROM calls ca
		INNER JOIN phonelines plo
			ON ca.origin_phoneline_id = plo.id
		INNER JOIN phonelines pld
			ON ca.destination_phoneline_id = pld.id
		INNER JOIN cities cio
			ON plo.city_id = cio.id
		INNER JOIN cities cid
			ON pld.city_id = cid.id
		INNER JOIN persons pe
			ON pe.phoneline_id = plo.id;



DROP VIEW  IF EXISTS v_Calls;
-- -- --
CREATE VIEW v_Calls AS
	SELECT p.id idClient, c.*
		FROM calls c
        INNER JOIN phonelines pl
			ON c.origin_phoneline_id = pl.id
		INNER JOIN persons p
			ON p.phoneline_id = pl.id;



DROP VIEW  IF EXISTS v_Bills;
-- -- --
CREATE VIEW v_Bills AS
	SELECT p.id idClient, b.*
		FROM bills b
		INNER JOIN persons p
			ON p.dni = b.dni;
