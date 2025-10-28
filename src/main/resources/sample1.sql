/**
 * Author:  thiago
 * Created: 28 de out. de 2025
 */

insert into ger_tipo_grupo
(id, nome)
values
(1, 'Documento Identificacao');

insert into ger_tipo 
(id, grupo_id, nome, detalhe)
values
(1, 1, 'CPF', 'Cadastro Pessoa Fisica')
(2, 1, 'Identidade', 'Registro Identidade');



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



