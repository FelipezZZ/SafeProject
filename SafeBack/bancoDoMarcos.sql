create database projeto;

use projeto;

create table pessoa(
	cod_pessoa integer primary key not null auto_increment,
	cod_firebase varchar(100),
	
	universidade varchar(50),
	RA varchar (15),
	descricao varchar(200),
	
	nome varchar(50) not null,
	email varchar(50) not null unique,
	senha varchar(50)not null,
	sexo integer not null,
	tipoperfil integer not null ,
	verificado boolean not null,
	dataCadastro date not null,
	primeiroAcesso boolean not null,
	cadastroFB boolean not null
);

create table anamnese(
	cod_pessoa integer not null,
	constraint fkCod_pessoa foreign key (cod_pessoa) references pessoa(cod_pessoa),
	a integer not null,
	d integer not null,
	s integer not null,
	dataAnamnese date not null
);

insert into anamnese values(4, 15, 25, 35, now());
insert into anamnese values(4, 10, 25, 30, now());
insert into anamnese values(4, 10, 20, 25, now());
insert into anamnese values(4, 5, 10, 20, now());
insert into anamnese values(4, 15, 25, 35, now());

create table admins(
	cod_admin integer primary key auto_increment,
	login varchar(50) not null unique,
	senha varchar(50) not null
);

insert into admins values(0,"adm","123");

/*
create table universidades(
	cod_universidade integer primary key auto_increment,
	nome_universidade varchar(50) not null unique
);

insert into universidades values(0, "Universidade Teste 1");
insert into universidades values(0, "Universidade Teste 2");
insert into universidades values(0, "Universidade Teste 3");
insert into universidades values(0, "Universidade Teste 4");*/

insert into pessoa values(0,null,null,null,"Caio","c@gmail.com","123456",2,false,now(),true,false);
SELECT nickname FROM pessoa WHERE login = "jooj" AND senha = 123 ;

Create table horarios(
	cod_estagiario integer not null,
	constraint fkCod_pessoa foreign key(cod_estagiario) references pessoa(cod_pessoa),
	dom varchar(12),
	seg varchar(12),
	ter varchar(12),
	qua varchar(12),
	qui varchar(12),
	sex varchar(12),
	sab varchar(12)
);


--! UTILIZAR ESSAS TABELAS EM CASO DE SECÇÃO DO BANCO

--!create table paciente(
--cod_paciente integer primary key not null auto_increment,
--login varchar(50) not null unique,
--nickname varchar(50) not null unique,
--anonimo boolean not null,
--registro date not null,
--primeiroAcesso boolean not null,

--);

INSERT INTO horarios (cod_estagiario, dom, seg, ter, qua, qui, sex, sab) VALUES ('21', '12:00~~13:00', '13:00~~14:00', '15:00~~16:00', '13:00~~14:00', '13:00~~14:00', '13:00~~14:00', '13:00~~14:00');
INSERT INTO horarios (cod_estagiario, dom, seg, ter, qua, qui, sex, sab) VALUES ('22', '12:00~~13:00', '13:00~~14:00', '15:00~~16:00', '13:00~~14:00', '13:00~~17:00', '13:00~~14:00', '13:00~~14:00');

--create table funcionario(
--cod_funcionario integer primary key auto_increment,
--login varchar(50)not null unique,
--nome varchar(50) not null,
--senha varchar(50) not null,
--verificado boolean not null,
--RA varchar (15) not null,



--)
