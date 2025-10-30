/**
 * Author:  thiago
 * Created: 28 de out. de 2025
 */

--V1
--drop table ger_tipo_grupo;
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

create table sis_config(
	id int, 
	versao int, 
	primary key(id)
);

insert into sis_config(id, versao) values (1, 0);

create table sis_empresa(
    id int primary key, 
    nome varchar(255)    
);

create table ger_tipo_grupo (
    id serial primary key, 
    nome varchar(255)
);

create table ger_tipo (
    id serial primary key, 
    grupo_id int references ger_tipo_grupo(id),
    nome varchar(255),
    detalhe varchar(512)
);

create index on ger_tipo (grupo_id, id);

create table ger_status_tipo(
    id serial primary key,
    versao int,
    tipo_id int references ger_tipo(id),
    nome varchar(127)
);

create table ger_status_proximo(
    id serial primary key,
    versao int,
    status_atual_id int references ger_status_tipo(id),
    status_proximo_id int references ger_status_tipo(id)
);

create table ger_status_historico(
    id serial primary key,
    versao int,
    tipo_id int references ger_tipo(id),
    processo_id int, 
    status_id int references ger_status_tipo(id),
    data timestamp,
    observacao varchar(255)
);

create index on ger_status_historico (tipo_id, processo_id);

create table ger_status (
    id serial primary key,
    versao int,
    tipo_id int references ger_tipo(id),
    processo_id int, 
    atual_id int references ger_status_historico(id)
);
create index on ger_status (tipo_id, processo_id);

create table ger_historico_grupo(
    id serial primary key,
    versao int,
    nome varchar(512),
    observacao text
);

create table ger_historico (
    id serial primary key,
    versao int,
    data timestamp, 
    grupo_id int references ger_historico_grupo(id),
    registro varchar(255)
);

create table ger_grupo (
    id serial primary key, 
    versao int,
    tipo_id int references ger_tipo(id),
    nome varchar(255),
    superior_id int references ger_grupo(id)
);

create table ger_pessoa (
    id serial primary key, 
    versao int, 
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    tipo_id int references ger_tipo(id),
    nome_primeiro varchar(255),
    nome_meio varchar(255),
    nome_ultimo varchar(255),
    apelido varchar(255),
    data_nascimento date 
);

create table seg_usuario (
    id serial primary key, 
    versao int, 
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    pessoa_id int references ger_pessoa(id),
    codigo varchar(32)
);



create table ger_arquivo (
    id serial primary key,
    versao int, 
    status_id int references ger_status(id),
    nome_original varchar(512),
    file_path varchar(255)
);

create table ger_arquivo_lista (
    id serial primary key, 
    versao int
);

create table ger_arquivo_lista_arquivos (
    id serial primary key, 
    versao int, 
    lista_id int references ger_arquivo_lista(id),
    tipo_id int references ger_tipo(id),
    arquivo_id int references ger_arquivo(id)
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

create table ger_pais (
  id serial primary key, 
  versao int,
  nome varchar(255), 
  codigo_telefone int
);

create table ger_estado (
    id serial primary key, 
    versao int, 
    nome varchar(255),
    pais_id int references ger_pais(id)
);

create table ger_cidade (
    id serial primary key, 
    versao int, 
    nome varchar(255),
    estado_id int references ger_estado(id)
);

create table ger_endereco (
    id serial primary key, 
    versao int, 
    rua varchar(255),
    complemento varchar(512),
    cep varchar(16),
    setor varchar(255),
    cidade_id int references ger_cidade(id)
);

create table ger_pessoa_endereco (
    id serial primary key, 
    versao int, 
    tipo_id int references ger_tipo(id),
    pessoa_id int references ger_pessoa(id),
    endereco_id int references ger_endereco(id)
);

create table ger_email (
    id serial primary key, 
    tipo_id int references ger_tipo(id),
    versao int,
    endereco varchar(512)
);

create table ger_pessoa_email (
    id serial primary key, 
    versao int,
    pessoa_id int references ger_pessoa(id),
    email_id int references ger_email(id)
);

create table ger_link (
    id serial primary key, 
    versao int,
    tipo_id int references ger_tipo(id),
    endereco varchar(1024)
);

create table ger_pessoa_link (
    id serial primary key, 
    versao int,
    pessoa_id int references ger_pessoa(id),
    link_id int references ger_link(id)
);


create table seg_perfil (
    id serial primary key, 
    versao int, 
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    nome varchar(127),
    descricao varchar(127)
);

create table seg_usuario_perfil(
    id serial primary key, 
    versao int, 
    usuario_id int references seg_usuario(id),
    perfil_id int references seg_perfil(id)
);

create table seg_modulo(
    id serial primary key, 
    versao int, 
    nome varchar(255)
);

create table seg_funcao(
    id serial primary key, 
    versao int, 
    modulo_id int references seg_modulo(id),
    ident varchar(255),
    nome varchar(255)
);

create table seg_perfil_funcao(
    id serial primary key, 
    versao int,
    funcao_id int references seg_funcao(id),
    perfil_id int references seg_perfil(id)
);

create table ger_telefone (
    id serial primary key, 
    versao int,
    tipo_id int references ger_tipo(id),
	ddd varchar(8),
	numero varchar(16)
);

create table ger_pessoa_telefone (
    id serial primary key, 
    versao int,
    pessoa_id int references ger_pessoa(id),
    telefone_id int references ger_telefone(id)
);


create table ger_cliente (
    id serial primary key, 
    versao int,
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    pessoa_id int references ger_pessoa(id)
    grupo_id int references ger_grupo(id)
);


create table ger_fornecedor (
    id serial primary key, 
    versao int,
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    pessoa_id int references ger_pessoa(id),
    grupo_id int references ger_grupo(id)
);

create table ger_fornecedor_grupo (
	id serial primary key, 
    versao int,
    grupo_id int references ger_grupo(id),
    fornecedor_id int references ger_fornecedor(id)
);

create table ger_item(
	id serial primary key, 
    versao int,
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    codigo varchar(32), 
    referencia varchar(32),
    nome varchar(255),
    observacao text
);

create table ger_item_grupo (
	id serial primary key, 
    versao int,
    grupo_id int references ger_grupo(id),
    item_id int references ger_item(id)
);

create table ger_calculo (
	id serial primary key, 
    versao int,
    descricao varchar(512),
    formula varchar(512)
);

create table ger_condicao_pagamento (
	id serial primary key, 
    versao int,
    status_id int references ger_status(id),
    nome varchar(255),
    calculo_id int references ger_calculo(id)
);

create table ger_imposto_contribuicao (
    id serial primary key, 
    versao int,
    status_id int references ger_status(id),
    nome varchar(127),
    observacao text, 
    calculo_id int references ger_calculo(id)
);

create table cot_cotacao (
	id serial primary key, 
    versao int,
    status_id int references ger_status(id),
    fornecedor_id int references ger_fornecedor(id)
);

create table cot_cotacao_item (
	id serial primary key, 
    versao int,
    item_id int references ger_item(id),
    codigo varchar(32), 
    referencia varchar(32),
    nome varchar(255),
	cotacao_id int references cot_cotacao(id),
	condicao_id int references ger_condicao_pagamento(id)
);

create table cot_cotacao_item_valor(
	id serial primary key, 
    versao int,
    valor numeric(10,2),
    cotacao_item_id  int references cot_cotacao_item(id),
	condicao_id int references ger_condicao_pagamento(id)
);


create table con_conta_contabil (
    id serial primary key, 
    versao int, 
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    codigo varchar(64),
    referencia varchar(127),
    conta_analitica_id int references con_conta_contabil(id)
);

create table con_historico (
    id serial primary key, 
    versao int, 
    codigo varchar(64),
    definicao varchar(512)
);

create table con_lancamento (
    id serial primary key, 
    versao int,
    historico_id int references con_historico(id),
    conta_id int references con_conta_contabil(id),
    data timestamp,
    credito numeric(10,2),
    debito numeric(10,2)
);


create table est_tabela_preco (
	id serial primary key, 
    versao int,
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    fornecedor_id int references ger_fornecedor(id),
	cotacao_id int references cot_cotacao(id),
    item_id int references ger_item(id),
    codigo varchar(32), 
    referencia varchar(32),
    nome varchar(255),
	condicao_id int references ger_condicao_pagamento(id)
);

create table est_tabela_preco_item_valor(
	id serial primary key, 
    versao int,
    valor numeric(10,2),
    cotacao_id  int references cot_cotacao_item(id),
	condicao_id int references ger_condicao_pagamento(id)
);

create table com_pedido_cliente (
    id serial primary key,
    versao int, 
    tipo_id int references ger_tipo(id),
    arquivo_id int references ger_arquivo_lista(id),
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    cliente_id int references ger_cliente(id)   
);

create table est_pedido_fornecedor (
    id serial primary key,
    versao int, 
    tipo_id int references ger_tipo(id),
    arquivo_id int references ger_arquivo_lista(id),
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    fornecedor_id int references ger_fornecedor(id)   
);

create table com_pedido_cliente_itens (
    id serial primary key, 
    versao int, 
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    item_id int references ger_item(id),
    quantidade numeric(10,2),
    valor numeric(10,2)
);

create table com_pedido_cliente_itens_valores (
    id serial primary key, 
    versao int, 
    tipo_id int references ger_tipo(id),
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    pedido_item_id int references com_pedido_cliente_itens(id), 
    origem_id int references ger_imposto_contribuicao(id),
    descricao varchar(255),
    valor numeric(10, 2)
);

create table est_pedido_fornecedor_itens (
    id serial primary key, 
    versao int, 
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    item_id int references ger_item(id),
    quantidade numeric(10,2),
    valor_unitario numeric(10,2),
    valor_total numeric(10,2)
);

create table est_pedido_fornecedor_itens_valores (
    id serial primary key, 
    versao int, 
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    item_id int references est_pedido_fornecedor_itens(id), 
    origem_id int references ger_imposto_contribuicao(id),
    descricao varchar(255),
    valor numeric(10, 2)   
);

create table est_estoque_local (
    id serial primary key, 
    versao int, 
    nome varchar(255),
    observacao text,
    local_superior_id int references est_estoque_local(id)
);

create table est_estoque_calculo (
    id serial primary key, 
    versao int,
    nome varchar(255),
    calculo_quantidade_id int references ger_calculo(id),
    calculo_valor_id int references ger_calculo(id)
);

create table est_estoque_config (
    id serial primary key, 
    versao int,
    tipo_id int references ger_tipo(id),
    calculo_id int references est_estoque_calculo(id)
);

create table est_estoque (
    id serial primary key, 
    versao int, 
    local_id int references est_estoque_local(id),
    config_id int references est_estoque_config(id),
    item_id int references ger_item(id),
    quantidade numeric(10,2), 
    status_id int references ger_status(id),
    valor_unitario numeric(10,2),
    valor_total numeric(10,2)
);

create table est_estoque_registro_tipo (
    id serial primary key, 
    versao int, 
    tipo_id int references ger_tipo(id),
    calculo_quantidade_id int references ger_calculo(id),
    calculo_valor_id int references ger_calculo(id)
);

create table est_estoque_registro (
    id serial primary key, 
    versao int, 
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    registro_tipo_id int references est_estoque_registro_tipo(id)
);

create table est_estoque_regitro_itens (
    id serial primary key, 
    versao int, 
    registro_id int references est_estoque_registro(id),
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    item_id int references ger_item(id),
    quantidade numeric(10,2),
    valor_unitario numeric(10,2),
    valor_total numeric(10,2)
);


create table fin_banco (
    id serial primary key, 
    versao int, 
    tipo_id int references ger_tipo(id),
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    nome varchar(64), 
    codigo varchar(16)
);

create table fin_banco_saldo (
    id serial primary key, 
    versao int, 
    tipo_id int references ger_tipo(id),
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    saldo numeric(10,2)
);

create table fin_conta_origem (
    id serial primary key, 
    versao int, 
    tipo_id int references ger_tipo(id),
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    nome varchar(512)
);

create table fin_credor_devedor (
    id serial primary key, 
    versao int, 
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),

    cliente_id int references ger_cliente(id),
    fornecedor_id int references ger_fornecedor(id),
    pessoa_id int references ger_pessoa(id)
);

create table fin_dados_bancarios(
    id serial primary key, 
    versao int, 
    status_id int references ger_status(id),
    credor_devedor_id  int references fin_credor_devedor(id), 
    banco_id int references fin_banco(id),
    agencia varchar(64),
    conta varchar(64),
    pix varchar(64)
);

create table fin_conta (
    id serial primary key, 
    versao int, 
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    credor_id int references fin_credor_devedor(id),
    devedor_id int references fin_credor_devedor(id),
    valor numeric(10,2), 
    origem_id int references fin_conta_origem(id)
);

create table fin_conta_ajuste (
    id serial primary key, 
    versao int, 
    tipo_id int references ger_tipo(id),
    status_id int references ger_status(id),
    historico_id int references ger_historico(id)
);

create table fin_conta_ajuste_contas(
    id serial primary key, 
    versao int, 
    tipo_id int references ger_tipo(id),
    ajuste_id int references fin_conta_ajuste(id),
    conta_id int references fin_conta(id)
);

create table fin_pagamento (
    id serial primary key, 
    versao int, 
    tipo_id int references ger_tipo(id),
    status_id int references ger_status(id),
    historico_id int references ger_historico(id),
    pagador_id int references fin_credor_devedor(id),
    recebedor_id int references fin_credor_devedor(id),
    valor_total numeric(10,2)
);

create table fin_pagamento_conta (
    id serial primary key, 
    versao int, 
    pagamento_id int references fin_pagamento(id),
    conta_id int references fin_conta(id),
    valor numeric(10,2)
);
