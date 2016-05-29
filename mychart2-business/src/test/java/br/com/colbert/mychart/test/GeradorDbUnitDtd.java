package br.com.colbert.mychart.test;

import java.io.*;
import java.sql.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.*;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdWriter;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.Test;

/**
 * Classe responsável por gerar o arquivo DTD do banco de dados.
 * 
 * @author ThiagoColbert
 * @since 29 de mai de 2016
 */
public class GeradorDbUnitDtd extends AbstractCdiTestCase {

	private static final String CAMINHO_ARQUIVO_DTD = "src/test/resources/datasets/mychart.dtd";

	@Inject
	private EntityManager entityManager;

	@Test
	public void gerarDtd() {
		entityManager.unwrap(Session.class).doWork(new Work() {

			@Override
			public void execute(Connection jdbcConnection) throws SQLException {
				IDatabaseConnection connection;
				try {
					connection = new DatabaseConnection(jdbcConnection);
					IDataSet dataSet = connection.createDataSet();
					Writer out = new OutputStreamWriter(new FileOutputStream(CAMINHO_ARQUIVO_DTD));
					FlatDtdWriter datasetWriter = new FlatDtdWriter(out);
					datasetWriter.setContentModel(FlatDtdWriter.CHOICE);
					datasetWriter.write(dataSet);
				} catch (DatabaseUnitException | IOException exception) {
					throw new RuntimeException("Erro ao gerar arquivo DTD", exception);
				}
			}
		});
	}
}
