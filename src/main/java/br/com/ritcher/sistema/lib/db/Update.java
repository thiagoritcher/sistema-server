package br.com.ritcher.sistema.lib.db;

public class Update {

	private DDLDatabaseUpdate conn;

	public Update(DDLDatabaseUpdate ddlDatabaseUpdate) {
		this.conn = ddlDatabaseUpdate;
	}

	public void v1() {
		conn.execute("create table sis_aplicacao(" + "	id uuid," + "	versao bigint," + "	criacao timestamptz,"
				+ "	nome varchar(100)," + "	primary key(id)" + ");");
	}

}