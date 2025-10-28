/**
 * Author:  thiago
 * Created: 28 de out. de 2025
 */

insert into ger_tipo_grupo
(id, nome)
values
(1, 'Documento Identificacao')
(2, 'Endereco')
;

insert into ger_tipo 
(id, grupo_id, nome, detalhe)
values
(1, 1, 'CPF', 'Cadastro Pessoa Fisica'),
(2, 1, 'Identidade', 'Registro Identidade'),
(3, 2, 'Residencia', 'Endereço Residencial'),
(4, 2, 'Trabalho', 'Endereço Trabalho')
;

insert into ger_pessoa 
(id, versao, nome_primeiro, nome_meio, nome_ultimo, apelido, data_nascimento)
values
(1, 1, 'Thiago', 'Ritcher da Cunha', 'Alvares', 'Thiago', '20/03/1984');

insert into ger_documento 
(id, versao, tipo_id, numero)
values
(1, 1, 1, '00256634106');

insert into ger_pessoa_documento 
(id, versao, pessoa_id, documento_id)
values
(1, 1, 1, 1);

insert into ger_estado
(id, versao, nome, pais)
values
(1, 1, 'Goias', 'Brasil');


insert into ger_cidade
(id, versao, nome, estado_id)
values 
(1, 1, 'Goiânia', 1)

insert into ger_endereco()
(id, versao, rua, complemento, cep, setor, cidade_id)
values
(1, 1, 'R. Wilton Pinheiro de Lima', 'Qd. 46 Lt 14 Casa 2', '74915189', 'Vila Maria', 1);

insert into ger_pessoa_endereco()
(id, versao, tipo_id,  pessoa_id, endereco_id)
values
(1, 1, 3, 1, 1);







