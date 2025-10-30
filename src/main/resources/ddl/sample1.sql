/**
 * Author:  thiago
 * Created: 28 de out. de 2025
 */
-- empresa
insert into sis_empresa
(id, nome)
values
(1, 'Sistema');


-- tipo
insert into ger_tipo_grupo
(id, nome)
values
(1, 'Documento Identificacao'),
(2, 'Endereco'),
(3, 'Link'),
(4, 'Email'),
(5, 'Tipo pessoa'),
(6, 'Status');

insert into ger_tipo 
(id, grupo_id, nome, detalhe)
values
(1, 1, 'CPF', 'Cadastro Pessoa Fisica'),
(2, 1, 'CNPJ', 'CNPJ Empresa'),
(3, 1, 'Identidade', 'Registro Identidade'),
(4, 2, 'Residencia', 'Endereço Residencial'),
(5, 2, 'Trabalho', 'Endereço Trabalho'),
(6, 3, 'Linkedin', 'Endereco linkedin'),
(7, 3, 'Maps', 'Endereco maps'),
(8, 4, 'E-mail', 'Endereco e-mail'),
(9, 5, 'Fisica', 'Pessoa fisica'),
(10, 5, 'Juridica', 'Pessoa juridica'),
(11, 6, 'Status', 'Status geral'),
(12, 6, 'Status cliente', 'Status especifico cliente')
;

-- status
insert into ger_status_tipo
(id, versao, tipo_id, nome)
values
(1, 1, 11, 'Ok' ),
(2, 1, 11, 'Cancelado');

insert into ger_status_proximo
(id, versao, status_atual_id, status_proximo_id)
values
(1, 1, 1, 2);

insert into ger_status_historico
(id, versao,  tipo_id, processo_id, data, observacao, status_id)
values
(1, 1, 11, 100, '2025-11-10 10:50:31', 'Status inicial', 1),
(2, 1, 11, 100, '2025-11-11 10:50:31', 'Canelado', 2);


insert into ger_status
(id, versao, tipo_id, processo_id, atual_id)
values 
(1, 1, 2);

-- historico
insert into ger_historico_grupo
(id, versao, nome, observacao)
values
(1, 1, 'Usuario login', 'Registro de historico de login')

insert into ger_historico
(id, versao, data, grupo_id, registro)
values
(1, 1, '2025-11-10 11:30:59', 1, 'Usuario entrou no sistema')

-- cidade estado
insert into ger_pais
(id, versao, nome, codigo_telefone)
values
(1, 1, 'Brasil', 55);

insert into ger_estado
(id, versao, nome, pais_id)
values
(1, 1, 'Goias', 1);

insert into ger_cidade
(id, versao, nome, estado_id)
values (1, 1, 'Goiânia', 1);


-- pessoa
insert into ger_pessoa 
(id, versao, tipo_id, nome_primeiro, nome_meio, nome_ultimo, apelido, data_nascimento)
values
(1, 1, 9, 'Thiago', 'Ritcher da Cunha', 'Alvares', 'Thiago', '20/03/1984');

insert into ger_documento 
(id, versao, tipo_id, numero)
values
(1, 1, 1, '00256634106');

insert into ger_pessoa_documento 
(id, versao, pessoa_id, documento_id)
values
(1, 1, 1, 1);


insert into ger_endereco
(id, versao, rua, complemento, cep, setor, cidade_id)
values (1, 1, 'R. Wilton Pinheiro de Lima', 'Qd. 46 Lt 14 Casa 2', '74915189', 'Vila Maria', 1);

insert into ger_pessoa_endereco
(id, versao, tipo_id,  pessoa_id, endereco_id)
values (1, 1, 3, 1, 1);


insert into ger_link
(id, versao, tipo_id, endereco)
values (1, 1, 7, 'https://www.google.com/maps/place//@-16.7510016,-49.2732416,7997m/data=!3m1!1e3?entry=ttu&g_ep=EgoyMDI1MTAyNi4wIKXMDSoASAFQAw%3D%3D');

insert into ger_pessoa_link
(id, versao, pessoa_id, link_id) 
values (1, 1, 1, 1);

insert into ger_email
(id, versao, tipo_id, endereco)
values (1, 1, 8, 'thiagoritcher@gmail.com');

insert into ger_pessoa_email
(id, versao, pessoa_id, email_id) 
values (1, 1, 1, 1);


--usuario
insert into seg_usuario
(id, versao, pessoa_id, codigo)
values (1, 1, 1, '19840320');

insert into seg_perfil
(id, versao, nome, descricao)
values
(1, 1, 'Admin', 'Admin profile');

insert into seg_usuario_perfil
(usuario_id, perfil_id)
values (1, 1);

insert into seg_modulo 
(id, versao, nome)
values 
(1, 1, 'Geral'),
(2, 1, 'Seguranca');

insert into seg_funcao
(id, versao, modulo_id, ident, nome)
values 
(1, 1, 1, 'geral/pessoa', 'Pessoa');

insert into seg_perfil_funcao
(id, versao, funcao_id, perfil_id)
values 
(1, 1, 1, 1);


-- cliente
begin 
pid int = rndid()

insert into ger_status_historico
(versao, tipo_id, processo_id, status_id, data, observacao)
values 
(1, 12, pid, 1, '2025-11-11 10:50:20', 'Cliente criado');

insert into ger_status
(versao, tipo_id, processo_id, status_id)
values 
(1, 1, 12, pid, 1);

insert into ger_historico
(versao, data, grupo_id, registro)
(1, '2025-11-11 10:50:20', 1, 'Cliente criado');


insert into ger_cliente
(id, versao, status_id, historico_id, pessoa_id)
values
(1, 1, lastid('ger_status'), lastid('ger_historico'), 1);
end 

