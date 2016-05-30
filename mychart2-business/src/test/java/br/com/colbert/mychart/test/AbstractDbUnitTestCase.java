package br.com.colbert.mychart.test;

import java.io.File;
import java.sql.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.*;
import org.dbunit.dataset.xml.*;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.*;
import org.slf4j.Logger;

import br.com.colbert.mychart.infrastructure.transaction.Transactional;

/**
 * Implementação base para os testes que necessitem que o DBUnit efetue carga de dados no banco.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
public abstract class AbstractDbUnitTestCase extends AbstractCdiTestCase {

	private static final String DATASETS_DIR = "datasets";

	@Inject
	protected EntityManager entityManager;

	@Inject
	private Logger logger;

	private FlatXmlDataSet dataSet;

	@Before
	@Transactional
	public void setUpDb() {
		logger.info("Carga do banco de dados");

		entityManager.unwrap(Session.class).doWork(new Work() {

			@Override
			public void execute(Connection jdbcConnection) throws SQLException {
				try {
					IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
					connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
					dataSet = new FlatXmlDataSetBuilder().setDtdMetadata(false).build(Thread.currentThread().getContextClassLoader()
							.getResourceAsStream(DATASETS_DIR + File.separatorChar + getDataSetFileName()));
					DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
				} catch (DatabaseUnitException exception) {
					throw new RuntimeException("Erro ao efetuar carga do banco de dados", exception);
				}
			}
		});
	}

	@After
	@Transactional
	public void tearDownDb() {
		logger.info("Limpeza do banco de dados");

		entityManager.unwrap(Session.class).doWork(new Work() {

			@Override
			public void execute(Connection jdbcConnection) throws SQLException {
				try {
					IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
					DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
				} catch (DatabaseUnitException exception) {
					throw new RuntimeException("Erro ao efetuar limpeza do banco de dados", exception);
				}
			}
		});
	}

	/**
	 * 
	 * @return
	 */
	protected abstract String getDataSetFileName();
}
