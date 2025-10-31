/**
 * Author:  thiago
 * Created: 28 de out. de 2025
 */

create function lastid(tbl text) returns integer
  language sql
  immutable
  returns null on null input
  return currval(tbl || '_id_seq');


create function rdnid() returns integer 
  language sql
  immutable
  returns null on null input
  -- max int
  return random() * (2147483646) + 1;

create function setid(tbl text, id integer) returns integer
  language sql
  immutable
  returns null on null input
  return setval(tbl || '_id_seq', id, true);

-- empresa
insert into sis_empresa
(id, nome)
values
(1, 'Sistema');



-- tipo
insert into ger_tipo_grupo
(id, sid,  nome)
values
(1, 1,'Documento Identificacao'),
(2, 2,'Endereco'),
(3, 3,'Link'),
(4, 4,'Email'),
(5, 5,'Tipo pessoa'),
(6, 6,'Status'), 
(7, 7,'Pessoas');
select setid('ger_tipo_grupo', 7);


insert into ger_tipo 
(id, grupo_id, sid, nome, detalhe)
values
(1,  1,  1,  'CPF', 'Cadastro Pessoa Fisica'),
(2,  1,  2,  'CNPJ', 'CNPJ Empresa'),
(3,  1,  3,  'Identidade', 'Registro Identidade'),
(4,  2,  4,  'Residencia', 'Endereço Residencial'),
(5,  2,  5,  'Trabalho', 'Endereço Trabalho'),
(6,  3,  6,  'Linkedin', 'Endereco linkedin'),
(7,  3,  7,  'Maps', 'Endereco maps'),
(8,  4,  8,  'E-mail', 'Endereco e-mail'),
(9,  5,  9,  'Fisica', 'Pessoa fisica'),
(10, 5,  10, 'Juridica', 'Pessoa juridica'),
(11, 6,  11, 'Status', 'Status geral'),
(12, 6,  12, 'Status cliente', 'Status especifico cliente'),
(13, 6,  13, 'Grupo Clietes', 'Tipo para grupo de clientes'),
(14, 6,  14, 'Grupo Fornecedores', 'Tipo para grupo de forneceddores')
;
select setid('ger_tipo', 12);

-- status
insert into ger_status
(id, versao, sid, tipo_id, nome)
values
(1, 1, 11, 1, 'Ok' ),
(2, 1, 11, 2, 'Cancelado');
select setid('ger_status', 2);

insert into ger_status_proximo
(id, versao, status_atual_id, status_proximo_id)
values
(1, 1, 1, 2);
select setid('ger_status_proximo', 1);

insert into ger_status_historico
(id, versao,  tipo_id, entidade_id, data, observacao, status_id)
values
(1, 1, 11, 100, '2025-11-10 10:50:31', 'Status inicial', 1),
(2, 1, 11, 100, '2025-11-11 10:50:31', 'Canelado', 2);
select setid('ger_status_historico', 2);


insert into ger_grupo
(id, versao, tipo_id, sid,  nome, superior_id)
values
(1, 1, 13, 1, 'Clientes', null),
(2, 1, 14, 2, 'Fornecedores', null),
(3, 1, 13, null, 'Principais', 1),
(4, 1, 13, null, 'Outros', 1),
(5, 1, 14, null, 'Suprimentos', 2),
(6, 1, 14, null, 'Materiais', 2),
(7, 1, 14, 3, 'Itens', null),
(8, 1, 14, null, 'Materiais', 7)
;
select setid('ger_grupo', 6);


-- cidade estado
insert into ger_pais
(id, versao, nome, codigo_telefone)
values
(1, 1, 'Brasil', 55);
select setid('ger_pais', 1);

insert into ger_estado
(id, versao, nome, pais_id)
values
(1, 1, 'Goias', 1);
select setid('ger_estado', 1);

insert into ger_cidade
(id, versao, nome, estado_id)
values (1, 1, 'Goiânia', 1);
select setid('ger_cidade', 1);


-- pessoa
insert into ger_pessoa 
(id, versao, tipo_id, nome_primeiro, nome_meio, nome_ultimo, apelido, data_nascimento)
values
(1, 1, 9, 'Thiago', 'Ritcher da Cunha', 'Alvares', 'Thiago', '20/03/1984');
select setid('ger_pessoa', 1);

insert into ger_documento 
(id, versao, tipo_id, numero)
values
(1, 1, 1, '00256634106');
select setid('ger_documento', 1);

insert into ger_pessoa_documento 
(id, versao, pessoa_id, documento_id)
values
(1, 1, 1, 1);
select setid('ger_pessoa_documento', 1);

insert into ger_endereco
(id, versao, rua, complemento, cep, setor, cidade_id)
values (1, 1, 'R. Wilton Pinheiro de Lima', 'Qd. 46 Lt 14 Casa 2', '74915189', 'Vila Maria', 1);
select setid('ger_endereco', 1);

insert into ger_pessoa_endereco
(id, versao, tipo_id,  pessoa_id, endereco_id)
values (1, 1, 3, 1, 1);
select setid('ger_pessoa_endereco', 1);

insert into ger_link
(id, versao, tipo_id, endereco)
values (1, 1, 7, 'https://www.google.com/maps/place//@-16.7510016,-49.2732416,7997m/data=!3m1!1e3?entry=ttu&g_ep=EgoyMDI1MTAyNi4wIKXMDSoASAFQAw%3D%3D');
select setid('ger_link', 1);

insert into ger_pessoa_link
(id, versao, pessoa_id, link_id) 
values (1, 1, 1, 1);
select setid('ger_pessoa_link', 1);

insert into ger_email
(id, versao, tipo_id, endereco)
values (1, 1, 8, 'thiagoritcher@gmail.com');
select setid('ger_email', 1);

insert into ger_pessoa_email
(id, versao, pessoa_id, email_id) 
values (1, 1, 1, 1);
select setid('ger_pessoa_email', 1);

--usuario
insert into seg_usuario
(id, versao, pessoa_id, codigo)
values (1, 1, 1, '19840320');
select setid('seg_usuario', 1);

insert into seg_perfil
(id, versao, nome, descricao)
values
(1, 1, 'Admin', 'Admin profile');
select setid('seg_perfil', 1);

insert into seg_usuario_perfil
(usuario_id, perfil_id)
values (1, 1);
select setid('seg_usuario_perfil', 1);

insert into seg_modulo 
(id, versao, nome)
values 
(1, 1, 'Geral'),
(2, 1, 'Seguranca');
select setid('seg_modulo', 2);

insert into seg_funcao
(id, versao, modulo_id, ident, nome)
values 
(1, 1, 1, 'geral/pessoa', 'Pessoa');
select setid('seg_funcao', 1);

insert into seg_perfil_funcao
(id, versao, funcao_id, perfil_id)
values 
(1, 1, 1, 1);
select setid('seg_perfil_funcao', 1);

-- cliente
insert into ger_cliente
(id, versao, status_id, pessoa_id, grupo_id)
values
(1, 1, 1, 1, 3);

-- fornecedor
insert into ger_fornecedor
(id, versao, status_id, pessoa_id, grupo_id)
values
(1, 1, 1, 1, 6);

-- item
insert into ger_item
(id, versao, status_id, codigo, referencia, nome, observacao)
values
(1, 1, 1, '123456', '123456', 'Item 1', 'Observacao 1');

-- item_grupo 
insert into ger_item_grupo
(id, versao, item_id, grupo_id)
values
(1, 1, 1, 8);


-- calculo
insert into ger_calculo
(id, versao, descricao, formula)
values
(1, 1, 'Calculo 1', '1 + 1');

-- ger_condicao_pagamento
insert into ger_condicao_pagamento
(id, versao, status_id, nome, observacao, calculo_id)
values
(1, 1, 1, 'Condicao 1', 'Observacao 1', 1);

-- ger_imposto_contribuicao
insert into ger_imposto_contribuicao
(id, versao, status_id, nome, observacao, calculo_id)
values
(1, 1, 1, 'Imposto 1', 'Observacao 1', 1);

-- cotacao
insert into cot_cotacao
(id, versao, status_id, fornecedor_id)
values
(1, 1, 1, 1);

-- cotacao_item
insert into cot_cotacao_item
(id, versao, cotacao_id, item_id, codigo, referencia, nome, condicao_id)
values
(1, 1, 1, 1, 1,  '123456', '123456', 'Item 1', 1);

-- cotacao_item_valor
insert into cot_cotacao_item_valor 
(id, versao, valor, cotacao_item_id, condicao_id)
values
(1, 1, 1, 1, 1);


insert into con_conta_contabil 
(id, versao, status_id ,codigo ,referencia , conta_analitica_id)
values 
(1, 1, 1, '1', null, null);

insert into con_historico 
(id, versao, status_id ,definicao)
values 
(1, 1, 1, '1');

insert into con_lancamento 
(id, versao, historico_id, conta_id, data, credito, debito)
values 
(1, 1, 1, 1, '2025-11-11 10:50:31', null, 1.00),
(2, 1, 1, 1, '2025-11-11 10:50:31', 1.00, null);


insert into est_tabela_preco 
(id, versao, status_id, fornecedor_id, cotacao_id, item_id, codigo, referencia, nome, condicao_id)
values
(1, 1, 1, 1, 1, 1, '123456', '123456', 'Item 1', 1);

insert into est_tabela_preco_item_valor 
(id, versao, valor, cotacao_id, condicao_id)
values
(1, 1, 1, 1, 1);

insert into com_pedido_cliente 
(id, versao, tipo_id, arquivo_id, status_id, cliente_id)
values
(1, 1, 1, 1, 1, 1);

