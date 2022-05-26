INSERT INTO provinces(name) values ('Buenos Aires'), ('Neuquen'), ('Misiones'), ('Cordoba');

INSERT INTO cities(name, prefix, province_id) values ('Mar del Plata', 223, 1), ('Chipoletti', 123, 2), ('Carlos Paz', 3456, 4);

INSERT INTO lines(number, status, city_id) values ('2235583444', true, 1), ('2254583444', true, 1), ('1147475566', true, 1), ('2215588144', true, 1);