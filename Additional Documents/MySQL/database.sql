
DROP DATABASE utnphones;
CREATE DATABASE utnphones;
USE utnphones;


	##### Creación de tablas #####

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users(
    id int auto_increment,
    username varchar(50),
    password varchar(50),
    rol varchar(31),
    person_id int,
    --
    constraint pk_idUser primary key (id)
);

DROP TABLE IF EXISTS persons;
CREATE TABLE IF NOT EXISTS persons(
	DTYPE varchar(31),
    id int auto_increment,
    name varchar(50),
    lastname varchar(50),
    dni int,
    phoneline_id int,
    --
    constraint pk_idPerson primary key (id),
    constraint u_dni unique(dni),
    constraint u_phoneline unique(phoneline_id),
    foreign key fk_phoneline (phoneline_id) references phonelines (id)
);

DROP TABLE IF EXISTS phonelines;
CREATE TABLE IF NOT EXISTS phonelines(
    id int auto_increment,
    number varchar(10),
    status bit,
    city_id int,
    --
    constraint pk_idPhoneline primary key (id),
    constraint u_number unique(number),
    foreign key fk_city (city_id) references cities (id)
);

DROP TABLE IF EXISTS cities;
CREATE TABLE IF NOT EXISTS cities(
    id int auto_increment,
    name varchar(50),
    prefix int,
    province_id int,
    --
    constraint pk_idCity primary key (id),
    constraint u_prefix unique(prefix),
    foreign key fk_province (province_id) references provinces (id)
);

DROP TABLE IF EXISTS provinces;
CREATE TABLE IF NOT EXISTS provinces(
    id int auto_increment,
    name varchar(50),
    --
    constraint pk_idProvince primary key (id)
);

DROP TABLE IF EXISTS rates;
CREATE TABLE IF NOT EXISTS rates(
    id int auto_increment,
    price double(8,2),
    starttime time,
    endtime time,
    origin_city_id int,
    destination_city_id int,
    --
    constraint pk_idRate primary key (id),
    foreign key fk_cityOrigin (origin_city_id) references cities (id),
    foreign key fk_cityDestination (destination_city_id) references cities (id)
);

DROP TABLE IF EXISTS calls;
CREATE TABLE IF NOT EXISTS calls(
    id int auto_increment,
    starttime datetime,
    duration int,
    total double(8,2), 
    idBill int,
    origin_phoneline_id int,
    destination_phoneline_id int,
    --
    constraint pk_idCall primary key (id),
    foreign key fk_phonelineOrigin (origin_phoneline_id) references phonelines (id),
    foreign key fk_phonelineDestination (destination_phoneline_id) references phonelines (id)
);

DROP TABLE IF EXISTS bills;
CREATE TABLE IF NOT EXISTS bills(
    id int auto_increment,
    dni int,
    number varchar(10),
    totalcalls int,
    costprice double(8,2),
    totalprice double(8,2),
    datecreation date,
    expiration date,
    paid bit,
    --
    constraint pk_idBill primary key (id)
);


