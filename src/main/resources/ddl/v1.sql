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

create table ger_estado (
    id serial primary key, 
    versao int, 
    nome varchar(255),
    pais varchar(127),
);

create table ger_cidade (
    id serial primary key, 
    versao int, 
    nome varchar(255),
    estado int references ger_estado(id)
);

create table ger_endereco (
    id serial primary key, 
    versao int, 
    rua varchar(255),
    complemento varchar(512),
    cep varchar(16),
    setor varchar(255)
    cidade_id int references ger_cidade(id)
);

create table ger_pessoa_endereco (
    id serial primary key, 
    versao int, 
    tipo_id int references ger_tipo(id),
    pessoa_id int references ger_pessoa(id),
    endereco_id int references ger_endereco(id)
);

create table seg_usuario (
    id serial primary key, 
    versao int, 
    pessoa_id int references ger_pessoa(id),
    codigo varchar(32)
);

create table seg_perfil (
    id serial primary key, 
    versao int, 
    nome varchar(127),
    descricao varchar(127)
);

create table seg_usuario_perfil(
    id serial primary key, 
    versao int, 
    usuario_id int references seg_usuario(id),
    perfil_id int references seg_usuario(id)
        
)
