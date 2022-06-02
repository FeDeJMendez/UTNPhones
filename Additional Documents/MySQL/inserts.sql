TRUNCATE TABLE provinces;
INSERT INTO provinces(name) 
	VALUES ('Buenos Aires'), ('Catamarca'), ('Chaco'), ('Chubut'), 
		('Córdoba'), ('Corrientes'), ('Entre Ríos'), ('Formosa'), 
        ('Jujuy'), ('La Pampa'), ('La Rioja'), ('Mendoza'),
        ('Misiones'), ('Neuquén'), ('Río Negro'), ('Salta'), 
        ('San Juan'), ('San Luis'), ('Santa Cruz'), ('Santa Fe'), 
        ('Santiago del Estero'), ('Tierra del Fuego, AeIAS'), ('Tucumán');


TRUNCATE TABLE cities;
INSERT INTO cities(name, prefix, province_id)
	VALUES ('CABA', 11, 1), ('La Plata', 221, 1),
		('Mar del Plata', 223, 1), ('Tandil', 249, 1),
		('Balcarce', 2266, 1), ('Pinamar', 2254, 1),
        ('Miramar', 2291, 1), ('Necochea', 2262, 1), 
		('Villa Carlos Paz', 3541, 5), ('Córdoba', 351, 5);


TRUNCATE TABLE rates;
INSERT INTO rates(price, starttime, endtime, origin_city_id, destination_city_id)
	VALUES (9.2, '00:02:00', '00:05:00', 1, 2), (8.3, '21:00:00', '22:03:00', 1, 2), 
		(4.4, '22:03:00', '22:05:00', 1, 2), (4.5, '23:55:00', '00:00:00', 1, 3);


TRUNCATE TABLE phonelines;
INSERT INTO phonelines (number)
	VALUES ('2235583444'), ('2254583444'), ('1147475566'), ('2215588144');
    
    
TRUNCATE TABLE persons;
INSERT INTO persons (name, lastname, dni, phoneline_id)
	VALUES ('Juan', 'Perez', 11123456, 1), ('Pepe', 'Juarez', 22123456, 2), ('Martin', 'Ramirez', 33123456, 3), ('Ramiro', 'Martinez', 44123456, 4 );
