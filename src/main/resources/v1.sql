/**
 * Author:  thiago
 * Created: 28 de out. de 2025
 */

--V1

create table ger_tipo_grupo (
    id int, 
    nome varchar(255)
);

create table ger_tipo (
    id int, 
    grupo_id int references ger_tipo_grupo(id),
    nome varchar(255),
    detalhe varchar(512)
);

create index on ger_tipo (tipo_id, id);

create table ger_arquivo (
    id serial primary key,
    versao int, 
    nome_original varchar(512)
    file_path varchar(255)
);

create table ger_pessoa (
    id serial primary key, 
    versao int, 
    nome_primeiro varchar(255),
    nome_meio varchar(255),
    nome_ultimo varchar(255),
    apelido varchar(255),
    data_nascimento date 
);

create table ger_documento (
    id serial primary key, 
    versao int, 
    tipo_id int, 
    numero varchar(127),
    arquivo_id int
);

create table ger_pessoa_documento (
    id serial primary key, 
    versao int, 
    pessoa_id int references ger_pessoa(id),
    documento_id int references ger_documento(id)
);

