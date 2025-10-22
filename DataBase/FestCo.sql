create database ferias;
drop database ferias;
use ferias;

create table Tipo_Documento(
	id bigint primary key auto_increment,
	documento varchar(25)
);

create table administrador(
	num_documento bigint primary key,
	tipo_documento bigint,
	nombres varchar(15),
	apellidos varchar(15),
	correo varchar(30),
	contrasena varchar(20),
	constraint foreign key  (tipo_documento) references Tipo_Documento(id)
);

create table usuarios(
	num_documento bigint primary key,
	tipo_documento bigint,
	nombres varchar(15),
	apellidos varchar(15),
	correo varchar(30),
	contrasena varchar(20),
	num_telefonico int(15),
	constraint foreign key (tipo_documento) references Tipo_Documento(id)
);


create table localidad(
	id bigint primary key auto_increment,
	localidada varchar(20)
);

create table Tipo_Evento(
	id bigint primary key auto_increment,
	tipo varchar(15)
);



create table municipio(
	id bigint primary key auto_increment,
    municipio varchar(25),
    id_departamento bigint
);

create table Evento(
	id bigint primary key auto_increment,
    nombre varchar(25),
    descripcion varchar(25),
    fecha_inicio date,
    fecha_fin date,
    id_municipio bigint,
    hora_inicio time,
    hora_fin time,
    id_administrador bigint,
    constraint foreign key (id_municipio) references municipio(id),
    constraint foreign key (id_administrador) references administrador(num_documento)
);



create table cantidad_boletas(
	id bigint primary key auto_increment,
    cantidad int,
    valor double, 
    id_localidad bigint,
    id_evento bigint,
	constraint foreign key (id_localidad) references localidad(id),
    constraint foreign key (id_evento) references evento(id)
);

create table artistas(
	id int primary key auto_increment,
    nombre varchar(25),
    genero_musical varchar(20),
    ciudad_natal varchar(20),
    id_evento bigint,
    constraint foreign key (id_evento) references evento(id)
);


create table boletas(
	id bigint primary key auto_increment,
	valor double,
	fecha date,
	id_localidad bigint,
	cantidad int,
	id_evento bigint,
	constraint foreign key (id_localidad) references localidad(id),
	constraint foreign key (id_evento) references evento(id)
);

create table reservas(
	id bigint primary key auto_increment,
    id_boletas bigint,
    id_usuario bigint,
    id_evento bigint,
    valor double,
    constraint foreign key (id_boletas) references boletas(id),
    constraint foreign key (id_usuario) references usuarios(num_documento),
    constraint foreign key (id_evento) references evento(id)
);

create table transaccion(
	id bigint primary key auto_increment,
    id_reserva bigint,
    banco varchar(30),
    valor double,
    num_transaccion varchar(30),
    constraint foreign key (id_reserva) references reservas(id)
);

create table compra(
	id bigint primary key auto_increment,
    id_transaccion bigint,
    estado varchar(20),
	constraint foreign key (id_transaccion) references transaccion(id)
);

create table departamento(
	id bigint primary key auto_increment,
    departamento varchar(20)
);


-- Insertar datos de ejemplo para departamentos
INSERT INTO departamento (departamento) VALUES 
('Valle del Cauca'),
('Cundinamarca'),
('Antioquia'),
('Atlántico'),
('Santander');

-- Insertar datos de ejemplo para municipios
INSERT INTO municipio (municipio, id_departamento) VALUES 
('Cali', 1),
('Palmira', 1),
('Buenaventura', 1),
('Bogotá', 2),
('Soacha', 2),
('Medellín', 3),
('Bello', 3),
('Barranquilla', 4),
('Soledad', 4),
('Bucaramanga', 5),
('Floridablanca', 5);

-- Insertar datos de ejemplo para tipo de documento
INSERT INTO Tipo_Documento (documento) VALUES 
('Cédula de Ciudadanía'),
('Cédula de Extranjería'),
('Pasaporte'),
('Tarjeta de Identidad');

-- Insertar datos de ejemplo para administrador
INSERT INTO administrador (num_documento, tipo_documento, nombres, apellidos, correo, contrasena) VALUES 
(1024480167, 1, 'Admin', 'Principal', 'admin@ferias.com', 'admin123');

-- Insertar datos de ejemplo para localidades
INSERT INTO localidad (localidada) VALUES 
('VIP'),
('Platea'),
('General'),
('Palco'),
('Tribuna');

ALTER TABLE municipio
ADD CONSTRAINT fk_municipio_departamento
FOREIGN KEY (id_departamento)
REFERENCES departamento(id);

