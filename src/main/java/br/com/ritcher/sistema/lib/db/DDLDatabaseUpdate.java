package br.com.ritcher.sistema.lib.db;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.DatabaseClient.GenericExecuteSpec;
import org.springframework.stereotype.Component;

@Component
public class DDLDatabaseUpdate implements CommandLineRunner {

	public void run(String... args) throws Exception {
		LOG.info("EXECUTING: DatabaseUpdate");
		update();
	}

	private static Logger LOG = LoggerFactory.getLogger(DDLDatabaseUpdate.class);

	private final DatabaseClient databaseClient;

	public DDLDatabaseUpdate(DatabaseClient databaseClient) {
		this.databaseClient = databaseClient;
	}

	public void update() {
		int i = databaseVersion();
		int startingVersion = i;

		databaseClient.sql("begin transaction").then().block();

		try {
			while (true) {
				i++;
				Method m = Update.class.getMethod("v" + i);
				Update update = new Update(this);
				try {
					LOG.info("Updating to " + i);
					m.invoke(update);
					LOG.info("Updated to " + i);

				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		} catch (NoSuchMethodException e) {
			int updatedVersion = i - 1;
			if (startingVersion < updatedVersion) {
				LOG.info("Update done");
				updateDatabaseVersion(updatedVersion);
				databaseClient.sql("commit").then().block();
			} else {
				LOG.info("Current version is up to date: " + updatedVersion);
				databaseClient.sql("rollback").then().block();
			}
			return;

		} catch (SecurityException e) {
			databaseClient.sql("rollback").then().block();
			throw new RuntimeException(e);
		}
	}

	private void updateDatabaseVersion(int versao) {
		GenericExecuteSpec sql = databaseClient.sql("update sis_config set versao = " + versao);
		sql.then().block();
	}

	private int databaseVersion() {
		GenericExecuteSpec sql = databaseClient.sql("select versao from sis_config");
		try {
			Map<String, Object> result = sql.fetch().first().block();
			return (Integer) result.get("versao");
		} catch (Exception e) {
			// new database, or another problem..
			LOG.error("new database, or another problem", e.getMessage());
			return createConfigTable();
		}
	}

	private int createConfigTable() {
		try {
			GenericExecuteSpec sql = databaseClient.sql("create table sis_config"
					+ "(id int, versao int, primary key(id));" + "insert into sis_config(id, versao) values (1, 0);");

			sql.then().block();
			return 0;

		} catch (Exception e) {
			LOG.error("Failed to create sis_config table");
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void execute(String sql) {
		try {
			GenericExecuteSpec ges = databaseClient.sql(sql);
			ges.then().block();

		} catch (Exception e) {
			LOG.error("Failed to execute " + sql);
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public List<Map<String, Object>> query(String sql) {
		return databaseClient.sql(sql).fetch().all().collectList().block();
	}
}